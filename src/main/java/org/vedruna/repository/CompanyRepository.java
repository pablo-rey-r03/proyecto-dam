package org.vedruna.repository;

import java.util.Optional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.vedruna.model.Company;
import org.vedruna.model.dto.NewCompanyDTO;
import org.vedruna.model.enums.Country;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CompanyRepository implements PanacheRepository<Company> {

    /**
     * Obtiene un Optional de empresa
     * @param id el ID de la empresa
     * @return optional de empresa
     */
    public Optional<Company> getCompanyById(Long id) {
        return findByIdOptional(id);
    }

    /**
     * Crea una nueva empresa
     * @param dto objeto con los campos
     * @return entidad creada
     * @throws IllegalArgumentException al obtener el valor del código de país inexistente
     */
    @Transactional
    public Company create(NewCompanyDTO dto) {
        try {
            Country.valueOf(dto.getCountry());

            Company company = new Company
                    (dto.getCif(), dto.getName(), Country.valueOf(dto.getCountry()), dto.getAddress());

            persist(company);

            return company;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Elimina una empresa
     * @param id ID de la empresa
     * @return la entidad eliminada
     */
    @Transactional
    public Company delete(Long id) {
        Company company = findByIdOptional(id).orElseThrow(() -> new NotFoundException("Empresa no encontrada."));
        delete(company);
        return company;
    }

    /**
     * Actualiza una empresa
     * @param id ID de la empresa
     * @param companyDTO objeto con los campos
     * @return la entidad actualizada
     */
    @Transactional
    public Company update(Long id, NewCompanyDTO companyDTO) {
        Optional<Company> opt = findByIdOptional(id);
        if (opt.isEmpty()) throw new NotFoundException("No hay empresas con el id " + id);

        Company company = opt.get();
        if (companyDTO.getName() != null) company.setName(companyDTO.getName());
        if (companyDTO.getCif() != null) company.setCif(companyDTO.getCif());
        if (companyDTO.getAddress() != null) company.setAddress(companyDTO.getAddress());
        if (companyDTO.getCountry() != null) company.setCountry(Country.valueOf(companyDTO.getCountry()));

        persist(company);

        return company;
    }
}
