package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.TipoServicioDTO;
import com.huellavet.reservas.model.TipoServicioModel;
import com.huellavet.reservas.repository.TipoServicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TipoServicioService {

    private final TipoServicioRepository tipoServicioRepository;

    public TipoServicioService(TipoServicioRepository tipoServicioRepository) {
        this.tipoServicioRepository = tipoServicioRepository;
    }

    @Transactional(readOnly = true)
    public List<TipoServicioDTO> listarTipos() {
        return tipoServicioRepository.findAll()
                .stream()
                .map(this::mapearATipoServicioDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<TipoServicioDTO> buscarPorId(Long id) {
        return tipoServicioRepository.findById(id)
                .map(this::mapearATipoServicioDTO);
    }

    @Transactional
    public Optional<TipoServicioDTO> crearTipo(TipoServicioDTO datos) {
        TipoServicioModel tipo = new TipoServicioModel();
        tipo.setNombre(datos.nombre());

        TipoServicioModel creado = tipoServicioRepository.save(tipo);
        return Optional.of(mapearATipoServicioDTO(creado));
    }

    @Transactional
    public Optional<TipoServicioDTO> actualizarTipo(Long id, TipoServicioDTO datos) {
        return tipoServicioRepository.findById(id)
                .map(tipo -> {
                    tipo.setNombre(datos.nombre());
                    TipoServicioModel actualizado = tipoServicioRepository.save(tipo);
                    return mapearATipoServicioDTO(actualizado);
                });
    }

    public boolean eliminarTipo(Long id) {
        if (!tipoServicioRepository.existsById(id)) {
            return false;
        }
        tipoServicioRepository.deleteById(id);
        return true;
    }

    private TipoServicioDTO mapearATipoServicioDTO(TipoServicioModel tipo) {
        return new TipoServicioDTO(tipo.getId(), tipo.getNombre());
    }
}
