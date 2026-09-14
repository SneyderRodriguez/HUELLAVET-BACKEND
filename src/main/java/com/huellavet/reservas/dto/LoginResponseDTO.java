package com.huellavet.reservas.dto;

public record LoginResponseDTO(
        String token,
        String rol,
        Object datos) {
}
