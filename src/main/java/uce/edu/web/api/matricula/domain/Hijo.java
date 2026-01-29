package materia.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Hijo")
@SequenceGenerator(name = "hijo_seq", sequenceName = "hijo_secuencia", allocationSize = 1)
public class Hijo extends PanacheEntityBase{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hijo_seq")
    public Long id;
    public String nombre;
    public String apellido;

    @ManyToOne(fetch = FetchType.LAZY) //mapeo de entidades
    @JoinColumn(name = "estudiante_id") 
    @JsonbTransient
    public Estudiante estudiante;

}
