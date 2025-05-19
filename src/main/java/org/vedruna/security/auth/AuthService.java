package org.vedruna.security.auth;

import java.util.Optional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.vedruna.model.Employee;
import org.vedruna.model.User;
import org.vedruna.model.dto.RegisterDTO;
import org.vedruna.repository.CompanyRepository;
import org.vedruna.repository.EmployeeRepository;
import org.vedruna.repository.UserRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class AuthService {

    @Inject
    CompanyRepository companyRepository;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    UserRepository userRepository;

    @Transactional
    public Employee register(RegisterDTO registerDTO) {

        if (userRepository.find("email", registerDTO.getEmail()).firstResult() != null)
            throw new IllegalArgumentException("Este email ya está registrado.");

        if (employeeRepository.find("nif", registerDTO.getNif()).firstResult() != null)
            throw new IllegalArgumentException("Este NIF ya está registrado.");

        Employee employee = new Employee(
                registerDTO.getNif(),
                registerDTO.getName(),
                registerDTO.getSurname(),
                registerDTO.isActive(),
                registerDTO.getCountry(),
                registerDTO.getStart_date(),
                null,
                registerDTO.getJob(),
                registerDTO.getDepartment(),
                null,
                companyRepository.findByIdOptional(registerDTO.getCompany_id())
                        .orElseThrow(() -> new NotFoundException("Empresa no encontrada."))
        );

        employeeRepository.persist(employee);

        Employee persisted = employeeRepository.findByNif(employee.getNif()).orElseThrow(() -> new NotFoundException("Empleado no encontrado."));

        User user = new User(
                employeeRepository.findByIdOptional(persisted.getId()).orElseThrow(() -> new NotFoundException("Empleado no encontrado.")),
                hashPassword(registerDTO.getPassword()),
                registerDTO.getEmail()
        );

        userRepository.persist(user);

        return persisted;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.find("email", Sort.by("id"), email).firstResultOptional();
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String raw, String hashed) {
        return BCrypt.checkpw(raw, hashed);
    }
}
