package org.vedruna.repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.vedruna.model.Document;
import org.vedruna.model.dto.NewDocumentDTO;
import org.vedruna.model.enums.ValidationState;

@ApplicationScoped
public class DocumentRepository implements PanacheRepository<Document> {

    @Inject
    EmployeeRepository employeeRepo;

    @Inject
    CompanyRepository companyRepo;

    /**
     * Crea un documento sin archivo
     * @param dto objeto con campos
     * @return nueva entidad
     */
    @Transactional
    public Document create(NewDocumentDTO dto) {
        // Las fechas de validación y expiración no pueden ser anteriores a la actual
        if (dto.getExpirationDate() != null && dto.getDate().isAfter(dto.getExpirationDate())) throw new IllegalArgumentException("La fecha de emisión no puede ser posterior a su fecha de expiración");
        if (dto.getValidationDate() != null && dto.getDate().isAfter(dto.getValidationDate())) throw new IllegalArgumentException("La fecha de emisión no puede ser posterior a su fecha de validación");

        Document doc = new Document(
                ValidationState.valueOf(dto.getValidationState()),
                companyRepo.findByIdOptional(dto.getContractorId()).orElseThrow(() -> new NotFoundException("Empresa no encontrada.")),
                companyRepo.findByIdOptional(dto.getSubcontractId()).orElseThrow(() -> new NotFoundException("Empresa no encontrada.")),
                dto.getName(),
                dto.getDate(),
                dto.getExpirationDate(),
                dto.getValidationDate(),
                dto.getEmployeeId() == null ? null : employeeRepo.findByIdOptional(dto.getEmployeeId()).orElseThrow(() -> new NotFoundException("Empleado no encontrado.")),
                dto.getAdditionalInfo(),
                null
        );

        persist(doc);

        return doc;
    }

    /**
     * Adjunta un archivo a un documento existente
     * @param id ID del documento
     * @param part Archivo para adjuntar
     * @return el documento con el archivo adjunto
     */
    @Transactional
    public Document addFile(Long id, InputPart part) {
        if (part == null) throw new BadRequestException("No se ha adjuntado ningún fichero");

        String contentDisposition = part.getHeaders().getFirst("Content-Disposition");
        String originalName = contentDisposition.replaceAll(".*filename=\"([^\"]+)\".*", "$1");

        Path uploadsDir = Path.of("uploads");

        try {
            if (!Files.exists(uploadsDir)) Files.createDirectories(uploadsDir);

            Document doc = findByIdOptional(id).orElseThrow(() -> new NotFoundException("Documento no encontrado."));

            String oldFilePath = doc.getFile_path();
            if (oldFilePath != null) {
                try {
                    // Borramos el archivo que había antes, si procede
                    Path oldFile = Path.of(oldFilePath);
                    Files.deleteIfExists(oldFile);
                } catch (IOException e) {
                    throw new InternalServerErrorException(e);
                }
            }

            // Creamos un identificador único para el archivo
            String fileName = "doc-" + id + "-" + System.currentTimeMillis() + "-" + originalName;
            Path target = uploadsDir.resolve(fileName);

            try (InputStream in = part.getBody(InputStream.class, null)) {
                Files.copy(in, target);
            }

            doc.setFile_path(target.toString());
            persist(doc);
            return doc;
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }

    /**
     * Obtiene los documentos subidos por un empleado
     * @param id ID del empleado
     * @return lista de documentos
     */
    public List<Document> getByEmp(Long id) {
        if (employeeRepo.findByIdOptional(id).isEmpty()) throw new NotFoundException("Empleado no encontrado");
        return find("employee.id", id).list();
    }

    /**
     * Obtiene los documentos subidos por una subcontrata
     * @param id ID de la empresa subcontratada
     * @return lista de documentos
     */
    public List<Document> getBySub(Long id) {
        if (companyRepo.findByIdOptional(id).isEmpty()) throw new NotFoundException("Empresa no encontrado");
        return find("subcontract.id", id).list();
    }

    /**
     * Actualiza un documento
     * @param id ID del documento
     * @param dto objeto con los campos
     * @return documento actualizado
     */
    @Transactional
    public Document update(Long id, NewDocumentDTO dto) {
        Document doc = findByIdOptional(id).orElseThrow(() -> new NotFoundException("Documento no encontrado."));

        if (dto.getValidationState() != null) doc.setValidation_state(ValidationState.valueOf(dto.getValidationState()));
        if (dto.getName() != null) doc.setName(dto.getName());
        if (dto.getDate() != null) doc.setDate(dto.getDate());
        if (dto.getExpirationDate() != null) doc.setExpiration_date(dto.getExpirationDate());
        if (dto.getValidationDate() != null) doc.setValidation_date(dto.getValidationDate());
        if (dto.getAdditionalInfo() != null) doc.setAdditional_info(dto.getAdditionalInfo());

        persist(doc);

        return doc;
    }

    /**
     * Obtiene el archivo adjunto a un documento
     * @param id ID del documento
     * @return el archivo binario
     */
    public Response downloadFileFromDocument(Long id) {
        Document doc = findByIdOptional(id).orElseThrow(() -> new NotFoundException("Documento no encontrado"));

        if (doc.getFile_path() == null) {
            throw new NotFoundException("No hay archivos adjuntos al documento");
        }

        Path path = Path.of(doc.getFile_path());
        if (!Files.exists(path)) throw new NotFoundException("No se ha encontrado el archivo en el servidor");

        File file = path.toFile();

        // Se devuelve el archivo adjunto en la propia respuesta
        return Response
                .ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + path.getFileName().toString() + "\"")
                .build();
    }

    /**
     * Elimina un documento y su archivo adjunto
     * @param id ID del documento
     * @return entidad eliminada
     */
    @Transactional
    public Document delete(Long id) {
        Document doc = findByIdOptional(id).orElseThrow(() -> new NotFoundException("Documento no encontrado."));

        delete(doc);
        String oldFilePath = doc.getFile_path();
        if (oldFilePath != null) {
            try {
                Path oldFile = Path.of(oldFilePath);
                Files.deleteIfExists(oldFile);
            } catch (IOException e) {
                throw new InternalServerErrorException(e);
            }
        }

        return doc;
    }
}
