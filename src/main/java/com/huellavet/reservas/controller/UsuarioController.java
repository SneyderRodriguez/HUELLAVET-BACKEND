package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.UsuarioActualizacionDTO;
import com.huellavet.reservas.dto.UsuarioResponseDTO;
import com.huellavet.reservas.model.UsuarioModel;
import com.huellavet.reservas.repository.UsuarioRepository;
import com.huellavet.reservas.security.AuthenticatedUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public UsuarioController(UsuarioRepository usuarioRepository,
                             AuthenticatedUserService authenticatedUserService) {
        this.usuarioRepository = usuarioRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> obtenerActual() {
        return ResponseEntity.ok(UsuarioResponseDTO.desdeEntidad(usuarioActual()));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioRepository.findAll().stream()
                .map(UsuarioResponseDTO::desdeEntidad)
                .toList());
    }

    @PutMapping("/me")
    public ResponseEntity<?> actualizarActual(@Valid @RequestBody UsuarioActualizacionDTO datos) {
        UsuarioModel usuario = usuarioActual();
        String email = datos.email().trim().toLowerCase();

        if (usuarioRepository.findByEmailIgnoreCase(email)
                .filter(otro -> !otro.getId().equals(usuario.getId()))
                .isPresent()) {
            return ResponseEntity.badRequest().body("Ya existe un usuario con ese correo");
        }

        usuario.setNombreCompleto(datos.nombreCompleto().trim());
        usuario.setEmail(email);
        usuario.setTelefono(datos.telefono());
        usuario.setIndicativoPais(datos.indicativoPais());
        usuario.setCiudad(datos.ciudad());
        usuario.setFechaNacimiento(datos.fechaNacimiento());
        return ResponseEntity.ok(UsuarioResponseDTO.desdeEntidad(usuarioRepository.save(usuario)));
    }

    private UsuarioModel usuarioActual() {
        Long id = authenticatedUserService.obtenerIdUsuarioActual();
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Usuario del token no encontrado en la BD"));
    }
}
