package ec.edu.uce.web.api.domain;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
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
@Table(name = "son")
@SequenceGenerator(name = "son_seq", sequenceName = "hijo_secuencia", allocationSize = 1)
public class Son extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "son_seq")
    public Long id;
    public String name;
    public String lastName;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    public Student student;
}
