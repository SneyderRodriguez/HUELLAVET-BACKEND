package com.huellavet.reservas.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class MascotaDto {

    @NotBlank(message = "El ID del usuario dueño es obligatorio")
    private String usuarioId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    private String raza;
    private String sexo;

    @PastOrPresent(message = "La fecha de nacimiento no puede ser una fecha futura")
    private LocalDate fechaNacimiento;

    @Positive(message = "El peso debe ser un número positivo")
    private Double peso;

    private String color;
    private LocalDate fechaUltimaConsulta;
    private String vacunas;
    private String alergias;
    private String observaciones;
    private String foto;

    public MascotaDto() {
    }


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
}