package org.vedruna.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.vedruna.model.SubcontractingRelationship;
import org.vedruna.model.dto.NewSubcontractingRelationshipDTO;
import org.vedruna.model.help.SubcontractingRelationshipId;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class SubcontractingRelationshipRepository implements PanacheRepository<SubcontractingRelationship> {

    @Inject
    CompanyRepository companyRepository;

    public List<SubcontractingRelationship> findByContractorId(Long contractorId) {
        if (companyRepository.findByIdOptional(contractorId).isEmpty()) throw new NotFoundException("No hay empresas con id " + contractorId + ".");

        return find("contractor.id", contractorId).list();
    }

    public List<SubcontractingRelationship> findBySubcontractId(Long subcontractId) {
        if (companyRepository.findByIdOptional(subcontractId).isEmpty()) throw new NotFoundException("No hay empresas con id " + subcontractId + ".");

        return find("subcontract.id", subcontractId).list();
    }

    public SubcontractingRelationship findByIds(Long contractorId, Long subcontractId) {
        if (companyRepository.findByIdOptional(contractorId).isEmpty()) throw new NotFoundException("No hay empresas con id " + contractorId + ".");
        if (companyRepository.findByIdOptional(subcontractId).isEmpty()) throw new NotFoundException("No hay empresas con id " + subcontractId + ".");

        Optional<SubcontractingRelationship> sr = find("contractor.id = ?1 and subcontract.id = ?2", contractorId, subcontractId).firstResultOptional();
        if (sr.isEmpty()) throw new NotFoundException("No existe relación entre las dos empresas dadas.");

        return sr.get();
    }

    @Transactional
    public SubcontractingRelationship create (Long contractorId, Long subcontractId, NewSubcontractingRelationshipDTO srDTO) {
        try {
            findByIds(contractorId, subcontractId);
            throw new IllegalArgumentException("La relación de contratación ya existe entra las empresas.");
        } catch (NotFoundException e) {
            if (srDTO.getEndDate() != null && srDTO.getStartDate().isAfter(srDTO.getEndDate())) throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la final.");
            if (Objects.equals(contractorId, subcontractId)) throw new IllegalArgumentException("Las empresas deben ser distintas.");

            SubcontractingRelationship newSr = new SubcontractingRelationship(
                    new SubcontractingRelationshipId(contractorId, subcontractId),
                    companyRepository.findById(contractorId),
                    companyRepository.findById(subcontractId),
                    srDTO.getStartDate(),
                    srDTO.getEndDate(),
                    srDTO.getAdditionalInfo()
            );

            persist(newSr);
            return newSr;
        }
    }

    @Transactional
    public SubcontractingRelationship delete (Long contractorId, Long subcontractId) {
        try {
            SubcontractingRelationship sr = findByIds(contractorId, subcontractId);

            delete(sr);
            return sr;
        } catch (NotFoundException e) {
            throw new NotFoundException("La relación de contratación no existe entra las empresas.");
        }
    }

    @Transactional
    public SubcontractingRelationship update (Long contractorId, Long subcontractId, NewSubcontractingRelationshipDTO srDTO) {
        try {
            SubcontractingRelationship sr = findByIds(contractorId, subcontractId);

            if (srDTO.getStartDate() != null) sr.setStart_date(srDTO.getStartDate());
            if (srDTO.getEndDate() != null) sr.setEnd_date(srDTO.getEndDate());
            if (srDTO.getAdditionalInfo() != null) sr.setAdditional_info(srDTO.getAdditionalInfo());

            if (sr.getEnd_date() != null && sr.getStart_date().isAfter(sr.getEnd_date())) throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la final.");
            if (Objects.equals(contractorId, subcontractId)) throw new IllegalArgumentException("Las empresas deben ser distintas.");

            persist(sr);
            return sr;
        } catch (NotFoundException e) {
            throw new NotFoundException("La relación de contratación no existe entra las empresas.");
        }
    }
}
