package com.huellavet.reservas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

/**
 * Transporta los datos de una cita entre el cliente de la API y el backend.
 *
 * El DTO evita exponer directamente la entidad de persistencia y permite que
 * el servicio controle cuáles valores se copian al crear o actualizar una cita.
 */
public class CitaDto {

    // Identifica la cita en las respuestas y no reemplaza el ID generado por la base de datos.
    private Long id;

    // Recibe el identificador del usuario que solicita la atención veterinaria.
    private String usuarioId;

    // Recibe el identificador de la mascota que será atendida.
    private String mascotaId;

    // Recibe el identificador numérico del servicio seleccionado.
    private Long servicioId;

    // Transporta la fecha de la cita mediante el formato ISO AAAA-MM-DD.
    private LocalDate fecha;

    // Transporta la hora de la cita mediante un valor compatible con LocalTime.
    private LocalTime hora;

    // Conserva textos como Pendiente para coincidir con las comparaciones del frontend.
    private String estado;

    // Conserva textos como clinica para coincidir con los valores enviados por el formulario.
    private String modalidad;

    // Describe dónde o por qué medio se realizará la atención.
    private String ubicacion;

    // Comunica el profesional asignado cuando la clínica ya realizó esa asignación.
    private String veterinario;

    // Transporta la explicación de la consulta proporcionada por el usuario.
    private String motivo;

    // Indica si la cita necesita un pago previo para reservarse.
    private Boolean tieneCostoReserva;

    // Transporta el valor monetario de la reserva usando precisión decimal.
    private BigDecimal costoReserva;

    // Conserva el nombre visible de la mascota solicitado por el DER y el frontend.
    private String nombreMascota;

    // Conserva el nombre visible del servicio para presentar la cita sin otra consulta.
    private String servicioNombre;

    // Informa el momento de creación sin permitir que el servicio lo copie al actualizar.
    private OffsetDateTime fechaCreacion;

    /**
     * Permite que Spring cree primero un DTO vacío y después asigne los valores
     * recibidos en el cuerpo JSON mediante los métodos setter.
     */
    public CitaDto() {

    }

    /**
     * Reúne todos los datos cuando el servicio transforma una entidad en DTO.
     * El orden de los parámetros coincide con el utilizado en CitaService.
     */
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

    // Los getters permiten que el servicio lea los datos recibidos y que Spring
    // los convierta en propiedades JSON al construir la respuesta HTTP.
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

    // Los setters permiten que Spring asigne al DTO cada propiedad recibida
    // desde el JSON sin depender de la generación automática de Lombok.
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
