package com.huellavet.reservas.dto;

import com.huellavet.reservas.entity.Usuario;

import java.time.LocalDate;

public record UsuarioResponse (
        Long id,
        String email,
        String nombreCompleto,
        String telefono,
        String indicativoPais,
        String ciudad,
        LocalDate fechaNacimiento) {
    public static UsuarioResponse desdeEntidad(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombreCompleto(),
                usuario.getTelefono(),
                usuario.getIndicativoPais(),
                usuario.getCiudad(),
                usuario.getFechaNacimiento()
        );
    }
}
