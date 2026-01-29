package materia.interfaces;

import java.io.ObjectInputFilter.Status;
import java.util.List;

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
import jakarta.ws.rs.core.Response;
import materia.application.MateriaService;
import materia.domain.Materia;
// import net.bytebuddy.asm.Advice.Return; // eliminado: import innecesario

@Path("/materias")
public class MateriaResource {
    
    @Inject
    private MateriaService materiaService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Materia> buscarTodos(){
        return this.materiaService.listaTodos();
    }

    @GET
    @Path("/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Materia> buscarPorNombre(@PathParam("nombre") String nombre){
        return this.materiaService.buscarPorNombre(nombre);
    }

    @GET
    @Path("/{creditos}")
    public List<Materia> buscarPorCreditos(@PathParam("creditos") String creditos){
        return this.materiaService.buscarPorCreditos(creditos);
    }


    @GET
    @Path("/{id}")
    public Materia consultarPorId(@PathParam("id") Integer id){
        return this.materiaService.consultarPorId(id);
    }

    @POST
    @Path("")
    public Response guardar(Materia materia){
        this.materiaService.crear(materia);
        return Response.status(Response.Status.CREATED).entity(materia).build();
    }

    @PUT
    @Path("/{id}")
    public void actualizar(@PathParam("id") Integer id, Materia materia){
        this.materiaService.actualizar(id, materia);
    }
    

    @PATCH
    @Path("/{id}")
    public Response actualizarParcial(@PathParam("id") Integer id, Materia materia){
        this.materiaService.actualizarParcial(id, materia);
        return Response.status(209).entity(null).build();
    }

    @DELETE
    @Path("/{id}")
    public void borrar(@PathParam("id") Integer id){
        this.materiaService.eliminar(id);
    }
}
