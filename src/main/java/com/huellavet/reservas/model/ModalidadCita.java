package com.huellavet.reservas.model;

/**
 * Representa las modalidades disponibles para prestar el servicio
 * veterinario asociado con una cita.
 *
 * El enum conserva valores uniformes en Java y entrega al frontend
 * los textos en minúscula que actualmente utiliza su lógica visual.
 */
public enum ModalidadCita {

    // Indica que la atención se realizará presencialmente en la clínica.
    CLINICA("clinica"),

    // Indica que la atención se realizará en la ubicación del usuario.
    DOMICILIO("domicilio"),

    // Indica que la orientación se realizará mediante atención virtual.
    VIRTUAL("virtual");

    // Conserva el valor exacto que se intercambia con el frontend mediante JSON.
    private final String valor;

    // El constructor relaciona la constante Java con el valor enviado por el formulario.
    ModalidadCita(String valor) {
        // Conserva el texto recibido para utilizarlo en las conversiones posteriores.
        this.valor = valor;
    }

    /**
     * Entrega el texto que el DTO incluirá en las respuestas de la API.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Permite recibir la modalidad sin depender del uso de mayúsculas o minúsculas.
     */
    public static ModalidadCita desdeValor(String valorRecibido) {
        // El ciclo recorre únicamente las modalidades definidas como válidas.
        for (ModalidadCita modalidad : values()) {
            // La comparación ignora diferencias entre letras mayúsculas y minúsculas.
            if (modalidad.valor.equalsIgnoreCase(valorRecibido)) {
                // Devuelve la constante segura que se almacenará en la entidad.
                return modalidad;
            }
        }

        // Informa al servicio que el texto recibido no pertenece al catálogo permitido.
        throw new IllegalArgumentException("La modalidad de la cita no es válida");
    }
}
