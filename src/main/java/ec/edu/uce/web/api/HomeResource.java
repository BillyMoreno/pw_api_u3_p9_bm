package ec.edu.uce.web.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class HomeResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String home() {
        return "Bienvenido a la API de Quarkus - pw_api_u3_p9_bm";
    }
}
