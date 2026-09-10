package com.huellavet.reservas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRegistroRequestDTO(
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 150, message = "El nombre no puede tener más de 150 caracteres")
    String nombreCompleto,

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no tiene unformato válido")
    @Size(max = 150, message = "El correo electrónico no puede tener más de 150 caracteres")
    String email,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
    String contrasena,

    @Size(max = 15, message = "El teléfono no puede tener más de 15 caracteres")
    String telefono,

    @Size(max = 3, message = "El indicativo del país no puede tener más de 3 caracteres")
    String indicativoPais,

    @Size(max = 100, message = "La ciudad no puede tener más de 100 caracteres")
    String ciudad,

    LocalDate fechaNacimiento
){}
