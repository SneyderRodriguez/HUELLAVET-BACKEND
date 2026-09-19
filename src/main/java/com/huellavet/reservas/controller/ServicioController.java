package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.ServicioDTO;
import com.huellavet.reservas.dto.ServiciosInicioDTO;
import com.huellavet.reservas.service.ServicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
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

    @PutMapping("/inicio")
    public ResponseEntity<?> definirServiciosInicio(@RequestBody ServiciosInicioDTO dto) {
        try {
            return ResponseEntity.ok(servicioService.definirServiciosInicio(dto == null ? null : dto.ids()));
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
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            return servicioService.eliminarServicio(id)
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.notFound().build();
        } catch (org.springframework.dao.DataIntegrityViolationException error) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar el servicio porque tiene citas asociadas");
        }
    }
}
