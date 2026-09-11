package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.MascotaDto;
import com.huellavet.reservas.exception.AccesoNoAutorizadoException;
import com.huellavet.reservas.model.MascotaModel;
import com.huellavet.reservas.model.UsuarioModel;
import com.huellavet.reservas.repository.MascotaRepository;
import com.huellavet.reservas.repository.UsuarioRepository;
import com.huellavet.reservas.security.AuthenticatedUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public MascotaService(MascotaRepository mascotaRepository, UsuarioRepository usuarioRepository, AuthenticatedUserService authenticatedUserService) {
        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    public List<MascotaModel> listarTodas() {
        return mascotaRepository.findAll();
    }

    public Optional<MascotaModel> buscarPorId(Long id) {
        return mascotaRepository.findById(id);
    }

    public List<MascotaModel> buscarPorUsuario(String usuarioId) {
        Long id = parsearId(usuarioId);
        if (authenticatedUserService.obtenerRolActual() == com.huellavet.reservas.model.Rol.USUARIO) {
            Long idAutenticado = authenticatedUserService.obtenerIdUsuarioActual();
            if (!idAutenticado.equals(id)) {
                throw new AccesoNoAutorizadoException("No puedes consultar las mascotas de otro usuario");
            }
        }
        return mascotaRepository.findByUsuarioId(id);
    }

    public MascotaModel guardar(MascotaDto dto) {
        Long idUsuarioEnBody = parsearId(dto.getUsuarioId());
        Long idUsuarioAutenticado = authenticatedUserService.obtenerIdUsuarioActual();

        if (!idUsuarioAutenticado.equals(idUsuarioEnBody)) {
            throw new AccesoNoAutorizadoException("No puedes crear una mascota para otro usuario");
        }
        UsuarioModel usuario = usuarioRepository.findById(parsearId(dto.getUsuarioId()))
                .orElseThrow(() -> new IllegalArgumentException("El usuario dueño de la mascota no existe"));
        MascotaModel nuevaMascota = new MascotaModel(
                usuario,
                dto.getNombre(),
                dto.getEspecie(),
                dto.getRaza(),
                dto.getSexo(),
                dto.getFechaNacimiento(),
                dto.getPeso(),
                dto.getColor(),
                dto.getFechaUltimaConsulta(),
                dto.getVacunas(),
                dto.getAlergias(),
                dto.getObservaciones(),
                dto.getFoto()
        );
        return mascotaRepository.save(nuevaMascota);
    }

    public MascotaModel actualizar(Long id, MascotaDto dto) {
        MascotaModel mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));
        validarDuenio(mascota);
            UsuarioModel usuario = usuarioRepository.findById(parsearId(dto.getUsuarioId()))
                    .orElseThrow(() -> new IllegalArgumentException("El usuario dueño de la mascota no existe"));
            mascota.setUsuario(usuario);
            mascota.setNombre(dto.getNombre());
            mascota.setEspecie(dto.getEspecie());
            mascota.setRaza(dto.getRaza());
            mascota.setSexo(dto.getSexo());
            mascota.setFechaNacimiento(dto.getFechaNacimiento());
            mascota.setPeso(dto.getPeso());
            mascota.setColor(dto.getColor());
            mascota.setFechaUltimaConsulta(dto.getFechaUltimaConsulta());
            mascota.setVacunas(dto.getVacunas());
            mascota.setAlergias(dto.getAlergias());
            mascota.setObservaciones(dto.getObservaciones());
            mascota.setFoto(dto.getFoto());
            return mascotaRepository.save(mascota);
    }

    public boolean eliminar(Long id) {
        MascotaModel mascota = mascotaRepository.findById(id).orElse(null);
        if (mascota == null) {
            return false;
        }
        validarDuenio(mascota);
        mascotaRepository.deleteById(id);
        return true;
    }

    private void validarDuenio(MascotaModel mascota) {
        Long idUsuarioAutenticado = authenticatedUserService.obtenerIdUsuarioActual();
        if (!mascota.getUsuario().getId().equals(idUsuarioAutenticado)) {
            throw new AccesoNoAutorizadoException("Esta mascota no te pertenece");
        }
    }

    private Long parsearId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("El ID del usuario debe ser un número válido");
        }
    }
}