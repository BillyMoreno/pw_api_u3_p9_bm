package uce.edu.web.api.matricula.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.matricula.application.EstudianteService;
import uce.edu.web.api.matricula.domain.Estudiante;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.DELETE;

@Path("/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class  EstudianteResourse {
    @Inject
    private EstudianteService estudianteService;
    @GET
    @Path("")
    public List<Estudiante>listarTodos(){
        return this.estudianteService.listarTodos();
    }

    // guardar estudiante nuevo
    @POST
    @Path("save")
    public Response saveEstudiante(Estudiante estudiante){
        this.estudianteService.saveEstudiante(estudiante);
        return Response.status(Response.Status.CREATED).entity(estudiante).build();
    }
    // consultar por id
    @GET
    @Path("{id}")
    public Estudiante consultarEstudiante(@PathParam("id") Integer id){
        return this.estudianteService.consultarEstudiante(id);
    }
    // actualizar
    @PUT
    @Path("{id}")
    public Response actualizarEstudiante(@PathParam("id") Integer id,Estudiante estudiante){
        this.estudianteService.actualizarEstudiante(id,estudiante);
        return Response.status(209).entity(null).build();
    }
    // actualizar parcial
    @PATCH
    @Path("{id}")
    public Response actualizarParcialEstudiante(@PathParam("id") Integer id,Estudiante estudiante){
        this.estudianteService.actualizarParcialEstudiante(id,estudiante);
        return Response.status(Response.Status.OK).entity(estudiante).build();
    }
    // eliminar
    @DELETE
    @Path("{id}")
    public Response eliminarEstudiante(@PathParam("id") Integer id){
        this.estudianteService.eliminarEstudiante(id);
        return Response.status(Response.Status.OK).entity("Estudiante eliminado").build();
    }
    // buscar por provincia
    @GET
    @Path("provincia/genero")
    public List<Estudiante> buscarPorProvincia(@QueryParam("provincia") String provincia, @QueryParam("genero") String genero){
        return this.estudianteService.buscarPorProvincia(provincia, genero);
    }
}
