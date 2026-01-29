package materia.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import materia.domain.Hijo;
import materia.infraestructure.HijoRepository;

@ApplicationScoped
public class HijoService {


    @Inject
    private HijoRepository hijoRepository;

    public List<Hijo> buscarPorIdEstudiante(Integer idEstudiante){
        return this.hijoRepository.buscarPorIdEstudiante(idEstudiante);
    }

}
