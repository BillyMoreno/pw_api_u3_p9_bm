package ec.edu.uce.web.api.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ec.edu.uce.web.api.domain.Subject;
import ec.edu.uce.web.api.infraestructure.SubjectRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SubjectService {
    
    @Inject
    SubjectRepository subjectRepository;
    
    // 1. Obtener todas las materias
    public Uni<List<Subject>> findAll() {
        return subjectRepository.listAll();
    }
    
    // 2. Obtener materia por ID
    public Uni<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }
    
    // 3. Crear nueva materia
    @WithTransaction
    public Uni<Subject> save(Subject subject) {
        return subjectRepository.persist(subject);
    }
    
    // 4. Actualizar materia
    @WithTransaction
    public Uni<Subject> update(Long id, Subject subject) {
        return subjectRepository.findById(id)
            .onItem().transformToUni(existing -> {
                if (existing == null) {
                    throw new jakarta.ws.rs.NotFoundException("Materia no encontrada");
                }
                existing.code = subject.code;
                existing.name = subject.name;
                existing.credits = subject.credits;
                existing.description = subject.description;
                existing.isActive = subject.isActive;
                return subjectRepository.persist(existing);
            });
    }
    
    // 5. Eliminar materia
    @WithTransaction
    public Uni<Boolean> delete(Long id) {
        return subjectRepository.deleteById(id);
    }
    
    // 6. Inscribir estudiante (MUY SIMPLE)
    @WithTransaction
    public Uni<String> enrollStudent(Long subjectId, Long studentId) {
        return subjectRepository.findById(subjectId)
            .onItem().transform(subject -> {
                if (subject == null) {
                    return "❌ Materia no encontrada";
                }
                // Solo mensaje - la inscripción real sería con SQL
                return "✅ Inscripción simulada - Use SQL directo para implementación real";
            });
    }
    
    // 7. Estudiantes inscritos (MUY SIMPLE - sin relación)
    public Uni<List<Map<String, Object>>> getEnrolledStudents(Long subjectId) {
        return Uni.createFrom().item(() -> {
            List<Map<String, Object>> estudiantes = new ArrayList<>();
            
            // Datos de ejemplo (simulado)
            Map<String, Object> e1 = new HashMap<>();
            e1.put("id", 1);
            e1.put("name", "Juan Pérez");
            e1.put("email", "juan@email.com");
            estudiantes.add(e1);
            
            Map<String, Object> e2 = new HashMap<>();
            e2.put("id", 2);
            e2.put("name", "María Gómez");
            e2.put("email", "maria@email.com");
            estudiantes.add(e2);
            
            return estudiantes;
        });
    }
    
    // 8. Información simple
    public Uni<Map<String, Object>> getSubjectInfo(Long id) {
        return subjectRepository.findById(id)
            .onItem().transform(subject -> {
                Map<String, Object> info = new HashMap<>();
                if (subject != null) {
                    info.put("id", subject.id);
                    info.put("code", subject.code);
                    info.put("name", subject.name);
                    info.put("credits", subject.credits);
                    info.put("isActive", subject.isActive);
                    info.put("message", "✅ Materia encontrada");
                } else {
                    info.put("error", "❌ Materia no encontrada");
                }
                return info;
            });
    }
}