package com.huellavet.reservas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

/**
 * Representa una cita veterinaria almacenada en la base de datos.
 *
 * Los identificadores de usuario, mascota y servicio se conservan como campos
 * escalares mientras el equipo integra las entidades definitivas relacionadas.
 */
// @Entity informa a JPA que los objetos de esta clase pueden persistirse.
@Entity

// @Table relaciona la entidad Java con la tabla citas de PostgreSQL.
@Table(name = "citas")

public class CitaModel {

    // Identifica de forma única cada cita y PostgreSQL genera su valor automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relaciona conceptualmente la cita con el usuario definido en el DER.
    @Column(name = "usuario_id", nullable = false)
    private String usuarioId;

    // Relaciona conceptualmente la cita con la mascota seleccionada por el usuario.
    @Column(name = "mascota_id", nullable = false)
    private String mascotaId;

    // Relaciona conceptualmente la cita con el servicio veterinario reservado.
    @Column(name = "servicio_id", nullable = false)
    private Long servicioId;

    // Guarda la fecha calendario elegida para prestar el servicio.
    @Column(nullable = false)
    private LocalDate fecha;

    // Guarda la hora exacta elegida para iniciar la atención.
    @Column(nullable = false)
    private LocalTime hora;

    // Almacena el nombre del enum como texto y evita depender de su posición numérica.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoCita estado;

    // Conserva como texto la modalidad seleccionada para la atención veterinaria.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ModalidadCita modalidad;

    // Describe la dirección o el canal donde se realizará la atención.
    @Column(length = 200)
    private String ubicacion;

    // Permite registrar el nombre del profesional asignado cuando esté disponible.
    @Column(length = 150)
    private String veterinario;

    // Conserva la explicación proporcionada por el usuario al solicitar la cita.
    @Column(columnDefinition = "TEXT")
    private String motivo;

    // Indica si el servicio exige un pago previo para reservar la franja horaria.
    @Column(name = "tiene_costo_reserva", nullable = false)
    private Boolean tieneCostoReserva;

    // Utiliza BigDecimal para representar dinero sin errores de aproximación decimal.
    @Column(name = "costo_reserva", precision = 10, scale = 2)
    private BigDecimal costoReserva;

    // Conserva el nombre visible de la mascota como referencia histórica de la cita.
    @Column(name = "nombre_mascota", nullable = false, length = 120)
    private String nombreMascota;

    // Conserva el nombre visible del servicio aunque su descripción cambie después.
    @Column(name = "servicio_nombre", nullable = false, length = 150)
    private String servicioNombre;

    // Registra cuándo se creó la cita y evita que el valor cambie en una actualización.
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    /**
     * JPA utiliza este constructor vacío para reconstruir las citas consultadas
     * en la base de datos sin depender de la generación automática de Lombok.
     */
    public CitaModel() {

    }

    /**
     * Completa los valores iniciales justo antes de insertar una cita nueva.
     */
    // @PrePersist ejecuta este método antes del primer INSERT realizado por JPA.
    @PrePersist
    public void prepararCreacion() {
        // Solo establece el estado inicial cuando el cliente no proporcionó uno válido.
        if (estado == null) {
            estado = EstadoCita.PENDIENTE;
        }

        // Evita almacenar null en una columna que representa una decisión de sí o no.
        if (tieneCostoReserva == null) {
            tieneCostoReserva = false;
        }

        // Representa la ausencia de cobro con cero y facilita los cálculos monetarios.
        if (costoReserva == null) {
            costoReserva = BigDecimal.ZERO;
        }

        // Registra el instante de creación únicamente cuando todavía no existe.
        if (fechaCreacion == null) {
            fechaCreacion = OffsetDateTime.now();
        }
    }

    // Los getters permiten que el servicio consulte los datos de la entidad
    // antes de convertirlos en un CitaDto para la respuesta de la API.
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

    public EstadoCita getEstado() {
        return estado;
    }

    public ModalidadCita getModalidad() {
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

    // Los setters permiten que el servicio copie únicamente los datos editables.
    // El ID y la fecha de creación no reciben setter para proteger esos valores internos.
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

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public void setModalidad(ModalidadCita modalidad) {
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
}
