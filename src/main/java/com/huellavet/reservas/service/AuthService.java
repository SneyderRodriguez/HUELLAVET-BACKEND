package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.LoginRequest;
import com.huellavet.reservas.dto.RegistroRequest;
import com.huellavet.reservas.dto.UsuarioResponse;
import com.huellavet.reservas.entity.Usuario;
import com.huellavet.reservas.exception.CorreoYaRegistradoException;
import com.huellavet.reservas.exception.CredencialesInvalidasException;
import com.huellavet.reservas.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public UsuarioResponse registrar(RegistroRequest request){
        String emailNormalizado = normalizarEmail(request.email());
        if(usuarioRepository.existsByEmailIgnoreCase(emailNormalizado)){
            throw new CorreoYaRegistradoException("Ya existe un usuario registrado con ese correo");
        }
        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(request.nombreCompleto().trim());
        usuario.setEmail(emailNormalizado);
        usuario.setContrasena(passwordEncoder.encode(request.contrasena()));
        usuario.setTelefono(request.telefono());
        usuario.setIndicativoPais(request.indicativoPais());
        usuario.setCiudad(request.ciudad());
        usuario.setFechaNacimiento(request.fechaNacimiento());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return UsuarioResponse.desdeEntidad(usuarioGuardado);
    }
    @Transactional(readOnly = true)
    public UsuarioResponse iniciarSesion(LoginRequest request){
        String emailNormalizado = normalizarEmail(request.email());
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(emailNormalizado)
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrectos"));
        boolean contrasenaValida = passwordEncoder.matches(request.contrasena(), usuario.getContrasena());
        if(!contrasenaValida){
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos");}
        return UsuarioResponse.desdeEntidad(usuario);
    }
    private String normalizarEmail(String email){
        return email.trim().toLowerCase();
    }
}
