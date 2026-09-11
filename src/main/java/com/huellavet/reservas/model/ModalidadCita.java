package com.huellavet.reservas.model;

public enum ModalidadCita {

    CLINICA("clinica"),

    DOMICILIO("domicilio"),

    VIRTUAL("virtual");

    private final String valor;

    ModalidadCita(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static ModalidadCita desdeValor(String valorRecibido) {
        for (ModalidadCita modalidad : values()) {
            if (modalidad.valor.equalsIgnoreCase(valorRecibido)) {
                return modalidad;
            }
        }
        throw new IllegalArgumentException("La modalidad de la cita no es válida");
    }
}
