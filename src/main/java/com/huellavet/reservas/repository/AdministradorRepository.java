package com.huellavet.reservas.repository;

import com.huellavet.reservas.model.AdministradorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<AdministradorModel, Long> {
    Optional<AdministradorModel> findByCorreoIgnoreCase(String correo);
}
