package com.huellavet.reservas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

public class CitaDto {
    private Long id;
    private String usuarioId;
    private String mascotaId;
    private Long servicioId;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
    private String modalidad;
    private String ubicacion;
    private String veterinario;
    private String motivo;
    private Boolean tieneCostoReserva;
    private BigDecimal costoReserva;
    private String nombreMascota;
    private String servicioNombre;
    private OffsetDateTime fechaCreacion;
    private Long administradorId;
    private String administradorNombre;
    public CitaDto() {

    }
    public CitaDto(
            Long id,
            String usuarioId,
            String mascotaId,
            Long servicioId,
            LocalDate fecha,
            LocalTime hora,
            String estado,
            String modalidad,
            String ubicacion,
            String veterinario,
            String motivo,
            Boolean tieneCostoReserva,
            BigDecimal costoReserva,
            String nombreMascota,
            String servicioNombre,
            OffsetDateTime fechaCreacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.mascotaId = mascotaId;
        this.servicioId = servicioId;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.modalidad = modalidad;
        this.ubicacion = ubicacion;
        this.veterinario = veterinario;
        this.motivo = motivo;
        this.tieneCostoReserva = tieneCostoReserva;
        this.costoReserva = costoReserva;
        this.nombreMascota = nombreMascota;
        this.servicioNombre = servicioNombre;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public String getMascotaId() {
        return mascotaId;
    }

    public Long getServicioId() {
        return servicioId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getEstado() {
        return estado;
    }

    public String getModalidad() {
        return modalidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public String getMotivo() {
        return motivo;
    }

    public Boolean getTieneCostoReserva() {
        return tieneCostoReserva;
    }

    public BigDecimal getCostoReserva() {
        return costoReserva;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public String getServicioNombre() {
        return servicioNombre;
    }

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Long getAdministradorId() { return administradorId; }

    public String getAdministradorNombre() { return administradorNombre; }

    public void setAdministradorId(Long administradorId) { this.administradorId = administradorId; }

    public void setAdministradorNombre(String administradorNombre) { this.administradorNombre = administradorNombre; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setMascotaId(String mascotaId) {
        this.mascotaId = mascotaId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setTieneCostoReserva(Boolean tieneCostoReserva) {
        this.tieneCostoReserva = tieneCostoReserva;
    }

    public void setCostoReserva(BigDecimal costoReserva) {
        this.costoReserva = costoReserva;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public void setServicioNombre(String servicioNombre) {
        this.servicioNombre = servicioNombre;
    }

    public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
