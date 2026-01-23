package uce.edu.web.api.matricula.infraestrcture;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.web.api.matricula.domain.Materia;
@ApplicationScoped
public class MateriaRepository implements PanacheRepository<Materia> {
}
