package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.ServicioDTO;
import com.huellavet.reservas.service.ServicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ServicioDTO> crear(@RequestBody ServicioDTO dto) {
        return servicioService.crearServicio(dto)
                .map(creado -> ResponseEntity.status(HttpStatus.CREATED).body(creado))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioDTO> actualizar(@PathVariable Long id, @RequestBody ServicioDTO dto) {
        return servicioService.actualizarServicio(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return servicioService.eliminarServicio(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
