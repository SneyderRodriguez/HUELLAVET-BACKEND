package com.huellavet.reservas.dto;

import com.huellavet.reservas.model.UsuarioModel;

import java.time.LocalDate;

public record UsuarioResponseDTO(
        Long id,
        String email,
        String nombreCompleto,
        String telefono,
        String indicativoPais,
        String ciudad,
        LocalDate fechaNacimiento) {
    public static UsuarioResponseDTO desdeEntidad(UsuarioModel usuarioModel) {
        return new UsuarioResponseDTO(
                usuarioModel.getId(),
                usuarioModel.getEmail(),
                usuarioModel.getNombreCompleto(),
                usuarioModel.getTelefono(),
                usuarioModel.getIndicativoPais(),
                usuarioModel.getCiudad(),
                usuarioModel.getFechaNacimiento()
        );
    }
}
