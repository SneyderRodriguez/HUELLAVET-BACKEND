package com.huellavet.reservas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrador", uniqueConstraints = {
        @UniqueConstraint(name = "uk_administrador_correo", columnNames = "correo")
})
@Data
@NoArgsConstructor
public class AdministradorModel {
    @Id
    @GeneratedValue(
            strategy = jakarta.persistence.GenerationType.IDENTITY
    )
    private Long id;

    @Column(nullable = false, length = 30)
    private String nombres;

    @Column(nullable = false, length = 30)
    private String apellidos;

    @Column(unique = true, nullable = false, length = 150)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    @Column(columnDefinition = "TEXT")
    private String foto;
}
