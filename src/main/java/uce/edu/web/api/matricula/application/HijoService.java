package materia.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import materia.application.representation.HijoRepresentation;
import materia.domain.Hijo;
import materia.infraestructure.HijoRepository;

@ApplicationScoped
public class HijoService {

    @Inject
    private HijoRepository hijoRepository;

    public List<HijoRepresentation> buscarPorIdEstudiante(Integer idEstudiante) {
        List<HijoRepresentation> lista = new ArrayList<>();
        for (Hijo h : hijoRepository.buscarPorIdEstudiante(idEstudiante)) {
            lista.add(mapperToHijoR(h));
        }
        return lista;
    }

    private HijoRepresentation mapperToHijoR(Hijo hijo) {
        HijoRepresentation hr = new HijoRepresentation();
        hr.id = hijo.id;
        hr.nombre = hijo.nombre;
        hr.apellido = hijo.apellido;
        return hr;
    }
}
