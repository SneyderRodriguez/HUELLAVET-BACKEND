package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.*;
import com.huellavet.reservas.model.AdministradorModel;
import com.huellavet.reservas.model.Rol;
import com.huellavet.reservas.model.UsuarioModel;
import com.huellavet.reservas.exception.CorreoYaRegistradoException;
import com.huellavet.reservas.exception.CredencialesInvalidasException;
import com.huellavet.reservas.repository.AdministradorRepository;
import com.huellavet.reservas.repository.UsuarioRepository;
import com.huellavet.reservas.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final AdministradorRepository administradorRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       AdministradorRepository administradorRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public UsuarioResponseDTO registrar(UserRegistroRequestDTO request){
        String emailNormalizado = normalizarEmail(request.email());

        if(usuarioRepository.existsByEmailIgnoreCase(emailNormalizado)){
            throw new CorreoYaRegistradoException("Ya existe un usuario registrado con ese correo");
        }
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setEmail(emailNormalizado);
        usuarioModel.setNombreCompleto(request.nombreCompleto().trim());
        usuarioModel.setContrasena(passwordEncoder.encode(request.contrasena()));
        usuarioModel.setTelefono(request.telefono());
        usuarioModel.setIndicativoPais(request.indicativoPais());
        usuarioModel.setCiudad(request.ciudad());
        usuarioModel.setFechaNacimiento(request.fechaNacimiento());

        UsuarioModel usuarioModelGuardado = usuarioRepository.save(usuarioModel);
        return UsuarioResponseDTO.desdeEntidad(usuarioModelGuardado);
    }

    @Transactional(readOnly = true)
    public LoginResponseDTO iniciarSesion(UserLoginRequestDTO request){
        String emailNormalizado = normalizarEmail(request.email());
        UsuarioModel usuarioModel = usuarioRepository.findByEmailIgnoreCase(emailNormalizado)
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrectos"));
        validarContrasena(request.contrasena(), usuarioModel.getContrasena());

        String token = jwtService.generarToken(usuarioModel.getEmail(), Rol.USUARIO.name(), usuarioModel.getId());
        UsuarioResponseDTO datos = UsuarioResponseDTO.desdeEntidad(usuarioModel);

        return new LoginResponseDTO(token, Rol.USUARIO.name(), datos);
    }

    @Transactional(readOnly = true)
    public LoginResponseDTO iniciarSesionAdministrador(AdminLoginRequestDTO request){
        String correoNormalizado = normalizarEmail(request.correo());
        AdministradorModel administradorModel = administradorRepository.findByCorreoIgnoreCase(correoNormalizado)
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrectos"));
        validarContrasena(request.contrasena(), administradorModel.getContrasena());

        String token = jwtService.generarToken(administradorModel.getCorreo(), Rol.ADMINISTRADOR.name(), administradorModel.getId());
        AdminResponseDTO datos = AdminResponseDTO.desdeEntidad(administradorModel);

        return new LoginResponseDTO(token, Rol.ADMINISTRADOR.name(), datos);
    }

    private void validarContrasena(String contrasenaIngresada, String contrasenaGuardada){
        boolean contrasenaValida = passwordEncoder.matches(contrasenaIngresada, contrasenaGuardada);
        if (!contrasenaValida){
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos");
        }
    }

    private String normalizarEmail(String email){
        return email.trim().toLowerCase();
    }
}