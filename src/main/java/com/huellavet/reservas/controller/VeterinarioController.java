package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.AdminLoginRequestDTO;
import com.huellavet.reservas.dto.AdminResponseDTO;
import com.huellavet.reservas.dto.LoginResponseDTO;
import com.huellavet.reservas.service.AuthService;
import com.huellavet.reservas.service.VeterinarioAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/veterinario")
public class VeterinarioController {
    private final VeterinarioAuthService veterinarioAuthService;

    public VeterinarioController(VeterinarioAuthService veterinarioAuthService) {
        this.veterinarioAuthService = veterinarioAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> iniciarSesion(@Valid @RequestBody VeterinarioLoginRequestDTO request){
        LoginResponseDTO respuesta = veterinarioAuthService.iniciarSesion(request);
        return ResponseEntity.ok(respuesta);
    }
}
