package org.vedruna.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.vedruna.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

}
