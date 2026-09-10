package com.huellavet.reservas.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "citas")
public class CitaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cita_usuario"))
    private UsuarioModel usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mascota_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cita_mascota"))
    private MascotaModel mascota;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "servicio_id", nullable = false, foreignKey = @ForeignKey(name = "fk_cita_servicio"))
    private ServicioModel servicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrador_id", foreignKey = @ForeignKey(name = "fk_cita_administrador"))
    private AdministradorModel administrador;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoCita estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ModalidadCita modalidad;

    @Column(length = 200)
    private String ubicacion;

    @Column(length = 150)
    private String veterinario;

    @Column(columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "tiene_costo_reserva", nullable = false)
    private Boolean tieneCostoReserva;

    @Column(name = "costo_reserva", precision = 10, scale = 2)
    private BigDecimal costoReserva;

    @Column(name = "nombre_mascota", nullable = false, length = 120)
    private String nombreMascota;

    @Column(name = "servicio_nombre", nullable = false, length = 150)
    private String servicioNombre;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    public CitaModel() {

    }
    @PrePersist
    public void prepararCreacion() {
        if (estado == null) {
            estado = EstadoCita.PENDIENTE;
        }
        if (tieneCostoReserva == null) {
            tieneCostoReserva = false;
        }
        if (costoReserva == null) {
            costoReserva = BigDecimal.ZERO;
        }
        if (fechaCreacion == null) {
            fechaCreacion = OffsetDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public UsuarioModel getUsuario() { return usuario; }

    public MascotaModel getMascota() { return mascota; }

    public ServicioModel getServicio() { return servicio; }

    public AdministradorModel getAdministrador() { return administrador; }

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

    public void setUsuario(UsuarioModel usuario) { this.usuario = usuario; }

    public void setMascota(MascotaModel mascota) { this.mascota = mascota; }

    public void setServicio(ServicioModel servicio) { this.servicio = servicio; }

    public void setAdministrador(AdministradorModel administrador) { this.administrador = administrador; }

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
