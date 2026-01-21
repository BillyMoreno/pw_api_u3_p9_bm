package ec.edu.uce.web.api.interfaces;

import java.util.List;
import java.util.Map;

import ec.edu.uce.web.api.application.SubjectService;
import ec.edu.uce.web.api.domain.Subject;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/materias")
@Produces(MediaType.APPLICATION_JSON)
public class SubjectResource {
    
    @Inject
    SubjectService subjectService;
    
    // ✅ 1. GET todas las materias
    @GET
    public Uni<List<Subject>> getAllSubjects() {
        return subjectService.findAll();
    }
    
    // ✅ 2. GET materia por ID
    @GET
    @Path("/{id}")
    public Uni<Subject> getSubjectById(@PathParam("id") Long id) {
        return subjectService.findById(id);
    }
    
    // ✅ 3. POST crear nueva materia
    @POST
    public Uni<Subject> createSubject(Subject subject) {
        return subjectService.save(subject);
    }
    
    // ✅ 4. PUT actualizar materia
    @PUT
    @Path("/{id}")
    public Uni<Subject> updateSubject(@PathParam("id") Long id, Subject subject) {
        return subjectService.update(id, subject);
    }
    
    // ✅ 5. DELETE eliminar materia
    @DELETE
    @Path("/{id}")
    public Uni<Boolean> deleteSubject(@PathParam("id") Long id) {
        return subjectService.delete(id);
    }
    
    // ✅ 6. POST inscribir estudiante (texto simple)
    @POST
    @Path("/{subjectId}/inscribir/{studentId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> enrollStudent(
            @PathParam("subjectId") Long subjectId,
            @PathParam("studentId") Long studentId) {
        return subjectService.enrollStudent(subjectId, studentId);
    }
    
    // ✅ 7. GET estudiantes inscritos (datos simulados)
    @GET
    @Path("/{id}/estudiantes")
    public Uni<List<Map<String, Object>>> getEnrolledStudents(@PathParam("id") Long id) {
        return subjectService.getEnrolledStudents(id);
    }
    
    // ✅ 8. GET información de materia
    @GET
    @Path("/{id}/info")
    public Uni<Map<String, Object>> getSubjectInfo(@PathParam("id") Long id) {
        return subjectService.getSubjectInfo(id);
    }
}