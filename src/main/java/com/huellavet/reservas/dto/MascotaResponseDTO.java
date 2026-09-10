package com.huellavet.reservas.dto;

import com.huellavet.reservas.model.MascotaModel;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MascotaResponseDTO(
        Long id,
        Long usuarioId,
        String nombre,
        String especie,
        String raza,
        String sexo,
        LocalDate fechaNacimiento,
        Double peso,
        String color,
        LocalDate fechaUltimaConsulta,
        String vacunas,
        String alergias,
        String observaciones,
        String foto,
        LocalDateTime creadaEn) {

    public static MascotaResponseDTO desdeEntidad(MascotaModel mascota) {
        return new MascotaResponseDTO(
                mascota.getId(),
                mascota.getUsuario().getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getSexo(),
                mascota.getFechaNacimiento(),
                mascota.getPeso(),
                mascota.getColor(),
                mascota.getFechaUltimaConsulta(),
                mascota.getVacunas(),
                mascota.getAlergias(),
                mascota.getObservaciones(),
                mascota.getFoto(),
                mascota.getCreadaEn()
        );
    }
}