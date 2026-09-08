package com.huellavet.reservas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.huellavet.reservas.model.ServicioModel;

public interface ServicioRepository extends JpaRepository<ServicioModel, Long> {
}
