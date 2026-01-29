package materia.infraestructure;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import materia.domain.Hijo;

@ApplicationScoped
public class HijoRepository implements PanacheRepository<Hijo>{

    public List<Hijo> buscarPorIdEstudiante(Integer idEstudiante){
        return find("estudiante.id", idEstudiante).list();
    }
}
