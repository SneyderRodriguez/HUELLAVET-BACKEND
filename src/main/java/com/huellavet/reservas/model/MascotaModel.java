package com.huellavet.reservas.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mascotas")
public class MascotaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String usuarioId;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String especie;

    @Column(length = 50)
    private String raza;

    @Column(length = 20)
    private String sexo;

    private LocalDate fechaNacimiento;

    private Double peso;

    @Column(length = 50)
    private String color;

    private LocalDate fechaUltimaConsulta;

    @Column(columnDefinition = "TEXT")
    private String vacunas;

    @Column(columnDefinition = "TEXT")
    private String alergias;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    private String foto;

    @Column(updatable = false)
    private LocalDateTime creadaEn;

    public MascotaModel() {
    }

    public MascotaModel(String usuarioId, String nombre, String especie, String raza,
                        String sexo, LocalDate fechaNacimiento, Double peso, String color,
                        LocalDate fechaUltimaConsulta, String vacunas, String alergias,
                        String observaciones, String foto) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.color = color;
        this.fechaUltimaConsulta = fechaUltimaConsulta;
        this.vacunas = vacunas;
        this.alergias = alergias;
        this.observaciones = observaciones;
        this.foto = foto;
        this.creadaEn = LocalDateTime.now();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public LocalDate getFechaUltimaConsulta() { return fechaUltimaConsulta; }
    public void setFechaUltimaConsulta(LocalDate fechaUltimaConsulta) { this.fechaUltimaConsulta = fechaUltimaConsulta; }

    public String getVacunas() { return vacunas; }
    public void setVacunas(String vacunas) { this.vacunas = vacunas; }

    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public LocalDateTime getCreadaEn() { return creadaEn; }
    public void setCreadaEn(LocalDateTime creadaEn) { this.creadaEn = creadaEn; }
}