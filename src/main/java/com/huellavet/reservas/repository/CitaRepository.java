package com.huellavet.reservas.repository;

import com.huellavet.reservas.model.CitaModel;
import com.huellavet.reservas.model.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface CitaRepository extends JpaRepository<CitaModel, Long> {
    List<CitaModel> findByUsuarioId(Long usuarioId);
    List<CitaModel> findByVeterinarioId(Long veterinarioId);
    List<CitaModel> findByFechaAndEstadoNotIn(LocalDate fecha, Collection<EstadoCita> estados);
}
