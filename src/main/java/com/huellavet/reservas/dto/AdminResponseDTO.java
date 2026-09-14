package com.huellavet.reservas.dto;

import com.huellavet.reservas.model.AdministradorModel;

public record AdminResponseDTO(
        Long id,
        String nombreCompleto,
        String correo
) {
    public static AdminResponseDTO desdeEntidad(AdministradorModel administradorModel) {
        return new AdminResponseDTO(
                administradorModel.getId(),
                administradorModel.getNombreCompleto(),
                administradorModel.getCorreo()
        );
    }
}