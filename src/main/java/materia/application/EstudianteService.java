package materia.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import materia.application.representation.EstudianteRepresentation;
import materia.domain.Estudiante;
import materia.domain.Materia;
import materia.infraestructure.EstudianteRepository;
import materia.infraestructure.MateriaRepository;
import materia.interfaces.EstudianteResource;

@ApplicationScoped
public class EstudianteService {
 
    @Inject
    private EstudianteRepository estudianteRepository;
 
    public List<EstudianteRepresentation> listarTodos() {
        List<EstudianteRepresentation> list = new ArrayList<>();
        for(Estudiante estu:this.estudianteRepository.listAll())
            list.add(this.mapperToEr(estu));
        return list;
    }
 
    public Estudiante consultarPorId(Integer id){
        return this.estudianteRepository.findById(id.longValue());
    }

    @Transactional
    public void crear(EstudianteRepresentation estu){
        this.estudianteRepository.persist(this.mapperToEstudiante(estu));
    }
 
    @Transactional
    public void actualizar(Integer id, Estudiante est){
        Estudiante estu = this.consultarPorId(id);
        estu.setApellido(est.getApellido());
        estu.setNombre(est.getNombre());
        estu.setFechaNacimiento(est.getFechaNacimiento());
    }
 
    @Transactional
    public void actualizarParcial(Integer id, Estudiante est){
        Estudiante estu=this.consultarPorId(id);
        if(est.getNombre()!=null){
            estu.setNombre(est.getNombre());
        }
        if(est.getApellido()!=null){
            estu.setApellido(est.getApellido());
        }
        if(est.getFechaNacimiento()!=null){
            estu.setFechaNacimiento(est.getFechaNacimiento());
        }
    }
   
    @Transactional
    public void eliminar(Integer id){
        this.estudianteRepository.deleteById(id.longValue());
    }
 
    public List<Estudiante> buscarPorProvincia(String provincia, String genero){
       // return this.estudianteRepository.find("provincia", provincia).list();
       return this.estudianteRepository.find("provincia= ?1 and genero=?2", provincia, genero).list();
       
    }

    private EstudianteRepresentation mapperToEr(Estudiante estu){
        EstudianteRepresentation estuR = new EstudianteRepresentation();
        estuR.setId(estuR.getId());
        estuR.setNombre(estuR.getNombre());
        estuR.setApellido(estuR.getApellido());
        estuR.setFechaNacimiento(estuR.getFechaNacimiento());
        estuR.setGenero(estuR.getGenero());
        estuR.setProvincia(estuR.getProvincia());
        return estuR;
    }

     private Estudiante mapperToEstudiante(EstudianteRepresentation estu){
        Estudiante estuR = new Estudiante();
        estuR.setId(estuR.getId());
        estuR.setNombre(estuR.getNombre());
        estuR.setApellido(estuR.getApellido());
        estuR.setFechaNacimiento(estuR.getFechaNacimiento());
        estuR.genero = estuR.genero;
        estuR.setProvincia(estuR.getProvincia());
        return estuR;
    }
}
