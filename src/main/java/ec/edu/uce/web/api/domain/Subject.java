package ec.edu.uce.web.api.domain;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subjects")
public class Subject extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "code", unique = true)
    public String code;

    @Column(name = "name")
    public String name;

    @Column(name = "credits")
    public Integer credits;

    @Column(name = "description")
    public String description;

    @Column(name = "is_active")
    public Boolean isActive = true;
}