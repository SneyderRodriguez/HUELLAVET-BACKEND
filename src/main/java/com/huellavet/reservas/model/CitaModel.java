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
    @JoinColumn(name = "veterinario_id", foreignKey = @ForeignKey(name = "fk_cita_veterinario"))
    private VeterinarioModel veterinario;

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

    @Column(name = "motivo_estado", columnDefinition = "TEXT")
    private String motivoEstado;

    @Column(name = "abono_estado", length = 20)
    private String abonoEstado;

    @Column(name = "abono_comprobante", columnDefinition = "TEXT")
    private String abonoComprobante;

    @Column(name = "abono_fecha_pago")
    private OffsetDateTime abonoFechaPago;

    @Column(name = "recordatorio_texto", columnDefinition = "TEXT")
    private String recordatorioTexto;

    @Column(name = "recordatorio_fecha")
    private LocalDate recordatorioFecha;

    @Column(name = "recordatorio_fecha_creacion")
    private OffsetDateTime recordatorioFechaCreacion;

    @Column(name = "cliente_nombre", length = 150)
    private String clienteNombre;

    @Column(name = "cliente_telefono", length = 40)
    private String clienteTelefono;

    @Column(name = "cliente_email", length = 150)
    private String clienteEmail;

    @Column(name = "cliente_direccion", length = 250)
    private String clienteDireccion;

    @Column(name = "canal_recordatorio", length = 20)
    private String canalRecordatorio;

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
        if (abonoEstado == null) {
            abonoEstado = "pendiente";
        }
    }

    public Long getId() {
        return id;
    }

    public UsuarioModel getUsuario() { return usuario; }

    public MascotaModel getMascota() { return mascota; }

    public ServicioModel getServicio() { return servicio; }

    public VeterinarioModel getVeterinario() { return veterinario; }

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

    public void setVeterinario(VeterinarioModel veterinario) { this.veterinario = veterinario; }

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