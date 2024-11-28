package com.Simulador.Restaurante.business.models;

public class Comida {
    private final Orden orden;

    public Comida(Orden orden) {
        this.orden = orden;
    }

    public Orden getOrden() {
        return orden;
    }
}
