package com.huellavet.reservas.repository;

import com.huellavet.reservas.model.CitaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaRepository extends JpaRepository<CitaModel, Long> {
    List<CitaModel> findByUsuarioId(Long usuarioId);
    List<CitaModel> findByVeterinarioId(Long veterinarioId);
}
