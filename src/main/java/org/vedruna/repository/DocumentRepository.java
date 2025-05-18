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

    @Transactional
    public Document create(NewDocumentDTO dto) {
        if (dto.getExpirationDate() != null && dto.getDate().isAfter(dto.getExpirationDate())) throw new IllegalArgumentException("La fecha de emisión no puede ser posterior a su fecha de expiración.");
        if (dto.getValidationDate() != null && dto.getDate().isAfter(dto.getValidationDate())) throw new IllegalArgumentException("La fecha de validación no puede ser posterior a su fecha de expiración.");

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

    @Transactional
    public Document addFile(Long id, InputPart part) {
        if (part == null) throw new BadRequestException("No se ha adjuntado ningún fichero.");

        String contentDisposition = part.getHeaders().getFirst("Content-Disposition");
        String originalName = contentDisposition.replaceAll(".*filename=\"([^\"]+)\".*", "$1");

        Path uploadsDir = Path.of("uploads");

        try {
            if (!Files.exists(uploadsDir)) Files.createDirectories(uploadsDir);

            Document doc = findByIdOptional(id).orElseThrow(() -> new NotFoundException("Documento no encontrado."));

            String oldFilePath = doc.getFile_path();
            if (oldFilePath != null) {
                try {
                    Path oldFile = Path.of(oldFilePath);
                    Files.deleteIfExists(oldFile);
                } catch (IOException e) {
                    throw new InternalServerErrorException(e);
                }
            }

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

    public List<Document> getByEmp(Long id) {
        if (employeeRepo.findByIdOptional(id).isEmpty()) throw new NotFoundException("Empleado no encontrado.");
        return find("employee.id", id).list();
    }

    public List<Document> getBySub(Long id) {
        if (companyRepo.findByIdOptional(id).isEmpty()) throw new NotFoundException("Empresa no encontrado.");
        return find("subcontract.id", id).list();
    }

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

    public Response downloadFileFromDocument(Long id) {
        Document doc = findByIdOptional(id).orElseThrow(() -> new NotFoundException("Documento no encontrado."));

        if (doc.getFile_path() == null) {
            throw new NotFoundException("No hay archivos adjuntos al documento.");
        }

        Path path = Path.of(doc.getFile_path());
        if (!Files.exists(path)) throw new NotFoundException("No se ha encontrado el archivo en el servidor.");

        File file = path.toFile();

        return Response
                .ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + path.getFileName().toString() + "\"")
                .build();
    }

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
