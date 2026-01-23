package uce.edu.web.api.matricula.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.domain.Materia;
import uce.edu.web.api.matricula.infraestrcture.MateriaRepository;

import java.util.List;

@ApplicationScoped
public class MateriaService {
    @Inject
    private MateriaRepository materiaRepository;
    // listar todas las materias
    public Iterable<Materia> listarMaterias(){
        return this.materiaRepository.listAll();
    }
    // registrar materia
    @Transactional
    public void agregarMateria(Materia materia){
        materiaRepository.persist(materia);
    }
    // consultar materia por id
    public Materia consultarMateriaId(Integer id){
        return this.materiaRepository.findById(id.longValue());
    }
    // actualizar materia por id
    @Transactional
    public void actualizarMateria(Integer id,Materia materia){
        Materia materiaActual = consultarMateriaId(id);
        if(materiaActual != null){
            materiaActual.setNombreMateria(materia.getNombreMateria());
            materiaActual.setCreditosMateria(materia.getCreditosMateria());
            materiaActual.setNumSemestre(materia.getNumSemestre());
        }
    }
    // actulizacion de forma parcial
    @Transactional
    public void actualizarParcialMateria(Integer id,Materia materia){
        Materia materiaActual = consultarMateriaId(id);
        if(materiaActual != null){
            if(materia.getNombreMateria() != null){
                materiaActual.setNombreMateria(materia.getNombreMateria());
            }
            if (materia.getNumSemestre() != null){
                materiaActual.setNumSemestre(materia.getNumSemestre());
            }
            if(materia.getCreditosMateria() != 0){
                materiaActual.setCreditosMateria(materia.getCreditosMateria());

            }

        }
    }
    // eliminar materia
    @Transactional
    public void eliminarMateria(Integer id){
        Materia materiaActual = consultarMateriaId(id);
        if(materiaActual != null){
            this.materiaRepository.delete(materiaActual);
        }
    }
    // buscar por nombre de materia
    public Materia buscarPorNombre(String nombre){
        return this.materiaRepository.find("nombreMateria",nombre).firstResult();
    }
    // listar materias por numero de semestre
    public List<Materia> listarMateriasPorSemestre(String numSemestre){
        return this.materiaRepository.find("numSemestre",numSemestre).list();
    }
}
