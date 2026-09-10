package com.huellavet.reservas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_servicio_id")
    private Long tipoServicioId;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    private Integer duracion;

    private String modalidad;

    @Column(name = "es_domicilio")
    private Boolean esDomicilio;

    @Column(name = "es_virtual")
    private Boolean esVirtual;

    @Column(name = "es_clinica")
    private Boolean esClinica;

    @Column(name = "direccion_clinica")
    private String direccionClinica;

    @Column(name = "tiene_costo_reserva")
    private Boolean tieneCostoReserva;

    @Column(name = "costo_reserva")
    private Double costoReserva;
}
