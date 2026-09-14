package com.huellavet.reservas.repository;

import com.huellavet.reservas.model.VeterinarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeterinarioRepository extends JpaRepository<VeterinarioModel, Long> {
    Optional<VeterinarioModel> findByCorreoIgnoreCase(String correo);
    boolean existsByCorreoIgnoreCase(String correo);
}
