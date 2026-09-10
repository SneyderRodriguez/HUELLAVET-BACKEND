package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.ServicioDTO;
import com.huellavet.reservas.model.ServicioModel;
import com.huellavet.reservas.model.TipoServicioModel;
import com.huellavet.reservas.repository.ServicioRepository;
import com.huellavet.reservas.repository.TipoServicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;
    private final TipoServicioRepository tipoServicioRepository;

    public ServicioService(ServicioRepository servicioRepository, TipoServicioRepository tipoServicioRepository) {
        this.servicioRepository = servicioRepository;
        this.tipoServicioRepository = tipoServicioRepository;
    }

    @Transactional(readOnly = true)
    public List<ServicioDTO> listarServicios() {
        return servicioRepository.findAll()
                .stream()
                .map(this::mapearAServicioDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<ServicioDTO> buscarPorId(Long id) {
        return servicioRepository.findById(id)
                .map(this::mapearAServicioDTO);
    }

    @Transactional
    public Optional<ServicioDTO> crearServicio(ServicioDTO datos) {
        TipoServicioModel tipoServicio = tipoServicioRepository.findById(datos.tipoServicioId())
                .orElseThrow(() -> new IllegalArgumentException("El tipo de servicio no existe"));

        ServicioModel servicio = new ServicioModel();
        servicio.setTipoServicio(tipoServicio);
        servicio.setNombre(datos.nombre());
        servicio.setDescripcion(datos.descripcion());
        servicio.setPrecio(datos.precio());
        servicio.setDuracion(datos.duracion());
        servicio.setModalidad(datos.modalidad());
        servicio.setEsDomicilio(datos.esDomicilio());
        servicio.setEsVirtual(datos.esVirtual());
        servicio.setEsClinica(datos.esClinica());
        servicio.setDireccionClinica(datos.direccionClinica());
        servicio.setTieneCostoReserva(datos.tieneCostoReserva());
        servicio.setCostoReserva(datos.costoReserva());

        ServicioModel creado = servicioRepository.save(servicio);
        return Optional.of(mapearAServicioDTO(creado));
    }

    @Transactional
    public Optional<ServicioDTO> actualizarServicio(Long id, ServicioDTO datos) {
        return servicioRepository.findById(id)
                .map(servicio -> {
                    TipoServicioModel tipoServicio = tipoServicioRepository.findById(datos.tipoServicioId())
                            .orElseThrow(() -> new IllegalArgumentException("El tipo de servicio no existe"));

                    servicio.setTipoServicio(tipoServicio);
                    servicio.setNombre(datos.nombre());
                    servicio.setDescripcion(datos.descripcion());
                    servicio.setPrecio(datos.precio());
                    servicio.setDuracion(datos.duracion());
                    servicio.setModalidad(datos.modalidad());
                    servicio.setEsDomicilio(datos.esDomicilio());
                    servicio.setEsVirtual(datos.esVirtual());
                    servicio.setEsClinica(datos.esClinica());
                    servicio.setDireccionClinica(datos.direccionClinica());
                    servicio.setTieneCostoReserva(datos.tieneCostoReserva());
                    servicio.setCostoReserva(datos.costoReserva());

                    ServicioModel actualizado = servicioRepository.save(servicio);
                    return mapearAServicioDTO(actualizado);
                });
    }

    public boolean eliminarServicio(Long id) {
        if (!servicioRepository.existsById(id)) {
            return false;
        }
        servicioRepository.deleteById(id);
        return true;
    }

    private ServicioDTO mapearAServicioDTO(ServicioModel servicio) {
        return new ServicioDTO(
                servicio.getId(),
                servicio.getTipoServicio().getId(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                servicio.getDuracion(),
                servicio.getModalidad(),
                servicio.getEsDomicilio(),
                servicio.getEsVirtual(),
                servicio.getEsClinica(),
                servicio.getDireccionClinica(),
                servicio.getTieneCostoReserva(),
                servicio.getCostoReserva()
        );
    }
}
