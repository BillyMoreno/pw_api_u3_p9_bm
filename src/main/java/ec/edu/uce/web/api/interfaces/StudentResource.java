package ec.edu.uce.web.api.interfaces;

import java.util.List;

import ec.edu.uce.web.api.application.StudentService;
import ec.edu.uce.web.api.domain.Student;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
    @Inject
    private StudentService studentService;

    @GET
    @Path("/todos")
    public Uni<List<Student>> getAllStudents() {
        return studentService.findAll();
    }

    @GET
    @Path("/buscarporid/{id}")
    public Uni<Student> getStudentById(@PathParam("id") Long id) {
        return studentService.findById(id);
    }

    @POST
    @Path("/guardar")
    public Uni<Student> createStudent(Student student) {
        return studentService.save(student);
    }

    @PUT
    @Path("/actualizar/{id}")
    public Uni<Student> updateStudent(@PathParam("id") Long id, Student student) {
        return studentService.update(id, student);
    }

    @PATCH
    @Path("/actualizarparcial/{id}")
    public Uni<Void> partialUpdateStudent(@PathParam("id") Long id, Student student) {
        return studentService.partialUpdate(id, student);
    }

    @DELETE
    @Path("/eliminar/{id}")
    public Uni<Boolean> deleteStudent(@PathParam("id") Long id) {
        return studentService.delete(id);
    }
}