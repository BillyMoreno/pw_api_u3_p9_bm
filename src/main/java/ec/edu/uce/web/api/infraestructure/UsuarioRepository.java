package ec.edu.uce.web.api.infraestructure;

import ec.edu.uce.web.api.domain.Usuario;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    
    public Uni<Usuario> findByUsuario(String usuario) {
        return find("usuario", usuario).firstResult();
    }
    
    public Uni<Long> countByUsuario(String usuario) {
        return count("usuario", usuario);
    }
}