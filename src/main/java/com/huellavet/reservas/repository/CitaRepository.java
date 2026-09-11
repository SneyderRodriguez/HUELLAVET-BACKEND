package com.huellavet.reservas.repository;

import com.huellavet.reservas.model.CitaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<CitaModel, Long> {
}
