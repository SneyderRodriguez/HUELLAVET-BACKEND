package com.huellavet.reservas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VeterinarioRegistroRequestDTO(
        @NotBlank(message = "Los nombres son obligatorios")
        @Size(max = 30, message = "Los nombres no pueden tener más de 30 caracteres")
        String nombres,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(max = 30, message = "Los apellidos no pueden tener más de 30 caracteres")
        String apellidos,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El correo electrónico no tiene un formato válido")
        @Size(max = 150, message = "El correo electrónico no puede tener más de 150 caracteres")
        String correo,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
        String contrasena,

        String foto
) {}