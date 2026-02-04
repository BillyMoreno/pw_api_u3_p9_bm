package ec.edu.uce.web.api.domain;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "subjects")
@SequenceGenerator(name = "subjects_seq", sequenceName = "materia_secuencia", allocationSize = 1)
public class Subject extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjects_seq")
    public Long id;
    public String name;
    public String code;
    public String description;
    public Integer credits;
    public Integer semester;
    public String teacher;
    public String classroom;
}
