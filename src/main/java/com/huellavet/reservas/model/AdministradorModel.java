package com.huellavet.reservas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "administradores", uniqueConstraints = {
        @UniqueConstraint(name = "uk_administradores_correo", columnNames = "correo")
})
public class AdministradorModel {
    @Id
    @GeneratedValue(
            strategy = jakarta.persistence.GenerationType.IDENTITY
    )
    private Long id;

    @Column(nullable = false, length = 30)
    private String nombres;

    @Column(nullable = false, length = 30)
    private String apellidos;

    @Column(unique = true, nullable = false, length = 150)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    @Column(columnDefinition = "TEXT")
    private String foto;

    public AdministradorModel() {}

    public Long getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
