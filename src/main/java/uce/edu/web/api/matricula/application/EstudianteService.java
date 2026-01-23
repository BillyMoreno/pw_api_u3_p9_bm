package uce.edu.web.api.matricula.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.domain.Estudiante;
import uce.edu.web.api.matricula.infraestrcture.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {
    @Inject
    public EstudianteRepository estudianteRepository;
    public List<Estudiante>listarTodos(){
         System.out.println("Listado de estudiantes:xxxxxxx " );
       return this.estudianteRepository.listAll();
      

    }

    @Transactional
    public void saveEstudiante(Estudiante estudiante){
        estudianteRepository.persist(estudiante);
    }
    public Estudiante consultarEstudiante(Integer id){
        return this.estudianteRepository.findById(id.longValue());
    }
    // actualizar estudiante
    @Transactional
    public void actualizarEstudiante(Integer id,Estudiante estudiante){
        Estudiante estudianteActual = this.estudianteRepository.findById(id.longValue());
        if(estudianteActual != null){
            estudianteActual.setNombre(estudiante.getNombre());
            estudianteActual.setApellido(estudiante.getApellido());
            estudianteActual.setFechaNacimiento(estudiante.getFechaNacimiento());
        }
       
    }
    // actualizacion parcial
    @Transactional
    public void actualizarParcialEstudiante(Integer id,Estudiante estudiante){
        Estudiante estudianteActual = this.estudianteRepository.findById(id.longValue());
        if(estudianteActual != null){
            if(estudiante.getNombre() != null){
                estudianteActual.setNombre(estudiante.getNombre());
            }
            if(estudiante.getApellido() != null){
                estudianteActual.setApellido(estudiante.getApellido());
            }
            if(estudiante.getFechaNacimiento() != null){
                estudianteActual.setFechaNacimiento(estudiante.getFechaNacimiento());
            }
        }
    }
    // eliminar estudiante
    @Transactional
    public void eliminarEstudiante(Integer id){
        Estudiante estudianteActual = this.estudianteRepository.findById(id.longValue());
        if(estudianteActual != null){
            this.estudianteRepository.delete(estudianteActual);
        }
    }

    // consultar por provincia y genero
    public List<Estudiante> buscarPorProvincia(String provincia, String genero){
        if (genero != null && !genero.isEmpty()) {
             System.out.println("Listado de provincia:xxxxxxx " );
            return this.estudianteRepository.find("provinciaa = ?1 and genero = ?2", provincia, genero).list();
        }
        return this.estudianteRepository.find("provinciaa", provincia).list();
    }
}
