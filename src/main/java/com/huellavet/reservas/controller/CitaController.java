package com.huellavet.reservas.controller;

import com.huellavet.reservas.dto.CitaDto;
import com.huellavet.reservas.service.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controla las solicitudes HTTP relacionadas con las citas veterinarias.
 *
 * Esta clase recibe las peticiones enviadas desde el cliente de la API,
 * delega las reglas de negocio en CitaService y construye una respuesta
 * HTTP adecuada para cada resultado.
 */

// @RestController permite que Spring reconozca esta clase como un controlador REST.
// También convierte automáticamente los objetos retornados en respuestas JSON.
@RestController

// Define /api/citas como la ruta principal compartida por todos los métodos del controlador.
@RequestMapping("/api/citas")
public class CitaController {

    // Guarda la referencia al servicio que contiene la lógica de negocio de las citas.
    private final CitaService citaService;

    /**
     * Recibe el servicio mediante inyección de dependencias por constructor.
     *
     * @param citaService servicio administrado por Spring que permite consultar,
     *                    crear, actualizar y eliminar citas.
     */
    public CitaController(CitaService citaService) {

        // Asigna el servicio recibido al atributo para utilizarlo en los endpoints.
        this.citaService = citaService;

    }

    /**
     * Consulta y devuelve todas las citas registradas.
     *
     * @return respuesta HTTP 200 con la lista de citas convertidas en DTO.
     */

    // @GetMapping atiende las solicitudes GET realizadas a /api/citas.
    @GetMapping
    public ResponseEntity<List<CitaDto>> listarTodas() {

        // ResponseEntity.ok construye una respuesta 200 con la lista entregada por el servicio.
        return ResponseEntity.ok(citaService.listarTodas());

    }

    /**
     * Busca una cita específica mediante el identificador incluido en la URL.
     *
     * @param id identificador numérico de la cita que se desea consultar.
     * @return respuesta 200 cuando la cita existe o respuesta 404 cuando no se encuentra.
     */

    // {id} representa la parte variable de la ruta /api/citas/{id}.
    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> buscarPorId(@PathVariable Long id) {

        // El servicio devuelve Optional porque la cita solicitada puede no existir.
        return citaService.buscarPorId(id)

                // Si Optional contiene una cita, se construye una respuesta HTTP 200.
                .map(ResponseEntity::ok)

                // Si Optional está vacío, se informa que el recurso no fue encontrado.
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    /**
     * Crea una cita con la información recibida en el cuerpo de la petición.
     *
     * @param datos DTO construido a partir del JSON enviado por el cliente.
     * @return respuesta 201 con la cita creada o respuesta 400 si los datos no son válidos.
     */

    // @PostMapping atiende solicitudes POST enviadas a /api/citas.
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CitaDto datos) {

        // try permite controlar las validaciones que el servicio comunica mediante una excepción.
        try {

            // Solicita al servicio validar, convertir y guardar la nueva cita.
            CitaDto citaCreada = citaService.crear(datos);

            // Devuelve 201 Created porque se creó un nuevo recurso en el sistema.
            return ResponseEntity.status(HttpStatus.CREATED).body(citaCreada);

        } catch (IllegalArgumentException error) {

            // Devuelve 400 Bad Request junto con la explicación de la validación incumplida.
            return ResponseEntity.badRequest().body(error.getMessage());

        }

    }

    /**
     * Actualiza una cita existente utilizando el ID de la ruta y los datos del cuerpo.
     *
     * @param id identificador de la cita que se desea modificar.
     * @param datos información editable recibida mediante JSON.
     * @return respuesta 200, 404 o 400 según el resultado de la operación.
     */

    // @PutMapping relaciona este método con PUT /api/citas/{id}.
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(

            // @PathVariable toma el identificador incluido en la URL.
            @PathVariable Long id,

            // @RequestBody transforma el JSON recibido en un objeto CitaDto.
            @RequestBody CitaDto datos) {

        // try permite responder de forma controlada cuando una regla de negocio no se cumple.
        try {

            // El ID de la URL tiene prioridad y evita que el cuerpo cambie otro registro.
            Optional<CitaDto> citaActualizada = citaService.actualizar(id, datos);

            // Comprueba si el repositorio no encontró una cita con el identificador indicado.
            if (citaActualizada.isEmpty()) {

                // Devuelve 404 Not Found porque no existe una cita que pueda actualizarse.
                return ResponseEntity.notFound().build();

            }

            // Devuelve 200 OK con los datos resultantes de la actualización.
            return ResponseEntity.ok(citaActualizada.get());

        } catch (IllegalArgumentException error) {

            // Devuelve 400 Bad Request cuando los nuevos datos no cumplen las validaciones.
            return ResponseEntity.badRequest().body(error.getMessage());

        }

    }

    /**
     * Elimina la cita correspondiente al identificador recibido.
     *
     * @param id identificador de la cita que se desea eliminar.
     * @return respuesta 204 si se eliminó o respuesta 404 si la cita no existe.
     */

    // @DeleteMapping atiende solicitudes DELETE realizadas a /api/citas/{id}.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        // El servicio devuelve true únicamente cuando encontró y eliminó la cita.
        if (citaService.eliminarPorId(id)) {

            // 204 No Content confirma la eliminación sin enviar un cuerpo de respuesta.
            return ResponseEntity.noContent().build();

        }

        // Si el servicio devuelve false, se informa que la cita solicitada no existe.
        return ResponseEntity.notFound().build();

    }

}
