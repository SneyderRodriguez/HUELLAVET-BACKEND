package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.UserLoginRequestDTO;
import com.huellavet.reservas.dto.UserRegistroRequestDTO;
import com.huellavet.reservas.dto.UsuarioResponseDTO;
import com.huellavet.reservas.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UsuarioResponseDTO> iniciarSesion(@Valid @RequestBody UserLoginRequestDTO request){
        UsuarioResponseDTO usuario = authService.iniciarSesion(request);
        return ResponseEntity.ok(usuario);
    }
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody UserRegistroRequestDTO request) {
        UsuarioResponseDTO usuario = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
}
