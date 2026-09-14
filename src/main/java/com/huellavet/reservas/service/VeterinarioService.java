package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.VeterinarioRegistroRequestDTO;
import com.huellavet.reservas.dto.VeterinarioResponseDTO;
import com.huellavet.reservas.exception.CorreoYaRegistradoException;
import com.huellavet.reservas.model.VeterinarioModel;
import com.huellavet.reservas.repository.VeterinarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public VeterinarioService(VeterinarioRepository veterinarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.veterinarioRepository = veterinarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<VeterinarioResponseDTO> listarTodos() {
        return veterinarioRepository.findAll().stream()
                .map(VeterinarioResponseDTO::desdeEntidad)
                .toList();
    }

    @Transactional
    public VeterinarioResponseDTO crear(VeterinarioRegistroRequestDTO request) {
        String correoNormalizado = request.correo().trim().toLowerCase();

        if (veterinarioRepository.existsByCorreoIgnoreCase(correoNormalizado)) {
            throw new CorreoYaRegistradoException("Ya existe un veterinario registrado con ese correo");
        }

        VeterinarioModel veterinario = new VeterinarioModel();
        veterinario.setNombres(request.nombres().trim());
        veterinario.setApellidos(request.apellidos().trim());
        veterinario.setCorreo(correoNormalizado);
        veterinario.setContrasena(passwordEncoder.encode(request.contrasena()));
        veterinario.setFoto(request.foto());

        VeterinarioModel guardado = veterinarioRepository.save(veterinario);
        return VeterinarioResponseDTO.desdeEntidad(guardado);
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (!veterinarioRepository.existsById(id)) {
            return false;
        }
        veterinarioRepository.deleteById(id);
        return true;
    }
}