package org.vedruna.repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.vedruna.model.Document;
import org.vedruna.model.dto.NewDocumentDTO;
import org.vedruna.model.enums.AddresseeType;
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

        if ("EMP".equals(dto.getAddresseeType()) && dto.getEmployeeId() == null) throw new BadRequestException("Si el documento es de un empleado, debe incluir su ID.");
        if ("COM".equals(dto.getAddresseeType()) && dto.getEmployeeId() != null) throw new BadRequestException("Si el documento es de una subcontrata, un ID de empleado no procede.");

        Document doc = new Document(
                ValidationState.valueOf(dto.getValidationState()),
                companyRepo.findByIdOptional(dto.getContractorId()).orElseThrow(() -> new NotFoundException("Empresa no encontrada.")),
                companyRepo.findByIdOptional(dto.getSubcontractId()).orElseThrow(() -> new NotFoundException("Empresa no encontrada.")),
                AddresseeType.valueOf(dto.getAddresseeType()),
                dto.getName(),
                dto.getDate(),
                dto.getExpirationDate(),
                dto.getValidationDate(),
                employeeRepo.findByIdOptional(dto.getEmployeeId()).orElseThrow(() -> new NotFoundException("Empleado no encontrado.")),
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

            String fileName = "doc-" + id + "-" + System.currentTimeMillis() + "-" + originalName;
            Path target = uploadsDir.resolve(fileName);

            try (InputStream in = part.getBody(InputStream.class, null)) {
                Files.copy(in, target);
            }

            Document doc = findByIdOptional(id).orElseThrow(() -> new NotFoundException("Documento no encontrado."));
            doc.setFile_path(target.toString());
            persist(doc);
            return doc;
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }
}
