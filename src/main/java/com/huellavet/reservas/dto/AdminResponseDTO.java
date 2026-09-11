package com.huellavet.reservas.dto;

import com.huellavet.reservas.model.VeterinarioModel;

public record AdminResponseDTO (
        Long id,
        String nombres,
        String apellidos,
        String correo,
        String foto
) {
    public static AdminResponseDTO desdeEntidad(VeterinarioModel veterinarioModel){
        return new AdminResponseDTO(
                veterinarioModel.getId(),
                veterinarioModel.getNombres(),
                veterinarioModel.getApellidos(),
                veterinarioModel.getCorreo(),
                veterinarioModel.getFoto()
        );
    }
}
