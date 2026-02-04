package ec.edu.uce.web.api.infraestructure;

import ec.edu.uce.web.api.domain.Student;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {

}

// aqui ya esta constrido el CRUD basico con PanacheRepository