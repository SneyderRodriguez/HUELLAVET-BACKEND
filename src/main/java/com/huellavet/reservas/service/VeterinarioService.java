package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.VeterinarioContrasenaRequestDTO;
import com.huellavet.reservas.dto.VeterinarioPerfilRequestDTO;
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

    @Transactional(readOnly = true)
    public VeterinarioResponseDTO obtenerPorId(Long id) {
        return VeterinarioResponseDTO.desdeEntidad(buscarPorId(id));
    }

    @Transactional
    public VeterinarioResponseDTO actualizarPerfil(Long id, VeterinarioPerfilRequestDTO request) {
        VeterinarioModel veterinario = buscarPorId(id);
        veterinario.setTelefono(request.telefono().trim());
        veterinario.setIndicativoPais(request.indicativoPais() == null ? null : request.indicativoPais().trim());
        veterinario.setCiudad(request.ciudad().trim());
        return VeterinarioResponseDTO.desdeEntidad(veterinarioRepository.save(veterinario));
    }

    @Transactional
    public void cambiarContrasena(Long id, VeterinarioContrasenaRequestDTO request) {
        VeterinarioModel veterinario = buscarPorId(id);
        if (!passwordEncoder.matches(request.contrasenaActual(), veterinario.getContrasena())) {
            throw new IllegalArgumentException("La contraseña actual no es correcta");
        }
        veterinario.setContrasena(passwordEncoder.encode(request.contrasenaNueva()));
        veterinarioRepository.save(veterinario);
    }

    private VeterinarioModel buscarPorId(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veterinario no encontrado"));
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (!veterinarioRepository.existsById(id)) {
            return false;
        }
        veterinarioRepository.deleteById(id);
        return true;
    }

    @Transactional
    public VeterinarioResponseDTO cambiarEstado(Long id, boolean activo) {
        VeterinarioModel veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veterinario no encontrado"));
        veterinario.setActivo(activo);
        return VeterinarioResponseDTO.desdeEntidad(veterinarioRepository.save(veterinario));
    }
}