package org.vedruna.repository;

import java.util.Optional;
import jakarta.enterprise.context.ApplicationScoped;
import org.vedruna.model.Employee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {
    public Optional<Employee> findByNif(String nif) {
        return find("nif", nif).firstResultOptional();
    }
}
