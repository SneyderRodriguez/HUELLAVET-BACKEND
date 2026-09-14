package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.LoginResponseDTO;
import com.huellavet.reservas.dto.VeterinarioLoginRequestDTO;
import com.huellavet.reservas.dto.VeterinarioResponseDTO;
import com.huellavet.reservas.exception.CredencialesInvalidasException;
import com.huellavet.reservas.model.Rol;
import com.huellavet.reservas.model.VeterinarioModel;
import com.huellavet.reservas.repository.VeterinarioRepository;
import com.huellavet.reservas.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeterinarioAuthService {

    private final VeterinarioRepository veterinarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public VeterinarioAuthService(VeterinarioRepository veterinarioRepository,
                                  BCryptPasswordEncoder passwordEncoder,
                                  JwtService jwtService) {
        this.veterinarioRepository = veterinarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponseDTO iniciarSesion(VeterinarioLoginRequestDTO request) {
        String correoNormalizado = request.correo().trim().toLowerCase();
        VeterinarioModel veterinario = veterinarioRepository.findByCorreoIgnoreCase(correoNormalizado)
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrectos"));

        boolean contrasenaValida = passwordEncoder.matches(request.contrasena(), veterinario.getContrasena());
        if (!contrasenaValida) {
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos");
        }

        String token = jwtService.generarToken(veterinario.getCorreo(), Rol.VETERINARIO.name(), veterinario.getId());
        VeterinarioResponseDTO datos = VeterinarioResponseDTO.desdeEntidad(veterinario);

        return new LoginResponseDTO(token, Rol.VETERINARIO.name(), datos);
    }
}