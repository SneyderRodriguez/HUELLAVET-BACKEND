package com.huellavet.reservas.dto;

import com.huellavet.reservas.model.AdministradorModel;

public record AdminResponseDTO (
        Long id,
        String nombres,
        String apellidos,
        String correo,
        String foto
) {
    public static AdminResponseDTO desdeEntidad(AdministradorModel administradorModel){
        return new AdminResponseDTO(
                administradorModel.getId(),
                administradorModel.getNombres(),
                administradorModel.getApellidos(),
                administradorModel.getCorreo(),
                administradorModel.getFoto()
        );
    }
}
