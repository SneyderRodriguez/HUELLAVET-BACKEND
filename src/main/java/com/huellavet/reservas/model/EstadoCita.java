package com.huellavet.reservas.model;

/**
 * Representa los diferentes estados que puede tener una cita
 * durante su proceso de solicitud, atención y finalización.
 *
 * El uso de un enum evita guardar estados escritos de distintas
 * maneras y permite trabajar con un conjunto controlado de valores.
 */
public enum EstadoCita {

    // Indica que la cita fue solicitada y todavía espera una decisión.
    PENDIENTE("Pendiente"),

    // Indica que la cita fue aceptada y se encuentra programada.
    CONFIRMADA("Confirmada"),

    // Indica que la atención veterinaria comenzó y todavía no ha finalizado.
    EN_CURSO("En curso"),

    // Indica que la solicitud de la cita no fue aceptada.
    RECHAZADA("Rechazada"),

    // Indica que la fecha o la hora de la cita fueron modificadas.
    REPROGRAMADA("Reprogramada"),

    // Indica que la cita fue cancelada antes de realizarse.
    CANCELADA("Cancelada"),

    // Indica que la atención veterinaria ya fue realizada.
    COMPLETADA("Completada");

    // Conserva el texto exacto que el frontend utiliza al mostrar y comparar el estado.
    private final String valor;

    // El constructor relaciona cada constante Java con su texto visible en el frontend.
    EstadoCita(String valor) {
        // Guarda el texto recibido para reutilizarlo al convertir la entidad en DTO.
        this.valor = valor;
    }

    /**
     * Entrega el texto que el DTO utilizará en las respuestas JSON.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Convierte el texto recibido desde el frontend en una constante segura del enum.
     */
    public static EstadoCita desdeValor(String valorRecibido) {
        // values entrega todas las constantes y el ciclo revisa cada opción permitida.
        for (EstadoCita estado : values()) {
            // La comparación ignora mayúsculas para aceptar el texto sin duplicar estados.
            if (estado.valor.equalsIgnoreCase(valorRecibido)) {
                // Devuelve la constante que JPA almacenará mediante EnumType.STRING.
                return estado;
            }
        }

        // La excepción detiene la operación cuando el texto no corresponde al catálogo.
        throw new IllegalArgumentException("El estado de la cita no es válido");
    }
}
