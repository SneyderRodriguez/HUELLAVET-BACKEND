package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.LoginRequest;
import com.huellavet.reservas.dto.UsuarioResponse;
import com.huellavet.reservas.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<UsuarioResponse> iniciarSesion(@Valid @RequestBody LoginRequest request){
        UsuarioResponse usuario = authService.iniciarSesion(request);
        return ResponseEntity.ok(usuario);
    }
}
