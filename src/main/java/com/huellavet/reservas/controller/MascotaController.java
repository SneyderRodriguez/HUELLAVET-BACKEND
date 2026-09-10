package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.MascotaDto;
import com.huellavet.reservas.dto.MascotaResponseDTO;
import com.huellavet.reservas.model.MascotaModel;
import com.huellavet.reservas.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "*")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> listarTodas() {
        List<MascotaResponseDTO> mascotas = mascotaService.listarTodas().stream()
                .map(MascotaResponseDTO::desdeEntidad)
                .toList();
        return ResponseEntity.ok(mascotas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> buscarPorId(@PathVariable Long id) {
        return mascotaService.buscarPorId(id)
                .map(MascotaResponseDTO::desdeEntidad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MascotaResponseDTO>> buscarPorUsuario(@PathVariable String usuarioId) {
        List<MascotaResponseDTO> mascotas = mascotaService.buscarPorUsuario(usuarioId).stream()
                .map(MascotaResponseDTO::desdeEntidad)
                .toList();
        return ResponseEntity.ok(mascotas);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody MascotaDto dto) {
        try {
            MascotaModel guardada = mascotaService.guardar(dto);
            return new ResponseEntity<>(MascotaResponseDTO.desdeEntidad(guardada), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody MascotaDto dto) {
        try {
            MascotaModel actualizada = mascotaService.actualizar(id, dto);
            return ResponseEntity.ok(MascotaResponseDTO.desdeEntidad(actualizada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (mascotaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}