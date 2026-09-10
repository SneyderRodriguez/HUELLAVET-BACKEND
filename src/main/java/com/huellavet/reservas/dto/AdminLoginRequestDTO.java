package com.huellavet.reservas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminLoginRequestDTO (
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no tiene un formato válido")
    String correo,

    @NotBlank(message = "La contraseña es obligatoria")
    String contrasena
){}
