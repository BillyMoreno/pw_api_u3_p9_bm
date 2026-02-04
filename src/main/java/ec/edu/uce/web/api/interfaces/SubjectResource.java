package ec.edu.uce.web.api.interfaces;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import ec.edu.uce.web.api.application.SubjectService;
import ec.edu.uce.web.api.domain.Subject;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/matricula/materias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("admin")
public class SubjectResource {
    
    @Inject
    private SubjectService subjectService;
    
    @Inject
    JsonWebToken jwt;

    @GET
    public Uni<Response> getAllSubjects() {
        System.out.println("Acceso a materias por: " + jwt.getName());
        
        return subjectService.findAll()
                .map(subjects -> Response.ok(subjects).build())
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(new ErrorResponse("Error", throwable.getMessage()))
                            .build();
                });
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getSubjectById(@PathParam("id") Long id) {
        try {
            validateId(id);
        } catch (BadRequestException e) {
            return Uni.createFrom().item(
                Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("ID inválido", e.getMessage()))
                    .build()
            );
        }
        
        return subjectService.findById(id)
                .map(subject -> Response.ok(subject).build())
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("No encontrado", throwable.getMessage()))
                            .build();
                });
    }

    @POST
    public Uni<Response> createSubject(Subject subject) {
        try {
            validateBody(subject);
        } catch (BadRequestException e) {
            return Uni.createFrom().item(
                Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Datos inválidos", e.getMessage()))
                    .build()
            );
        }
        
        return subjectService.save(subject)
                .map(savedSubject -> {
                    return Response.status(Response.Status.CREATED)
                            .entity(savedSubject)
                            .build();
                })
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Error", throwable.getMessage()))
                            .build();
                });
    }

    @POST
    @Path("/guardarmultiplesmaterias")
    public Uni<Response> createMultipleSubjects(List<Subject> subjects) {
        if (subjects == null || subjects.isEmpty()) {
            return Uni.createFrom().item(
                Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Datos inválidos", "La lista de materias no puede estar vacía"))
                    .build()
            );
        }
        
        return subjectService.saveAll(subjects)
                .map(savedSubjects -> Response.ok(savedSubjects).build())
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Error", throwable.getMessage()))
                            .build();
                });
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> updateSubject(@PathParam("id") Long id, Subject subject) {
        try {
            validateId(id);
            validateBody(subject);
        } catch (BadRequestException e) {
            return Uni.createFrom().item(
                Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Datos inválidos", e.getMessage()))
                    .build()
            );
        }
        
        return subjectService.update(id, subject)
                .map(updatedSubject -> Response.ok(updatedSubject).build())
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("No encontrado", throwable.getMessage()))
                            .build();
                });
    }

    @PATCH
    @Path("/{id}")
    public Uni<Response> partialUpdateSubject(@PathParam("id") Long id, Subject subject) {
        try {
            validateId(id);
        } catch (BadRequestException e) {
            return Uni.createFrom().item(
                Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("ID inválido", e.getMessage()))
                    .build()
            );
        }
        
        return subjectService.partialUpdate(id, subject)
                // CORRECCIÓN: Usar transformToUni en lugar de map
                .onItem().transformToUni(ignored -> 
                    Uni.createFrom().item(Response.noContent().build())
                )
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("No encontrado", throwable.getMessage()))
                            .build();
                });
    }

    @GET
    @Path("/semestre")
    public Uni<Response> getSubjectsBySemester(@QueryParam("semester") Integer semester) {
        return subjectService.findBySemester(semester)
                .map(subjects -> Response.ok(subjects).build())
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("No encontrado", throwable.getMessage()))
                            .build();
                });
    }

    @GET
    @Path("/aula")
    public Uni<Response> getSubjectsByClassroom(@QueryParam("classroom") String classroom) {
        return subjectService.findByClassroom(classroom)
                .map(subjects -> Response.ok(subjects).build())
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("No encontrado", throwable.getMessage()))
                            .build();
                });
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteSubject(@PathParam("id") Long id) {
        try {
            validateId(id);
        } catch (BadRequestException e) {
            return Uni.createFrom().item(
                Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("ID inválido", e.getMessage()))
                    .build()
            );
        }
        
        return subjectService.delete(id)
                // CORRECCIÓN: Usar transformToUni en lugar de map
                .onItem().transformToUni(ignored -> 
                    Uni.createFrom().item(Response.noContent().build())
                )
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("No encontrado", throwable.getMessage()))
                            .build();
                });
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("ID inválido: debe ser un número positivo");
        }
    }

    private void validateBody(Subject subject) {
        if (subject == null) {
            throw new BadRequestException("Datos de la materia son requeridos");
        }
    }
    
    // Clase interna para errores
    public static class ErrorResponse {
        public String error;
        public String message;
        
        public ErrorResponse() {}
        
        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }
    }
}