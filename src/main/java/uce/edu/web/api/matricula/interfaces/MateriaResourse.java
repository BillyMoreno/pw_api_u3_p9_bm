package uce.edu.web.api.matricula.interfaces;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.matricula.application.MateriaService;
import uce.edu.web.api.matricula.domain.Materia;

import java.util.List;

@Path("/materias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MateriaResourse {
    @Inject
    private MateriaService materiaService;
    @GET
    @Path("")
    public Iterable<Materia> listarMaterias(){
        return this.materiaService.listarMaterias();
    }
    @GET
    @Path("{id}")
    public Materia consultarMateriaId(@PathParam("id") Integer id) {
        return this.materiaService.consultarMateriaId(id);
    }
    @POST
    @Path("saveMateria")
    public Response agregarMateria(Materia materia){
        this.materiaService.agregarMateria(materia);
        return Response.status(Response.Status.CREATED).entity(materia).build();
    }
    @PUT
    @Path("{id}")
    public Response actualizarMateria(@PathParam("id") Integer id,Materia materia){
        this.materiaService.actualizarMateria(id,materia);
        return Response.status(Response.Status.OK).entity(materia).build();
    }
    @PATCH
    @Path("{id}")
    public Response actualizarParcialMateria(@PathParam("id") Integer id,Materia materia) {
        this.materiaService.actualizarParcialMateria(id, materia);
        return Response.status(Response.Status.OK).entity(materia).build();
    }
    @DELETE
    @Path("{id}")
    public Response eliminarMateria(@PathParam("id") Integer id) {
        this.materiaService.eliminarMateria(id);
        return Response.status(Response.Status.OK).entity("Materia eliminada").build();
    }
    // buscar por nombre de materia
    @GET
    @Path("buscarPorNombre/{nombre}")
    public Materia buscarPorNombre(@PathParam("nombre") String nombre) {
        return this.materiaService.buscarPorNombre(nombre);
    }
    // listar materias por numero de semestre
    @GET
    @Path("MateriasPorSemestre/{numSemestre}")
    public List<Materia> MateriasPorSemestre(@PathParam("numSemestre") String numSemestre){
        return this.materiaService.listarMateriasPorSemestre(numSemestre);
    }

}
