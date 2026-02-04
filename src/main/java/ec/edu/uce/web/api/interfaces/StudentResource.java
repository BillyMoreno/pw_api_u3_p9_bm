package ec.edu.uce.web.api.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import ec.edu.uce.web.api.application.SonService;
import ec.edu.uce.web.api.application.StudentService;
import ec.edu.uce.web.api.application.representation.LinkDTO;
import ec.edu.uce.web.api.application.representation.StudentRepresentation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/matricula/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("admin")
public class StudentResource {
    
    @Inject
    private StudentService studentService;

    @Inject
    private SonService sonService;

    @Inject
    JsonWebToken jwt;

    @Context
    private UriInfo uriInfo;

    @GET
    public Uni<Response> getAllStudents() {
        System.out.println("Usuario autenticado: " + jwt.getName());
        
        return studentService.findAll()
                .flatMap(students -> {
                    if (students == null || students.isEmpty()) {
                        return Uni.createFrom().item(
                            Response.ok(new ArrayList<>()).build()
                        );
                    }
                    
                    // Procesar cada estudiante y agregar links
                    List<StudentRepresentation> result = new ArrayList<>();
                    for (StudentRepresentation student : students) {
                        // Agregar links directamente
                        student = addLinks(student);
                        result.add(student);
                    }
                    
                    return Uni.createFrom().item(Response.ok(result).build());
                })
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(new ErrorResponse("Error", throwable.getMessage()))
                            .build();
                });
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getStudentById(@PathParam("id") Long id) {
        return studentService.findById(id)
                .map(student -> {
                    if (student != null) {
                        student = addLinks(student);
                        return Response.ok(student).build();
                    } else {
                        return Response.status(Response.Status.NOT_FOUND)
                                .entity(new ErrorResponse("No encontrado", "Estudiante no encontrado"))
                                .build();
                    }
                })
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("No encontrado", throwable.getMessage()))
                            .build();
                });
    }

    @GET
    @Path("/provincia")
    public Uni<Response> getStudentsByProvince(@QueryParam("province") String province) {
        return studentService.findByProvince(province)
                .map(students -> {
                    List<StudentRepresentation> result = new ArrayList<>();
                    for (StudentRepresentation student : students) {
                        student = addLinks(student);
                        result.add(student);
                    }
                    return Response.ok(result).build();
                })
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(new ErrorResponse("Error", throwable.getMessage()))
                            .build();
                });
    }

    @GET
    @Path("/provincia/genero")
    public Uni<Response> getStudentsByProvinceAndGender(
            @QueryParam("province") String province,
            @QueryParam("gender") String gender) {
        return studentService.findByProvinceAndGender(province, gender)
                .map(students -> {
                    List<StudentRepresentation> result = new ArrayList<>();
                    for (StudentRepresentation student : students) {
                        student = addLinks(student);
                        result.add(student);
                    }
                    return Response.ok(result).build();
                })
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(new ErrorResponse("Error", throwable.getMessage()))
                            .build();
                });
    }

    @POST
    public Uni<Response> createStudent(StudentRepresentation student) {
        return studentService.save(student)
                .map(savedStudent -> {
                    savedStudent = addLinks(savedStudent);
                    return Response.status(Response.Status.CREATED)
                            .entity(savedStudent)
                            .build();
                })
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Error", throwable.getMessage()))
                            .build();
                });
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> updateStudent(@PathParam("id") Long id, StudentRepresentation student) {
        return studentService.update(id, student)
                .map(updatedStudent -> {
                    updatedStudent = addLinks(updatedStudent);
                    return Response.ok(updatedStudent).build();
                })
                .onFailure().recoverWithItem(throwable -> {
                    if (throwable instanceof jakarta.ws.rs.NotFoundException) {
                        return Response.status(Response.Status.NOT_FOUND)
                                .entity(new ErrorResponse("No encontrado", throwable.getMessage()))
                                .build();
                    }
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Error", throwable.getMessage()))
                            .build();
                });
    }

    @PATCH
    @Path("/{id}")
    public Uni<Response> partialUpdateStudent(@PathParam("id") Long id, StudentRepresentation student) {
        return studentService.partialUpdate(id, student)
                .onItem().transformToUni(ignored -> 
                    Uni.createFrom().item(Response.noContent().build())
                )
                .onFailure().recoverWithItem(throwable -> {
                    if (throwable instanceof jakarta.ws.rs.NotFoundException) {
                        return Response.status(Response.Status.NOT_FOUND)
                                .entity(new ErrorResponse("No encontrado", throwable.getMessage()))
                                .build();
                    }
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(new ErrorResponse("Error", throwable.getMessage()))
                            .build();
                });
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteStudent(@PathParam("id") Long id) {
        return studentService.delete(id)
                .flatMap(deleted -> {
                    if (deleted) {
                        return Uni.createFrom().item(Response.noContent().build());
                    } else {
                        return Uni.createFrom().item(
                            Response.status(Response.Status.NOT_FOUND)
                                .entity(new ErrorResponse("No encontrado", "Estudiante no encontrado"))
                                .build()
                        );
                    }
                });
    }

    @GET
    @Path("/{studentId}/hijos")
    public Uni<Response> getSonsByStudentId(@PathParam("studentId") Long studentId) {
        return sonService.findByStudentId(studentId)
                .map(sons -> Response.ok(sons).build())
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(new ErrorResponse("Error", throwable.getMessage()))
                            .build();
                });
    }

    private StudentRepresentation addLinks(StudentRepresentation student) {
        if (student == null) {
            return null;
        }
        
        String baseUri = uriInfo.getBaseUriBuilder()
                .path(StudentResource.class)
                .build()
                .toString();

        if (student.links == null) {
            student.links = new ArrayList<>();
        } else {
            student.links.clear();
        }
        
        student.links.add(new LinkDTO("self", baseUri + "/" + student.id));
        student.links.add(new LinkDTO("sons", baseUri + "/" + student.id + "/hijos"));
        
        return student;
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