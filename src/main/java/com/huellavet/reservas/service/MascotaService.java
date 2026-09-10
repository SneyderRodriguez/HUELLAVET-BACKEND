package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.MascotaDto;
import com.huellavet.reservas.model.MascotaModel;
import com.huellavet.reservas.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public List<MascotaModel> listarTodas() {
        return mascotaRepository.findAll();
    }

    public Optional<MascotaModel> buscarPorId(Long id) {
        return mascotaRepository.findById(id);
    }

    public List<MascotaModel> buscarPorUsuario(String usuarioId) {
        return mascotaRepository.findByUsuarioId(usuarioId);
    }

    public MascotaModel guardar(MascotaDto dto) {
        MascotaModel nuevaMascota = new MascotaModel(
                dto.getUsuarioId(),
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
        return mascotaRepository.findById(id).map(mascota -> {
            mascota.setUsuarioId(dto.getUsuarioId());
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
        }).orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));
    }

    public boolean eliminar(Long id) {
        if (mascotaRepository.existsById(id)) {
            mascotaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}