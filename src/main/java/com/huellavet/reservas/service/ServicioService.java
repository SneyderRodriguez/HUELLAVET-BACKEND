package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.ServicioDTO;
import com.huellavet.reservas.model.ServicioModel;
import com.huellavet.reservas.model.TipoServicioModel;
import com.huellavet.reservas.repository.ServicioRepository;
import com.huellavet.reservas.repository.TipoServicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ServicioService {
    public static final int MAX_SERVICIOS_INICIO = 3;
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
        servicio.setIcono(datos.icono());
        servicio.setImagen(datos.imagen());

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
        servicio.setIcono(datos.icono());
        servicio.setImagen(datos.imagen());

                    ServicioModel actualizado = servicioRepository.save(servicio);
                    return mapearAServicioDTO(actualizado);
                });
    }

    @Transactional
    public List<ServicioDTO> definirServiciosInicio(List<Long> ids) {
        List<Long> seleccion = (ids == null ? List.<Long>of() : ids).stream()
                .filter(java.util.Objects::nonNull)
                .distinct()
                .limit(MAX_SERVICIOS_INICIO)
                .toList();

        List<ServicioModel> servicios = servicioRepository.findAll();
        Set<Long> existentes = new HashSet<>();
        servicios.forEach(servicio -> existentes.add(servicio.getId()));
        for (Long id : seleccion) {
            if (!existentes.contains(id)) {
                throw new IllegalArgumentException("El servicio " + id + " no existe");
            }
        }

        for (ServicioModel servicio : servicios) {
            int indice = seleccion.indexOf(servicio.getId());
            servicio.setMostrarEnHome(indice != -1);
            servicio.setDestacado(indice == 0);
            servicio.setOrdenInicio(indice == -1 ? null : indice + 1);
        }

        return servicioRepository.saveAll(servicios).stream()
                .map(this::mapearAServicioDTO)
                .toList();
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
                servicio.getCostoReserva(),
                servicio.getIcono(),
                servicio.getImagen(),
                Boolean.TRUE.equals(servicio.getMostrarEnHome()),
                Boolean.TRUE.equals(servicio.getDestacado()),
                servicio.getOrdenInicio()
        );
    }
}
