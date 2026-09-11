package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.LoginRequestDTO;
import com.huellavet.reservas.dto.LoginResponseDTO;
import com.huellavet.reservas.dto.RegistroRequestDTO;
import com.huellavet.reservas.dto.UsuarioResponseDTO;
import com.huellavet.reservas.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody RegistroRequestDTO request){
        UsuarioResponseDTO usuario = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> iniciarSesion(@Valid @RequestBody LoginRequestDTO request){
        LoginResponseDTO respuesta = authService.iniciarSesion(request);
        return ResponseEntity.ok(respuesta);
    }
}
