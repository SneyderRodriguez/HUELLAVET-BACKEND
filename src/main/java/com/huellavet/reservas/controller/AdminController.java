package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.AdminLoginRequestDTO;
import com.huellavet.reservas.dto.LoginResponseDTO;
import com.huellavet.reservas.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AuthService authService;

    public AdminController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> iniciarSesion(@Valid @RequestBody AdminLoginRequestDTO request){
        LoginResponseDTO respuesta = authService.iniciarSesionAdministrador(request);
        return ResponseEntity.ok(respuesta);
    }
}
