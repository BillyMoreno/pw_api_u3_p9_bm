package materia.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import materia.application.representation.EstudianteRepresentation;
import materia.domain.Estudiante;
import materia.infraestructure.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;

    public List<EstudianteRepresentation> listarTodos() {
        List<EstudianteRepresentation> list = new ArrayList<>();
        for (Estudiante estu : estudianteRepository.listAll()) {
            list.add(mapperToEr(estu));
        }
        return list;
    }

    public EstudianteRepresentation consultarPorId(Integer id) {
        Estudiante est = estudianteRepository.findById(id.longValue());
        if (est == null) return null;
        return mapperToEr(est);
    }

    @Transactional
    public void crear(EstudianteRepresentation estuR) {
        estudianteRepository.persist(mapperToEstudiante(estuR));
    }

    @Transactional
    public EstudianteRepresentation actualizar(Integer id, EstudianteRepresentation estuR) {
        Estudiante estu = estudianteRepository.findById(id.longValue());
        if (estu == null) return null;
        estu.setNombre(estuR.getNombre());
        estu.setApellido(estuR.getApellido());
        estu.setFechaNacimiento(estuR.getFechaNacimiento());
        estu.setProvincia(estuR.getProvincia());
        estu.genero = estuR.getGenero();
        return mapperToEr(estu);
    }

    @Transactional
    public void actualizarParcial(Integer id, EstudianteRepresentation estuR) {
        Estudiante estu = estudianteRepository.findById(id.longValue());
        if (estu == null) return;
        if (estuR.getNombre() != null) estu.setNombre(estuR.getNombre());
        if (estuR.getApellido() != null) estu.setApellido(estuR.getApellido());
        if (estuR.getFechaNacimiento() != null) estu.setFechaNacimiento(estuR.getFechaNacimiento());
        if (estuR.getProvincia() != null) estu.setProvincia(estuR.getProvincia());
        if (estuR.getGenero() != null) estu.genero = estuR.getGenero();
    }

    @Transactional
    public boolean eliminar(Integer id) {
        return estudianteRepository.deleteById(id.longValue());
    }

    public List<Estudiante> buscarPorProvincia(String provincia, String genero) {
        return estudianteRepository.find("provincia = ?1 and genero = ?2", provincia, genero).list();
    }

    // Mapper privado, solo usado dentro del Service
    private EstudianteRepresentation mapperToEr(Estudiante estu) {
        EstudianteRepresentation estuR = new EstudianteRepresentation();
        estuR.setId(estu.getId());
        estuR.setNombre(estu.getNombre());
        estuR.setApellido(estu.getApellido());
        estuR.setFechaNacimiento(estu.getFechaNacimiento());
        estuR.setProvincia(estu.getProvincia());
        estuR.setGenero(estu.genero);
        return estuR;
    }

    private Estudiante mapperToEstudiante(EstudianteRepresentation estuR) {
        Estudiante estu = new Estudiante();
        estu.setId(estuR.getId());
        estu.setNombre(estuR.getNombre());
        estu.setApellido(estuR.getApellido());
        estu.setFechaNacimiento(estuR.getFechaNacimiento());
        estu.setProvincia(estuR.getProvincia());
        estu.genero = estuR.getGenero();
        return estu;
    }
}
