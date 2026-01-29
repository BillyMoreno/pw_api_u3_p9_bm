package materia.application.representation;

import java.time.LocalDateTime;
import java.util.List;

public class EstudianteRepresentation {

    private Long id;
    private String nombre;
    private String apellido;
    private LocalDateTime fechaNacimiento;
    private String provincia;
    private String genero;

    

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public LocalDateTime getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDateTime fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    //http://localhost:8080/............/estudiante/1/hijos

    public List<LinkDto> links;

}
