package com.huellavet.reservas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VeterinarioPerfilRequestDTO(
        @NotBlank(message = "El teléfono es obligatorio")
        @Size(max = 15, message = "El teléfono no puede tener más de 15 caracteres")
        String telefono,

        @Size(max = 5, message = "El indicativo no puede tener más de 5 caracteres")
        String indicativoPais,

        @NotBlank(message = "La ciudad es obligatoria")
        @Size(max = 100, message = "La ciudad no puede tener más de 100 caracteres")
        String ciudad
) {}
