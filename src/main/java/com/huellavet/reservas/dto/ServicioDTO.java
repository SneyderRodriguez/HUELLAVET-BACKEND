package com.huellavet.reservas.dto;

public record ServicioDTO(
        Long id,
        Long tipoServicioId,
        String nombre,
        String descripcion,
        Double precio,
        Integer duracion,
        String modalidad,
        Boolean esDomicilio,
        Boolean esVirtual,
        Boolean esClinica,
        String direccionClinica,
        Boolean tieneCostoReserva,
        Double costoReserva
) {
}
