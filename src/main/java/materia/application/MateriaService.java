package materia.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import materia.domain.Estudiante;
import materia.domain.Materia;
import materia.infraestructure.MateriaRepository;

@ApplicationScoped
public class MateriaService {

    @Inject
    private MateriaRepository materiaRepository;

    public List<Materia> listaTodos(){
       return this.materiaRepository.listAll();
    }

    public Materia consultarPorId(Integer id){
        return this.materiaRepository.findById(id.longValue());
    }

    @Transactional
    public void crear(Materia materia){
        this.materiaRepository.persist(materia);
    }

    @Transactional
    public void actualizar(Integer id, Materia materia){
        Materia mat = this.consultarPorId(id);
        // actualizar con los valores recibidos
        mat.setNombre(mat.getNombre());
        mat.setCreditos(mat.getCreditos());
    }


    @Transactional
    public void actualizarParcial(Integer id, Materia materia){
        Materia mat = this.consultarPorId(id);
    if (materia.getNombre() != null) {
        mat.setNombre(mat.getNombre());
    }
    if (materia.getCreditos() != null) {
        mat.setCreditos(mat.getCreditos());
    }
    }

    @Transactional
    public void eliminar(Integer id){
        this.materiaRepository.deleteById(id.longValue());
    }

 
    public List<Materia> buscarPorNombre(String nombre){
        return this.materiaRepository.find("nombre=?1", nombre).list();
    }


    public List<Materia> buscarPorCreditos(String creditos){
        return this.materiaRepository.find("creditos=?1",creditos).list();
    }

    
}
