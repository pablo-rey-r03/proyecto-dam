package org.vedruna.repository;

import java.util.Optional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.vedruna.model.Employee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.vedruna.model.dto.NewEmployeeDTO;
import org.vedruna.model.enums.Country;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

    @Inject
    CompanyRepository companyRepo;

    public Optional<Employee> findByNif(String nif) {
        return find("nif", nif).firstResultOptional();
    }

    @Transactional
    public Employee create(NewEmployeeDTO dto) {
        if (findByNif(dto.getNif()).isPresent()) throw new IllegalArgumentException("Ya existe un empleado con este NIF.");

        if (dto.getEndDate() != null && dto.getStartDate().isAfter(dto.getEndDate())) throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la final.");

        Employee employee = new Employee(
                dto.getNif(),
                dto.getName(),
                dto.getSurname(),
                dto.getActive(),
                Country.valueOf(dto.getCountry()),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getJob(),
                dto.getDepartment(),
                dto.getAdditionalInfo(),
                companyRepo.findByIdOptional(dto.getCompanyId()).orElseThrow(NotFoundException::new)
        );

        persist(employee);
        return employee;
    }
}
