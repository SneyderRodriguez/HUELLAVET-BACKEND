package com.huellavet.reservas.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UsuarioActualizacionDTO(
        @NotBlank String nombreCompleto,
        String telefono,
        String indicativoPais,
        String ciudad,
        LocalDate fechaNacimiento) {
}
