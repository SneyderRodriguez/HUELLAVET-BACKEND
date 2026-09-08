package com.huellavet.reservas.repository;

import com.huellavet.reservas.model.CitaModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Proporciona las operaciones de persistencia para la entidad CitaModel.
 *
 * JpaRepository aporta métodos CRUD como findAll, findById, save y deleteById
 * sin que sea necesario escribir manualmente las consultas básicas.
 */
// Se declara como interfaz porque Spring Data JPA crea automáticamente la
// implementación concreta que conecta el servicio con la base de datos.
// CitaModel indica la entidad administrada y Long corresponde al tipo de su ID.
public interface CitaRepository extends JpaRepository<CitaModel, Long> {

    // El cuerpo puede permanecer vacío porque JpaRepository ya hereda las
    // operaciones CRUD básicas requeridas en esta primera versión del módulo.

}
