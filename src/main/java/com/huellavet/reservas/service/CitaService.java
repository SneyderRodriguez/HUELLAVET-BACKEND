package com.huellavet.reservas.service;

import com.huellavet.reservas.dto.CitaDto;
import com.huellavet.reservas.model.CitaModel;
import com.huellavet.reservas.model.EstadoCita;
import com.huellavet.reservas.model.ModalidadCita;
import com.huellavet.reservas.repository.CitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Contiene las reglas de negocio y coordina el acceso a los datos de las citas.
 */
// @Service permite que Spring cree y administre una instancia de esta clase.
@Service
public class CitaService {

    // La referencia es final porque se recibe una vez y no debe cambiar durante el servicio.
    private final CitaRepository citaRepository;

    // La inyección por constructor permite que Spring entregue el repositorio requerido.
    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    /**
     * Consulta todas las entidades y las transforma en DTO antes de responder.
     */
    // readOnly indica que esta operación consulta información sin modificarla.
    @Transactional(readOnly = true)
    public List<CitaDto> listarTodas() {
        // findAll obtiene las entidades; stream permite recorrerlas y map transforma
        // cada CitaModel en CitaDto antes de reunir el resultado en una lista.
        return citaRepository.findAll().stream()
                .map(this::convertirADto)
                .toList();
    }

    /**
     * Busca una cita por su identificador y conserva Optional para representar
     * de forma explícita que el registro puede no existir.
     */
    // La transacción de solo lectura acompaña la consulta sin solicitar escritura.
    @Transactional(readOnly = true)
    public Optional<CitaDto> buscarPorId(Long id) {
        // map ejecuta la conversión solamente cuando Optional contiene una entidad.
        return citaRepository.findById(id)
                .map(this::convertirADto);
    }

    /**
     * Valida la solicitud, construye una entidad nueva y la guarda en PostgreSQL.
     */
    // La transacción agrupa la validación y el guardado como una sola operación.
    @Transactional
    public CitaDto crear(CitaDto datos) {
        // Detiene la creación antes de guardar si falta información obligatoria.
        validarDatos(datos);

        // Crea una entidad sin ID para que PostgreSQL lo genere al insertar el registro.
        CitaModel cita = new CitaModel();

        // true informa que deben aplicarse los valores iniciales propios de una cita nueva.
        copiarDatosEditables(datos, cita, true);

        // save persiste la entidad y la respuesta se convierte a DTO para no exponerla.
        return convertirADto(citaRepository.save(cita));
    }

    /**
     * Modifica únicamente una cita existente y conserva su ID y fecha de creación.
     */
    // La transacción permite consultar y actualizar el registro de manera coordinada.
    @Transactional
    public Optional<CitaDto> actualizar(Long id, CitaDto datos) {
        // Optional representa de forma explícita que el ID podría no estar registrado.
        Optional<CitaModel> citaEncontrada = citaRepository.findById(id);

        // Si no existe, se devuelve un Optional vacío para que el controlador responda 404.
        if (citaEncontrada.isEmpty()) {
            return Optional.empty();
        }

        // La validación se realiza después de comprobar que el recurso sí existe.
        validarDatos(datos);

        // get recupera la entidad porque la condición anterior confirmó su existencia.
        CitaModel cita = citaEncontrada.get();

        // false evita aplicar nuevamente comportamientos reservados para la creación.
        copiarDatosEditables(datos, cita, false);

        // Optional.of comunica al controlador que la actualización produjo una respuesta.
        return Optional.of(convertirADto(citaRepository.save(cita)));
    }

    /**
     * Elimina la cita indicada y comunica si el registro realmente existía.
     */
    // La eliminación necesita una transacción de escritura sobre la base de datos.
    @Transactional
    public boolean eliminarPorId(Long id) {
        // La comprobación previa permite diferenciar una eliminación de un ID inexistente.
        if (!citaRepository.existsById(id)) {
            return false;
        }

        // deleteById ejecuta la eliminación mediante el método heredado de JpaRepository.
        citaRepository.deleteById(id);

        // true confirma al controlador que el registro existía y fue solicitado para borrar.
        return true;
    }

