package ec.edu.uce.web.api.infraestructure;

import ec.edu.uce.web.api.domain.Subject;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SubjectRepository implements PanacheRepository<Subject> {
    // Básico - hereda todo de PanacheRepository
}