package com.huellavet.reservas.service;

import com.huellavet.reservas.model.ServicioModel;
import com.huellavet.reservas.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;
    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<ServicioModel> listarServicios() {
        return servicioRepository.findAll();
    }

    public Optional<ServicioModel> buscarPorId(Long id) {
        return servicioRepository.findById(id);
    }

    public ServicioModel crearServicio(ServicioModel servicio) {
        return servicioRepository.save(servicio);
    }

    public Optional<ServicioModel> actualizarServicio(Long id, ServicioModel datos) {
        return servicioRepository.findById(id)
                .map(servicio -> {
                    servicio.setNombre(datos.getNombre());
                    servicio.setDescripcion(datos.getDescripcion());
                    servicio.setPrecio(datos.getPrecio());
                    servicio.setDuracion(datos.getDuracion());
                    servicio.setModalidad(datos.getModalidad());
                    servicio.setEsDomicilio(datos.getEsDomicilio());
                    servicio.setEsVirtual(datos.getEsVirtual());
                    servicio.setEsClinica(datos.getEsClinica());
                    servicio.setDireccionClinica(datos.getDireccionClinica());
                    servicio.setTieneCostoReserva(datos.getTieneCostoReserva());
                    servicio.setCostoReserva(datos.getCostoReserva());
                    servicio.setTipoServicioId(datos.getTipoServicioId());
                    return servicioRepository.save(servicio);
                });
    }

    public boolean eliminarServicio(Long id) {
        if (!servicioRepository.existsById(id)) {
            return false;
        }
        servicioRepository.deleteById(id);
        return true;
    }
}
