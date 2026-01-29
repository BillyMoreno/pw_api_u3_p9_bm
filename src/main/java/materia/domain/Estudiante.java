package materia.domain;

import java.time.LocalDateTime;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "Estudiante")
@SequenceGenerator(name = "estudiante_seq", sequenceName = "estudiante_secuencia", allocationSize = 1)    
public class Estudiante extends PanacheEntityBase {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estudiante_seq")
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDateTime fechaNacimiento;
    private String provincia;
    public String genero;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Hijo> hijos;
 
    // Getters y Setters
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public String getApellido() {
        return apellido;
    }
 
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
 
    public LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }
 
    public void setFechaNacimiento(LocalDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
 
    public String getProvincia() {
        return provincia;
    }
 
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
