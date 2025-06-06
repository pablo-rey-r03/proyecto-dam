package org.vedruna.repository;

import java.time.LocalDate;
import java.util.List;
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

    /**
     * Crea un nuevo empleado
     * @param dto Objeto con campos
     * @return nueva entidad
     */
    @Transactional
    public Employee create(NewEmployeeDTO dto) {
        if (findByNif(dto.getNif()).isPresent()) throw new IllegalArgumentException("Ya existe un empleado con este NIF");

        if (dto.getStartDate().isAfter(LocalDate.now())) throw new IllegalArgumentException("La fecha de contratación no puede ser futura");

        if (dto.getEndDate() != null && dto.getStartDate().isAfter(dto.getEndDate())) throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la final");

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
                companyRepo.findByIdOptional(dto.getCompanyId()).orElseThrow(() -> new NotFoundException("Empresa no encontrada."))
        );

        persist(employee);
        return employee;
    }

    /**
     * Actualiza un empleado
     * @param dto Objeto con campos
     * @param id ID del empleado
     * @return entidad actualizada
     */
    @Transactional
    public Employee update(NewEmployeeDTO dto, Long id) {
        Employee employee = findById(id);

        if (dto.getStartDate().isAfter(LocalDate.now())) throw new IllegalArgumentException("La fecha de contratación no puede ser futura");

        if (dto.getEndDate() != null && dto.getStartDate().isAfter(dto.getEndDate())) throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la final");

        employee.setNif(dto.getNif());
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurname());
        employee.setActive(dto.getActive());
        employee.setCountry(Country.valueOf(dto.getCountry()));
        employee.setStart_date(dto.getStartDate());
        employee.setEnd_date(dto.getEndDate());
        employee.setJob(dto.getJob());
        employee.setDepartment(dto.getDepartment());
        employee.setAdditional_info(dto.getAdditionalInfo());

        persist(employee);
        return employee;
    }

    /**
     * Elimina un empleado
     * @param id ID del empleado
     * @return empleado eliminado
     */
    @Transactional
    public Employee delete(Long id) {
        if (findByIdOptional(id).isEmpty()) throw new NotFoundException("No se ha encontrado un empleado con id " + id);

        Employee employee = findById(id);
        delete(employee);
        return employee;
    }

    public List<Employee> getByCompany(Long id) {
        if (companyRepo.findByIdOptional(id).isEmpty()) throw new NotFoundException("No se ha encontrado una empresa con id " + id);

        return find("company.id", id).list();
    }
}
