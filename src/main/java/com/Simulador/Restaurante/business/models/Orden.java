package com.Simulador.Restaurante.business.models;

public class Orden {
    private final int id;
    private final int comensalId;

    public Orden(int id, int comensalId) {
        this.id = id;
        this.comensalId = comensalId;
    }

    public int getId() {
        return id;
    }

    public int getComensalId() {
        return comensalId;
    }
}
