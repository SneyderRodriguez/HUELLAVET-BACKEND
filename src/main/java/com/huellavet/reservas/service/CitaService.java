package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.CitaDto;
import com.huellavet.reservas.exception.AccesoNoAutorizadoException;
import com.huellavet.reservas.model.*;
import com.huellavet.reservas.repository.*;
import com.huellavet.reservas.security.AuthenticatedUserService;
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
    private final VeterinarioRepository veterinarioRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public CitaService(CitaRepository citaRepository, UsuarioRepository usuarioRepository, MascotaRepository mascotaRepository, ServicioRepository servicioRepository, VeterinarioRepository veterinarioRepository, AuthenticatedUserService authenticatedUserService) {
        this.citaRepository = citaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mascotaRepository = mascotaRepository;
        this.servicioRepository = servicioRepository;
        this.veterinarioRepository = veterinarioRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Transactional(readOnly = true)
    public List<CitaDto> listarTodas() {
        return citaRepository.findAll().stream()
                .map(this::convertirADto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CitaDto> listarPorUsuario(Long usuarioId) {
        if (authenticatedUserService.obtenerRolActual() == Rol.USUARIO) {
            Long idAutenticado = authenticatedUserService.obtenerIdUsuarioActual();
            if (!idAutenticado.equals(usuarioId)) {
                throw new AccesoNoAutorizadoException("No puedes consultar las citas de otro usuario");
            }
        }
        return citaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertirADto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CitaDto> listarPorVeterinario(Long veterinarioId) {
        if (authenticatedUserService.obtenerRolActual() == Rol.VETERINARIO) {
            Long idAutenticado = authenticatedUserService.obtenerIdVeterinarioActual();
            if (!idAutenticado.equals(veterinarioId)) {
                throw new AccesoNoAutorizadoException("No puedes consultar las citas de otro veterinario");
            }
        }
        return citaRepository.findByVeterinarioId(veterinarioId).stream()
                .map(this::convertirADto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<CitaDto> buscarPorId(Long id) {
        return citaRepository.findById(id).map(this::convertirADto);
    }

    @Transactional
    public CitaDto crear(CitaDto datos) {
        validarDatos(datos);

        Long idUsuarioEnBody = parsearId(datos.getUsuarioId(), "usuario");
        Long idUsuarioAutenticado = authenticatedUserService.obtenerIdUsuarioActual();
        if (!idUsuarioAutenticado.equals(idUsuarioEnBody)) {
            throw new AccesoNoAutorizadoException("No puedes crear una cita para otro usuario");
        }

        CitaModel cita = new CitaModel();
        copiarDatosEditables(datos, cita, true);
        return convertirADto(citaRepository.save(cita));
    }

    @Transactional
    public boolean eliminarPorId(Long id) {
        if (!citaRepository.existsById(id)) {
            return false;
        }
        citaRepository.deleteById(id);
        return true;
    }

    @Transactional
    public CitaDto aceptar(Long id) {
        CitaModel cita = obtenerCitaYValidarVeterinario(id);
        if (cita.getEstado() != EstadoCita.PENDIENTE) {
            throw new IllegalArgumentException("Solo se puede aceptar una cita en estado Pendiente");
        }
        cita.setEstado(EstadoCita.CONFIRMADA);
        return convertirADto(citaRepository.save(cita));
    }

    @Transactional
    public CitaDto rechazar(Long id) {
        CitaModel cita = obtenerCitaYValidarVeterinario(id);
        if (cita.getEstado() != EstadoCita.PENDIENTE) {
            throw new IllegalArgumentException("Solo se puede rechazar una cita en estado Pendiente");
        }
        cita.setEstado(EstadoCita.RECHAZADA);
        return convertirADto(citaRepository.save(cita));
    }

    @Transactional
    public CitaDto completar(Long id) {
        CitaModel cita = obtenerCitaYValidarVeterinario(id);
        if (cita.getEstado() != EstadoCita.CONFIRMADA && cita.getEstado() != EstadoCita.EN_CURSO) {
            throw new IllegalArgumentException("Solo se puede completar una cita Confirmada o En curso");
        }
        cita.setEstado(EstadoCita.COMPLETADA);
        return convertirADto(citaRepository.save(cita));
    }

    @Transactional
    public CitaDto cancelar(Long id) {
        CitaModel cita = obtenerCitaYValidarUsuario(id);
        if (cita.getEstado() == EstadoCita.COMPLETADA || cita.getEstado() == EstadoCita.CANCELADA) {
            throw new IllegalArgumentException("No se puede cancelar una cita ya finalizada");
        }
        cita.setEstado(EstadoCita.CANCELADA);
        return convertirADto(citaRepository.save(cita));
    }

    @Transactional
    public CitaDto reprogramar(Long id, CitaDto datosNuevos) {
        CitaModel cita = obtenerCitaYValidarUsuario(id);
        if (datosNuevos.getFecha() == null || datosNuevos.getHora() == null) {
            throw new IllegalArgumentException("La nueva fecha y hora son obligatorias para reprogramar");
        }
        cita.setFecha(datosNuevos.getFecha());
        cita.setHora(datosNuevos.getHora());
        cita.setEstado(EstadoCita.REPROGRAMADA);
        return convertirADto(citaRepository.save(cita));
    }

    private CitaModel obtenerCitaYValidarVeterinario(Long id) {
        CitaModel cita = citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La cita no existe"));

        if (authenticatedUserService.esAdministrador()) {
            return cita;
        }

        Long idVeterinarioAutenticado = authenticatedUserService.obtenerIdVeterinarioActual();
        if (cita.getVeterinario() == null || !cita.getVeterinario().getId().equals(idVeterinarioAutenticado)) {
            throw new AccesoNoAutorizadoException("Esta cita no está asignada a ti");
        }
        return cita;
    }

    private CitaModel obtenerCitaYValidarUsuario(Long id) {
        CitaModel cita = citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La cita no existe"));

        if (authenticatedUserService.esAdministrador()) {
            return cita;
        }

        Long idUsuarioAutenticado = authenticatedUserService.obtenerIdUsuarioActual();
        if (!cita.getUsuario().getId().equals(idUsuarioAutenticado)) {
            throw new AccesoNoAutorizadoException("Esta cita no te pertenece");
        }
        return cita;
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
        cita.setMotivo(limpiarTexto(datos.getMotivo()));
        cita.setNombreMascota(datos.getNombreMascota().trim());
        cita.setServicioNombre(datos.getServicioNombre().trim());

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
                cita.getVeterinario() != null ? cita.getVeterinario().getNombres() + " " + cita.getVeterinario().getApellidos() : null,
                cita.getMotivo(),
                cita.getTieneCostoReserva(),
                cita.getCostoReserva(),
                cita.getNombreMascota(),
                cita.getServicioNombre(),
                cita.getFechaCreacion());

        if (cita.getVeterinario() != null) {
            dto.setVeterinarioId(cita.getVeterinario().getId());
        }

        return dto;
    }

    private Long parsearId(String id, String nombreCampo) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("El " + nombreCampo + " debe ser un número válido");
        }
    }

    private boolean textoVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    private String limpiarTexto(String texto) {
        return texto == null ? null : texto.trim();
    }
}