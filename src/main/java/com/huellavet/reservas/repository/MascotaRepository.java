package com.huellavet.reservas.repository;

import com.huellavet.reservas.model.MascotaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<MascotaModel, Long> {


    List<MascotaModel> findByUsuarioId(Long usuarioId);
}