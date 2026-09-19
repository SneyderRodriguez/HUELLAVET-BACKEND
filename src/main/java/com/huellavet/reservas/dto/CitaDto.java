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
    private Long veterinarioId;
    private String veterinarioNombre;
    private String fotoMascota;
    private String motivoEstado;
    private String abonoEstado;
    private String abonoComprobante;
    private OffsetDateTime abonoFechaPago;
    private String recordatorioTexto;
    private LocalDate recordatorioFecha;
    private OffsetDateTime recordatorioFechaCreacion;
    private String clienteNombre;
    private String clienteTelefono;
    private String clienteEmail;
    private String clienteDireccion;
    private String canalRecordatorio;
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

    public Long getVeterinarioId() { return veterinarioId; }

    public String getVeterinarioNombre() { return veterinarioNombre; }

    public void setVeterinarioId(Long veterinarioId) { this.veterinarioId = veterinarioId; }

    public void setVeterinarioNombre(String veterinarioNombre) { this.veterinarioNombre = veterinarioNombre; }

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

    public String getFotoMascota() { return fotoMascota; }

    public void setFotoMascota(String fotoMascota) { this.fotoMascota = fotoMascota; }

    public String getMotivoEstado() { return motivoEstado; }

    public void setMotivoEstado(String motivoEstado) { this.motivoEstado = motivoEstado; }

    public String getAbonoEstado() { return abonoEstado; }

    public void setAbonoEstado(String abonoEstado) { this.abonoEstado = abonoEstado; }

    public String getAbonoComprobante() { return abonoComprobante; }

    public void setAbonoComprobante(String abonoComprobante) { this.abonoComprobante = abonoComprobante; }

    public OffsetDateTime getAbonoFechaPago() { return abonoFechaPago; }

    public void setAbonoFechaPago(OffsetDateTime abonoFechaPago) { this.abonoFechaPago = abonoFechaPago; }

    public String getRecordatorioTexto() { return recordatorioTexto; }

    public void setRecordatorioTexto(String recordatorioTexto) { this.recordatorioTexto = recordatorioTexto; }

    public LocalDate getRecordatorioFecha() { return recordatorioFecha; }

    public void setRecordatorioFecha(LocalDate recordatorioFecha) { this.recordatorioFecha = recordatorioFecha; }

    public OffsetDateTime getRecordatorioFechaCreacion() { return recordatorioFechaCreacion; }

    public void setRecordatorioFechaCreacion(OffsetDateTime recordatorioFechaCreacion) { this.recordatorioFechaCreacion = recordatorioFechaCreacion; }

    public String getClienteNombre() { return clienteNombre; }

    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getClienteTelefono() { return clienteTelefono; }

    public void setClienteTelefono(String clienteTelefono) { this.clienteTelefono = clienteTelefono; }

    public String getClienteEmail() { return clienteEmail; }

    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }

    public String getClienteDireccion() { return clienteDireccion; }

    public void setClienteDireccion(String clienteDireccion) { this.clienteDireccion = clienteDireccion; }

    public String getCanalRecordatorio() { return canalRecordatorio; }

    public void setCanalRecordatorio(String canalRecordatorio) { this.canalRecordatorio = canalRecordatorio; }
}
