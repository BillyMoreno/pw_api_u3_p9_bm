package ec.edu.uce.web.api.infraestructure;

import java.util.List;

import ec.edu.uce.web.api.domain.Son;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SonRepository implements PanacheRepository<Son> {
    public Uni<List<Son>> findByStudentId(Long studentId) {
        return find("student.id", studentId).list();
    }
}
