package uce.edu.web.api.matricula.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "materia")
@SequenceGenerator(name = "materia_seq",sequenceName = "materia_secuencia",allocationSize = 1)
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "materia_seq")
    private Integer codigoMateria;
    private String nombreMateria;
    private String numSemestre;
    private int creditosMateria;

    public String getNumSemestre() {
        return numSemestre;
    }

    public void setNumSemestre(String numSemestre) {
        this.numSemestre = numSemestre;
    }

    public Integer getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(Integer codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public int getCreditosMateria() {
        return creditosMateria;
    }

    public void setCreditosMateria(int creditosMateria) {
        this.creditosMateria = creditosMateria;
    }
}
