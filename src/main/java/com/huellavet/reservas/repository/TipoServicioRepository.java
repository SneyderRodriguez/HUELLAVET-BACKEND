package com.huellavet.reservas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.huellavet.reservas.model.TipoServicioModel;

public interface TipoServicioRepository extends JpaRepository<TipoServicioModel, Long> {
}
