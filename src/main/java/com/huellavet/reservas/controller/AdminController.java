package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.AdminLoginRequestDTO;
import com.huellavet.reservas.dto.AdminResponseDTO;
import com.huellavet.reservas.repository.AdministradorRepository;
import com.huellavet.reservas.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    private final AuthService authService;
    public AdminController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<AdminResponseDTO> iniciarSesion(
        @Valid @RequestBody AdminLoginRequestDTO request){
        AdminResponseDTO adminResponseDTO = authService.iniciarSesionAdministrador(request);
        return ResponseEntity.ok(adminResponseDTO);
    }
}
