package com.huellavet.reservas.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "usuarios", uniqueConstraints = {@UniqueConstraint(name = "uk_usuarios_email", columnNames = "email")})
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(nullable = false, length = 20)
    private String contrasena;

    @Column(length = 15)
    private String telefono;

    @Column(name = "indicativo_pais", length = 3)
    private String indicativoPais;

    @Column(length = 100)
    private String ciudad;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    @PrePersist
    public void antesDeGuardar() {
        if (creadoEn == null) {
            creadoEn = LocalDateTime.now();
        }
    }
    public UsuarioModel() {}

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String conrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndicativoPais() {
        return indicativoPais;
    }

    public void setIndicativoPais(String indicativoPais) {
        this.indicativoPais = indicativoPais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }
}
