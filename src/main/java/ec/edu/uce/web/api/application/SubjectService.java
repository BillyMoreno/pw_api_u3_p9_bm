package ec.edu.uce.web.api.application;

import java.util.List;

import ec.edu.uce.web.api.domain.Subject;
import ec.edu.uce.web.api.infraestructure.SubjectRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class SubjectService {
    @Inject
    SubjectRepository subjectRepository;

    @WithTransaction
    public Uni<List<Subject>> findAll() {
        return subjectRepository.listAll();
    }

    public Uni<Subject> findById(Long id) {
        return subjectRepository.findById(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("Materia no encontrada con ID: " + id));
    }

    @WithTransaction
    public Uni<Subject> save(Subject subject) {
        validateSubjectData(subject);
        return subjectRepository.persist(subject);
    }

    @WithTransaction
    public Uni<List<Subject>> saveAll(List<Subject> subjects) {
        // Validar que la lista no esté vacía
        if (subjects == null || subjects.isEmpty()) {
            throw new BadRequestException("La lista de materias es requerida y no puede estar vacía");
        }

        // Validar cada materia individualmente
        for (Subject subject : subjects) {
            validateSubjectData(subject);
        }

        // Persistir todas las materias de forma transaccional
        return subjectRepository.persist(subjects)
                .replaceWith(subjects);
    }

    @WithTransaction
    public Uni<Subject> update(Long id, Subject subject) {
        validateSubjectData(subject);

        return subjectRepository.findById(id)
                .onItem().transform(existing -> {
                    if (existing == null) {
                        throw new NotFoundException("Materia no encontrada con ID: " + id);
                    }
                    // Actualizar todos los campos
                    existing.name = subject.name;
                    existing.code = subject.code;
                    existing.description = subject.description;
                    existing.credits = subject.credits;
                    existing.semester = subject.semester;
                    existing.teacher = subject.teacher;
                    existing.classroom = subject.classroom;
                    return existing;
                })
                .chain(subjectRepository::persistAndFlush);
    }

    @WithTransaction
    public Uni<Void> partialUpdate(Long id, Subject subject) {
        return subjectRepository.findById(id)
                .onItem().transform(existing -> {
                    if (existing == null) {
                        throw new NotFoundException("Materia no encontrada con ID: " + id);
                    }
                    // Actualizar solo campos no nulos
                    if (subject.name != null) {
                        existing.name = subject.name;
                    }
                    if (subject.code != null) {
                        existing.code = subject.code;
                    }
                    if (subject.description != null) {
                        existing.description = subject.description;
                    }
                    if (subject.credits != null) {
                        existing.credits = subject.credits;
                    }
                    if (subject.semester != null) {
                        existing.semester = subject.semester;
                    }
                    if (subject.teacher != null) {
                        existing.teacher = subject.teacher;
                    }
                    if (subject.classroom != null) {
                        existing.classroom = subject.classroom;
                    }
                    return existing;
                })
                .chain(subjectRepository::persistAndFlush)
                .replaceWithVoid();
    }

    public Uni<List<Subject>> findBySemester(Integer semester) {
        // Validación de rango de semestre
        if (semester < 1 || semester > 10) {
            throw new BadRequestException("Semestre debe estar entre 1 y 10");
        }
        return subjectRepository.find("semester", semester).list()
                .onItem().transform(subjects -> {
                    if (subjects.isEmpty()) {
                        throw new NotFoundException("No se encontraron materias para el semestre " + semester);
                    }
                    return subjects;
                });
    }

    public Uni<List<Subject>> findByClassroom(String classroom) {
        // Validación de aula no vacía
        if (classroom == null || classroom.trim().isEmpty()) {
            throw new BadRequestException("El parámetro aula es requerido y no puede estar vacío");
        }
        return subjectRepository.find("classroom", classroom).list()
                .onItem().transform(subjects -> {
                    if (subjects.isEmpty()) {
                        throw new NotFoundException("No se encontraron materias en el aula '" + classroom + "'");
                    }
                    return subjects;
                });
    }

    @WithTransaction
    public Uni<Void> delete(Long id) {
        return subjectRepository.findById(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("Materia no encontrada con ID: " + id))
                .chain(subject -> subjectRepository.delete(subject))
                .replaceWithVoid();
    }

    /**
     * Método privado para validar datos de negocio de Subject
     */
    private void validateSubjectData(Subject subject) {
        if (subject.name == null || subject.name.trim().isEmpty()) {
            throw new BadRequestException("El nombre es requerido y no puede estar vacío");
        }

        if (subject.code == null || subject.code.trim().isEmpty()) {
            throw new BadRequestException("El código es requerido y no puede estar vacío");
        }

        if (subject.credits != null && subject.credits < 0) {
            throw new BadRequestException("Los créditos no pueden ser negativos");
        }

        if (subject.semester != null && (subject.semester < 1 || subject.semester > 10)) {
            throw new BadRequestException("El semestre debe estar entre 1 y 10");
        }
    }
}
