package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.CitaDto;
import com.huellavet.reservas.model.*;
import com.huellavet.reservas.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;
    private final ServicioRepository servicioRepository;
    private final AdministradorRepository administradorRepository;

    public CitaService(CitaRepository citaRepository, UsuarioRepository usuarioRepository, MascotaRepository mascotaRepository, ServicioRepository servicioRepository, AdministradorRepository administradorRepository) {
        this.citaRepository = citaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mascotaRepository = mascotaRepository;
        this.servicioRepository = servicioRepository;
        this.administradorRepository = administradorRepository;
    }

    @Transactional(readOnly = true)
    public List<CitaDto> listarTodas() {
        return citaRepository.findAll().stream()
                .map(this::convertirADto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<CitaDto> buscarPorId(Long id) {
        return citaRepository.findById(id)
                .map(this::convertirADto);
    }
    @Transactional
    public CitaDto crear(CitaDto datos) {
        validarDatos(datos);

        CitaModel cita = new CitaModel();
        copiarDatosEditables(datos, cita, true);
        return convertirADto(citaRepository.save(cita));
    }

    @Transactional
    public Optional<CitaDto> actualizar(Long id, CitaDto datos) {
        Optional<CitaModel> citaEncontrada = citaRepository.findById(id);
        if (citaEncontrada.isEmpty()) {
            return Optional.empty();
        }
        validarDatos(datos);
        CitaModel cita = citaEncontrada.get();
        copiarDatosEditables(datos, cita, false);
        return Optional.of(convertirADto(citaRepository.save(cita)));
    }
    @Transactional
    public boolean eliminarPorId(Long id) {
        if (!citaRepository.existsById(id)) {
            return false;
        }
        citaRepository.deleteById(id);
        return true;
    }

    private void validarDatos(CitaDto datos) {
        if (datos == null) {
            throw new IllegalArgumentException("Los datos de la cita son obligatorios");
        }
        if (datos.getUsuarioId() == null || !usuarioRepository.existsById(parsearId(datos.getUsuarioId(), "usuario"))) {
            throw new IllegalArgumentException("El usuario de la cita no existe");
        }
        if (datos.getMascotaId() == null || !mascotaRepository.existsById(parsearId(datos.getMascotaId(), "mascota"))) {
            throw new IllegalArgumentException("La mascota de la cita no existe");
        }
        if (datos.getServicioId() == null || !servicioRepository.existsById(datos.getServicioId())) {
            throw new IllegalArgumentException("El servicio de la cita no existe");
        }
        if (datos.getFecha() == null) {
            throw new IllegalArgumentException("La fecha de la cita es obligatoria");
        }
        if (datos.getHora() == null) {
            throw new IllegalArgumentException("La hora de la cita es obligatoria");
        }
        if (textoVacio(datos.getModalidad())) {
            throw new IllegalArgumentException("La modalidad de la cita es obligatoria");
        }

        ModalidadCita modalidad = ModalidadCita.desdeValor(datos.getModalidad());
        if (modalidad == ModalidadCita.DOMICILIO && textoVacio(datos.getUbicacion())) {
            throw new IllegalArgumentException("La ubicación es obligatoria para una cita a domicilio");
        }
        if (textoVacio(datos.getNombreMascota())) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio");
        }
        if (textoVacio(datos.getServicioNombre())) {
            throw new IllegalArgumentException("El nombre del servicio es obligatorio");
        }
        if (Boolean.TRUE.equals(datos.getTieneCostoReserva())) {
            if (datos.getCostoReserva() == null || datos.getCostoReserva().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("El costo de reserva debe ser igual o mayor que cero");
            }
        }
    }

    private Long parsearId(String id, String nombreCampo) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("El " + nombreCampo + " debe ser un número válido");
        }
    }

    private void copiarDatosEditables(CitaDto datos, CitaModel cita, boolean esNueva) {
        UsuarioModel usuario = usuarioRepository.findById(parsearId(datos.getUsuarioId(), "usuario"))
                .orElseThrow(() -> new IllegalArgumentException("El usuario de la cita no existe"));
        MascotaModel mascota = mascotaRepository.findById(parsearId(datos.getMascotaId(), "mascota"))
                .orElseThrow(() -> new IllegalArgumentException("La mascota de la cita no existe"));
        ServicioModel servicio = servicioRepository.findById(datos.getServicioId())
                .orElseThrow(() -> new IllegalArgumentException("El servicio de la cita no existe"));

        cita.setUsuario(usuario);
        cita.setMascota(mascota);
        cita.setServicio(servicio);
        cita.setFecha(datos.getFecha());
        cita.setHora(datos.getHora());
        cita.setModalidad(ModalidadCita.desdeValor(datos.getModalidad()));
        cita.setUbicacion(limpiarTexto(datos.getUbicacion()));
        cita.setVeterinario(limpiarTexto(datos.getVeterinario()));
        cita.setMotivo(limpiarTexto(datos.getMotivo()));
        cita.setNombreMascota(datos.getNombreMascota().trim());
        cita.setServicioNombre(datos.getServicioNombre().trim());

        if (datos.getAdministradorId() != null) {
            AdministradorModel administrador = administradorRepository.findById(datos.getAdministradorId())
                    .orElseThrow(() -> new IllegalArgumentException("El administrador asignado no existe"));
            cita.setAdministrador(administrador);
        }

        if (datos.getEstado() != null) {
            cita.setEstado(EstadoCita.desdeValor(datos.getEstado()));
        } else if (esNueva) {
            cita.setEstado(EstadoCita.PENDIENTE);
        }

        boolean tieneCosto = Boolean.TRUE.equals(datos.getTieneCostoReserva());
        cita.setTieneCostoReserva(tieneCosto);
        cita.setCostoReserva(tieneCosto ? datos.getCostoReserva() : BigDecimal.ZERO);
    }

    private CitaDto convertirADto(CitaModel cita) {
        CitaDto dto = new CitaDto(
                cita.getId(),
                String.valueOf(cita.getUsuario().getId()),
                String.valueOf(cita.getMascota().getId()),
                cita.getServicio().getId(),
                cita.getFecha(),
                cita.getHora(),
                cita.getEstado().getValor(),
                cita.getModalidad().getValor(),
                cita.getUbicacion(),
                cita.getVeterinario(),
                cita.getMotivo(),
                cita.getTieneCostoReserva(),
                cita.getCostoReserva(),
                cita.getNombreMascota(),
                cita.getServicioNombre(),
                cita.getFechaCreacion());

        if (cita.getAdministrador() != null) {
            dto.setAdministradorId(cita.getAdministrador().getId());
            dto.setAdministradorNombre(cita.getAdministrador().getNombres() + " " + cita.getAdministrador().getApellidos());
        }
        return dto;
    }

    private boolean textoVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    private String limpiarTexto(String texto) {
        return texto == null ? null : texto.trim();
    }
}
