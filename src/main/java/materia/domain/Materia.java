package materia.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="Materia")
@SequenceGenerator(name = "materia_seq", sequenceName = "materia_secuencia", allocationSize = 1)
public class Materia extends PanacheEntityBase{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materia_seq")
    private Integer id;
    private String nombre;
    private String creditos;


    //GETTERS AND SETTERS
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCreditos() {
        return creditos;
    }
    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

}
