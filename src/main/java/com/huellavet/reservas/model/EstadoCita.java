package com.huellavet.reservas.model;

public enum EstadoCita {

    PENDIENTE("Pendiente"),

    CONFIRMADA("Confirmada"),

    EN_CURSO("En curso"),

    RECHAZADA("Rechazada"),

    REPROGRAMADA("Reprogramada"),

    CANCELADA("Cancelada"),

    COMPLETADA("Completada");

    private final String valor;

    EstadoCita(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static EstadoCita desdeValor(String valorRecibido) {
        for (EstadoCita estado : values()) {
            if (estado.valor.equalsIgnoreCase(valorRecibido)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("El estado de la cita no es válido");
    }
}
