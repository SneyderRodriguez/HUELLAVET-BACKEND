package com.huellavet.reservas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VeterinarioContrasenaRequestDTO(
        @NotBlank(message = "La contraseña actual es obligatoria")
        String contrasenaActual,

        @NotBlank(message = "La nueva contraseña es obligatoria")
        @Size(min = 8, max = 20, message = "La nueva contraseña debe tener entre 8 y 20 caracteres")
        String contrasenaNueva
) {}
