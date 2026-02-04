package ec.edu.uce.web.api.interfaces;

import ec.edu.uce.web.api.application.AuthService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll  // Permitir acceso sin autenticación
public class AuthResource {
    
    @Inject
    AuthService authService;
    
    @POST
    @Path("/login")
    public Uni<Response> login(LoginRequest loginRequest) {
        if (loginRequest == null || 
            loginRequest.usuario == null || loginRequest.usuario.trim().isEmpty() ||
            loginRequest.contrasena == null || loginRequest.contrasena.trim().isEmpty()) {
            
            return Uni.createFrom().item(
                Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Datos incompletos", "Usuario y contraseña son requeridos"))
                    .build()
            );
        }
        
        return authService.autenticar(loginRequest.usuario, loginRequest.contrasena)
                .onItem().transform(authResponse -> 
                    Response.ok(authResponse).build()
                )
                .onFailure().recoverWithItem(throwable -> {
                    String mensaje = throwable.getMessage();
                    if (throwable instanceof SecurityException) {
                        return Response.status(Response.Status.UNAUTHORIZED)
                                .entity(new ErrorResponse("Credenciales inválidas", mensaje))
                                .build();
                    } else {
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(new ErrorResponse("Error interno", mensaje))
                                .build();
                    }
                });
    }
    
    @GET
    @Path("/init-admin")
    public Uni<Response> inicializarAdmin() {
        return authService.inicializarAdmin()
                .onItem().transform(creado -> {
                    if (creado) {
                        return Response.ok(new MessageResponse("Usuario admin creado exitosamente")).build();
                    } else {
                        return Response.ok(new MessageResponse("El usuario admin ya existe")).build();
                    }
                })
                .onFailure().recoverWithItem(throwable -> 
                    Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new ErrorResponse("Error al crear admin", throwable.getMessage()))
                        .build()
                );
    }
    
    @GET
    @Path("/health")
    public Response health() {
        return Response.ok(new MessageResponse("Auth service is running")).build();
    }
    
    // ===== CLASES DTO =====
    
    public static class LoginRequest {
        public String usuario;
        public String contrasena;
        
        public LoginRequest() {}
    }
    
    public static class ErrorResponse {
        public String error;
        public String message;
        
        public ErrorResponse() {}
        
        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }
    }
    
    public static class MessageResponse {
        public String message;
        
        public MessageResponse() {}
        
        public MessageResponse(String message) {
            this.message = message;
        }
    }
}