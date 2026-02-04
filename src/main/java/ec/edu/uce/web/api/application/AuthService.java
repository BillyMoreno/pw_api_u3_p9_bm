package ec.edu.uce.web.api.application;

import java.time.Instant;
import java.util.Set;

import ec.edu.uce.web.api.domain.Usuario;
import ec.edu.uce.web.api.infraestructure.UsuarioRepository;
import ec.edu.uce.web.api.util.PasswordUtil;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {
    
    @Inject
    UsuarioRepository usuarioRepository;
    
    @WithTransaction
    public Uni<AuthResponse> autenticar(String usuario, String contrasena) {
        // Validar parámetros
        if (usuario == null || usuario.trim().isEmpty() || 
            contrasena == null || contrasena.trim().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Usuario y contraseña son requeridos"));
        }
        
        return usuarioRepository.findByUsuario(usuario.trim())
                .onItem().transformToUni(usuarioEncontrado -> {
                    if (usuarioEncontrado == null) {
                        return Uni.createFrom().failure(new SecurityException("Usuario no encontrado"));
                    }
                    
                    if (!usuarioEncontrado.activo) {
                        return Uni.createFrom().failure(new SecurityException("Usuario inactivo"));
                    }
                    
                    if (!PasswordUtil.verificar(contrasena, usuarioEncontrado.contrasena)) {
                        return Uni.createFrom().failure(new SecurityException("Contraseña incorrecta"));
                    }
                    
                    // Generar JWT
                    return generarToken(usuarioEncontrado);
                });
    }
    
    @WithTransaction
    public Uni<Boolean> inicializarAdmin() {
        return usuarioRepository.findByUsuario("admin")
                .onItem().transformToUni(adminExistente -> {
                    if (adminExistente != null) {
                        return Uni.createFrom().item(false); // Ya existe
                    }
                    
                    Usuario admin = new Usuario();
                    admin.usuario = "admin";
                    admin.contrasena = PasswordUtil.encriptar("admin123");
                    admin.rol = "admin";
                    admin.activo = true;
                    
                    return usuarioRepository.persist(admin)
                            .replaceWith(true);
                });
    }
    
    private Uni<AuthResponse> generarToken(Usuario usuario) {
        try {
            String issuer = "matricula-auth";
            long ttl = 3600; // 1 hora
            Instant now = Instant.now();
            Instant exp = now.plusSeconds(ttl);
            
            String jwt = Jwt.issuer(issuer)
                    .subject(usuario.usuario)
                    .upn(usuario.usuario)
                    .preferredUserName(usuario.usuario)
                    .groups(Set.of(usuario.rol))
                    .issuedAt(now)
                    .expiresAt(exp)
                    .sign();
            
            AuthResponse response = new AuthResponse();
            response.accessToken = jwt;
            response.expiresAt = exp.getEpochSecond();
            response.role = usuario.rol;
            response.usuario = usuario.usuario;
            
            return Uni.createFrom().item(response);
            
        } catch (Exception e) {
            return Uni.createFrom().failure(e);
        }
    }
    
    public static class AuthResponse {
        public String accessToken;
        public long expiresAt;
        public String role;
        public String usuario;
        
        public AuthResponse() {}
    }
}