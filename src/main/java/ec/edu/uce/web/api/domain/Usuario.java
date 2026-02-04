package ec.edu.uce.web.api.domain;  // ← DEBE estar en el MISMO paquete que las otras entidades

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
@SequenceGenerator(name = "usuarios_seq", sequenceName = "usuario_secuencia", allocationSize = 1)
public class Usuario extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_seq")
    public Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    public String usuario;
    
    @Column(nullable = false, length = 100)
    public String contrasena;
    
    @Column(nullable = false, length = 20)
    public String rol = "user";
    
    @Column(nullable = false)
    public Boolean activo = true;
    
    // Constructor vacío REQUERIDO
    public Usuario() {}
    
    // Constructor útil
    public Usuario(String usuario, String contrasena, String rol) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = true;
    }
}