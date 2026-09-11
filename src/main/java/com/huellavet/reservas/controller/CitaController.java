package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.CitaDto;
import com.huellavet.reservas.service.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
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

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CitaDto datos) {
        try {
            CitaDto citaCreada = citaService.crear(datos);
            return ResponseEntity.status(HttpStatus.CREATED).body(citaCreada);

        } catch (IllegalArgumentException error) {
            return ResponseEntity.badRequest().body(error.getMessage());

        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody CitaDto datos) {

        try {
            Optional<CitaDto> citaActualizada = citaService.actualizar(id, datos);
            if (citaActualizada.isEmpty()) {
                return ResponseEntity.notFound().build();

            }
            return ResponseEntity.ok(citaActualizada.get());

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
