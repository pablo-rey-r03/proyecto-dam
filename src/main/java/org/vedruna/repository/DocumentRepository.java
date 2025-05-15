package org.vedruna.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.vedruna.model.Document;

@ApplicationScoped
public class DocumentRepository implements PanacheRepository<Document> {

}
