package com.huellavet.reservas.service;

import com.huellavet.reservas.model.TipoServicioModel;
import com.huellavet.reservas.repository.TipoServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoServicioService {

    private final TipoServicioRepository tipoServicioRepository;

    public TipoServicioService(TipoServicioRepository tipoServicioRepository) {
        this.tipoServicioRepository = tipoServicioRepository;
    }

    public List<TipoServicioModel> listarTipos() {
        return tipoServicioRepository.findAll();
    }

    public Optional<TipoServicioModel> buscarPorId(Long id) {
        return tipoServicioRepository.findById(id);
    }

    public TipoServicioModel crearTipo(TipoServicioModel tipo) {
        return tipoServicioRepository.save(tipo);
    }

    public Optional<TipoServicioModel> actualizarTipo(Long id, TipoServicioModel datos) {
        return tipoServicioRepository.findById(id)
                .map(tipo -> {
                    tipo.setNombre(datos.getNombre());
                    return tipoServicioRepository.save(tipo);
                });
    }

    public boolean eliminarTipo(Long id) {
        if (!tipoServicioRepository.existsById(id)) {
            return false;
        }
        tipoServicioRepository.deleteById(id);
        return true;
    }
}
