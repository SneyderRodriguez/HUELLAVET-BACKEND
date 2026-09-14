package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.CitaDto;
import com.huellavet.reservas.exception.AccesoNoAutorizadoException;
import com.huellavet.reservas.service.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<List<CitaDto>> listarTodas() {
        return ResponseEntity.ok(citaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> buscarPorId(@PathVariable Long id) {
        return citaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Long usuarioId) {
        try {
            return ResponseEntity.ok(citaService.listarPorUsuario(usuarioId));
        } catch (AccesoNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<?> listarPorVeterinario(@PathVariable Long veterinarioId) {
        try {
            return ResponseEntity.ok(citaService.listarPorVeterinario(veterinarioId));
        } catch (AccesoNoAutorizadoException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CitaDto datos) {
        try {
            CitaDto citaCreada = citaService.crear(datos);
            return ResponseEntity.status(HttpStatus.CREATED).body(citaCreada);
        } catch (AccesoNoAutorizadoException error) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error.getMessage());
        } catch (IllegalArgumentException error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @PutMapping("/{id}/aceptar")
    public ResponseEntity<?> aceptar(@PathVariable Long id) {
        return ejecutarCambioEstado(() -> citaService.aceptar(id));
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazar(@PathVariable Long id) {
        return ejecutarCambioEstado(() -> citaService.rechazar(id));
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<?> completar(@PathVariable Long id) {
        return ejecutarCambioEstado(() -> citaService.completar(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        return ejecutarCambioEstado(() -> citaService.cancelar(id));
    }

    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<?> reprogramar(@PathVariable Long id, @RequestBody CitaDto datosNuevos) {
        return ejecutarCambioEstado(() -> citaService.reprogramar(id, datosNuevos));
    }

    private ResponseEntity<?> ejecutarCambioEstado(java.util.function.Supplier<CitaDto> accion) {
        try {
            return ResponseEntity.ok(accion.get());
        } catch (AccesoNoAutorizadoException error) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error.getMessage());
        } catch (IllegalArgumentException error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (citaService.eliminarPorId(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}