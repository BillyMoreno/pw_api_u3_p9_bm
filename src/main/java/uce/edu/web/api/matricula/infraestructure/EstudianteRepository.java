package materia.infraestructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import materia.domain.Estudiante;
import materia.domain.Materia;

@ApplicationScoped
public class EstudianteRepository implements PanacheRepository<Estudiante>{

}