    /**
     * Reúne las validaciones que protegen la coherencia mínima de una cita.
     */
    private void validarDatos(CitaDto datos) {
        if (datos == null) {
            throw new IllegalArgumentException("Los datos de la cita son obligatorios");
        }

        if (textoVacio(datos.getUsuarioId())) {
            throw new IllegalArgumentException("El usuario de la cita es obligatorio");
        }

        if (textoVacio(datos.getMascotaId())) {
            throw new IllegalArgumentException("La mascota de la cita es obligatoria");
        }

        if (datos.getServicioId() == null) {
            throw new IllegalArgumentException("El servicio de la cita es obligatorio");
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

        if (modalidad == ModalidadCita.DOMICILIO
                && textoVacio(datos.getUbicacion())) {
            throw new IllegalArgumentException("La ubicación es obligatoria para una cita a domicilio");
        }

        if (textoVacio(datos.getNombreMascota())) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio");
        }

        if (textoVacio(datos.getServicioNombre())) {
            throw new IllegalArgumentException("El nombre del servicio es obligatorio");
        }

        if (Boolean.TRUE.equals(datos.getTieneCostoReserva())) {
            if (datos.getCostoReserva() == null
                    || datos.getCostoReserva().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("El costo de reserva debe ser igual o mayor que cero");
            }
        }
    }

    /**
     * Copia los campos que el cliente puede proporcionar sin reemplazar valores internos.
     */
    private void copiarDatosEditables(CitaDto datos, CitaModel cita, boolean esNueva) {
        // trim elimina espacios externos de los identificadores y nombres obligatorios.
        cita.setUsuarioId(datos.getUsuarioId().trim());
        cita.setMascotaId(datos.getMascotaId().trim());
        cita.setServicioId(datos.getServicioId());
        cita.setFecha(datos.getFecha());
        cita.setHora(datos.getHora());
        cita.setModalidad(ModalidadCita.desdeValor(datos.getModalidad()));
        cita.setUbicacion(limpiarTexto(datos.getUbicacion()));
        cita.setVeterinario(limpiarTexto(datos.getVeterinario()));
        cita.setMotivo(limpiarTexto(datos.getMotivo()));
        cita.setNombreMascota(datos.getNombreMascota().trim());
        cita.setServicioNombre(datos.getServicioNombre().trim());

        // Convierte el texto del frontend a la constante Java cuando fue informado.
        if (datos.getEstado() != null) {
            cita.setEstado(EstadoCita.desdeValor(datos.getEstado()));
        } else if (esNueva) {
            // Una cita nueva sin estado explícito comienza en Pendiente.
            cita.setEstado(EstadoCita.PENDIENTE);
        }

        // Boolean.TRUE.equals evita un error si el DTO recibe null en este campo.
        boolean tieneCosto = Boolean.TRUE.equals(datos.getTieneCostoReserva());
        cita.setTieneCostoReserva(tieneCosto);

        // El operador ternario conserva el costo informado o usa cero cuando no hay cobro.
        cita.setCostoReserva(tieneCosto ? datos.getCostoReserva() : BigDecimal.ZERO);
    }

    /**
     * Construye el objeto que el controlador devolverá como respuesta JSON.
     */
    private CitaDto convertirADto(CitaModel cita) {
        // El constructor reúne la entidad en un objeto de transporte; los enums se
        // convierten a los textos exactos que el frontend espera recibir y mostrar.
        return new CitaDto(
                cita.getId(),
                cita.getUsuarioId(),
                cita.getMascotaId(),
                cita.getServicioId(),
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
    }

    // Centraliza la comprobación de cadenas nulas, vacías o compuestas por espacios.
    private boolean textoVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    // Elimina espacios externos y conserva null cuando el campo no fue informado.
    private String limpiarTexto(String texto) {
        return texto == null ? null : texto.trim();
    }
}
