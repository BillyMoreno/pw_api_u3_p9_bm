package ec.edu.uce.web.api.application;

import java.util.List;

import ec.edu.uce.web.api.domain.Student;
import ec.edu.uce.web.api.infraestructure.StudentRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StudentService {
    @Inject
    StudentRepository studentRepository;

    @WithTransaction
    public Uni<List<Student>> findAll() {
        return studentRepository.listAll();
    }

    public Uni<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @WithTransaction
    public Uni<Student> save(Student student) {
        return studentRepository.persist(student);
    }

    @WithTransaction
    public Uni<Student> update(Long id, Student student) {
        return studentRepository.findById(id)
                .onItem().transform(existing -> {
                    if (existing == null) {
                        throw new jakarta.ws.rs.NotFoundException("Student not found");
                    }
                    existing.name = student.name;
                    existing.lastName = student.lastName;
                    existing.email = student.email;
                    existing.birthDay = student.birthDay;
                    return existing;
                })
                .chain(studentRepository::persistAndFlush);
    }

    @WithTransaction
    public Uni<Void> partialUpdate(Long id, Student student) {
        return studentRepository.findById(id)
                .onItem().transform(existing -> {
                    if (existing == null) {
                        throw new jakarta.ws.rs.NotFoundException("Student not found");
                    }
                    if (student.name != null) {
                        existing.name = student.name;
                    }
                    if (student.lastName != null) {
                        existing.lastName = student.lastName;
                    }
                    if (student.email != null) {
                        existing.email = student.email;
                    }
                    if (student.birthDay != null) {
                        existing.birthDay = student.birthDay;
                    }
                    return existing;
                })
                .chain(studentRepository::persistAndFlush)
                .replaceWithVoid();
    }

    @WithTransaction
    public Uni<Boolean> delete(Long id) {
        return studentRepository.deleteById(id);
    }
}
