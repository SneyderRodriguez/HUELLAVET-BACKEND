package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.ServicioDTO;
import com.huellavet.reservas.service.ServicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public List<ServicioDTO> listar() {
        return servicioService.listarServicios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> buscarPorId(@PathVariable Long id) {
        return servicioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ServicioDTO dto) {
        try {
            return servicioService.crearServicio(dto)
                    .map(creado -> ResponseEntity.status(HttpStatus.CREATED).body(creado))
                    .orElse(ResponseEntity.badRequest().build());
        } catch (IllegalArgumentException error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ServicioDTO dto) {
        try {
            return servicioService.actualizarServicio(id, dto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException error) {
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return servicioService.eliminarServicio(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
