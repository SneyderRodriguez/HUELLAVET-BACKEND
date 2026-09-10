package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.TipoServicioDTO;
import com.huellavet.reservas.service.TipoServicioService;
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
@RequestMapping("/api/tipos-servicio")
public class TipoServicioController {

    private final TipoServicioService tipoServicioService;

    public TipoServicioController(TipoServicioService tipoServicioService) {
        this.tipoServicioService = tipoServicioService;
    }

    @GetMapping
    public List<TipoServicioDTO> listar() {
        return tipoServicioService.listarTipos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoServicioDTO> buscarPorId(@PathVariable Long id) {
        return tipoServicioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoServicioDTO> crear(@RequestBody TipoServicioDTO dto) {
        return tipoServicioService.crearTipo(dto)
                .map(creado -> ResponseEntity.status(HttpStatus.CREATED).body(creado))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoServicioDTO> actualizar(@PathVariable Long id, @RequestBody TipoServicioDTO dto) {
        return tipoServicioService.actualizarTipo(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return tipoServicioService.eliminarTipo(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
