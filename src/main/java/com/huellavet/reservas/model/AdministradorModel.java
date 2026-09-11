package com.huellavet.reservas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administradores", uniqueConstraints = {
        @UniqueConstraint(name = "uk_administradores_correo", columnNames = "correo")
})
@Data
@NoArgsConstructor
public class AdministradorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    @Column(unique = true, nullable = false, length = 150)
    private String correo;

    @Column(nullable = false, length = 100)
    private String contrasena;
}