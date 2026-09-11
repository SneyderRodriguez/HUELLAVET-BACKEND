package com.huellavet.reservas.dto;

import com.huellavet.reservas.model.VeterinarioModel;

public record VeterinarioResponseDTO(
        Long id,
        String nombres,
        String apellidos,
        String correo,
        String foto
) {
    public static VeterinarioResponseDTO desdeEntidad(VeterinarioModel veterinarioModel) {
        return new VeterinarioResponseDTO(
                veterinarioModel.getId(),
                veterinarioModel.getNombres(),
                veterinarioModel.getApellidos(),
                veterinarioModel.getCorreo(),
                veterinarioModel.getFoto()
        );
    }
}
