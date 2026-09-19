package com.huellavet.reservas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "veterinarios", uniqueConstraints = {
        @UniqueConstraint(name = "uk_veterinarios_correo", columnNames = "correo")
})
@Data
@NoArgsConstructor
public class VeterinarioModel {
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

    @Column(length = 20)
    private String telefono;

    @Column(name = "indicativo_pais", length = 5)
    private String indicativoPais;

    @Column(length = 100)
    private String ciudad;

    @Column(nullable = false)
    private boolean activo = true;
}
