package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.LoginResponseDTO;
import com.huellavet.reservas.dto.VeterinarioLoginRequestDTO;
import com.huellavet.reservas.dto.VeterinarioRegistroRequestDTO;
import com.huellavet.reservas.dto.VeterinarioResponseDTO;
import com.huellavet.reservas.exception.CorreoYaRegistradoException;
import com.huellavet.reservas.service.VeterinarioAuthService;
import com.huellavet.reservas.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinario")
public class VeterinarioController {

    private final VeterinarioAuthService veterinarioAuthService;
    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioAuthService veterinarioAuthService, VeterinarioService veterinarioService) {
        this.veterinarioAuthService = veterinarioAuthService;
        this.veterinarioService = veterinarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> iniciarSesion(@Valid @RequestBody VeterinarioLoginRequestDTO request){
        LoginResponseDTO respuesta = veterinarioAuthService.iniciarSesion(request);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<VeterinarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(veterinarioService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody VeterinarioRegistroRequestDTO request) {
        try {
            VeterinarioResponseDTO creado = veterinarioService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (CorreoYaRegistradoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            if (veterinarioService.eliminar(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar: el veterinario tiene citas asignadas");
        }
    }
}