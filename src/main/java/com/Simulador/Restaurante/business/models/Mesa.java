package com.Simulador.Restaurante.business.models;

public class Mesa {
    private final int numero;
    private EstadoMesa estado;
    private double posX;
    private double posY;

    public Mesa(int numero) {
        this.numero = numero;
        this.estado = EstadoMesa.LIBRE;
    }

    public int getNumero() {
        return numero;
    }

    public synchronized EstadoMesa getEstado() {
        return estado;
    }

    public synchronized void asignarComensal() {
        this.estado = EstadoMesa.OCUPADA;
    }

    public synchronized void liberarMesa() {
        this.estado = EstadoMesa.LIBRE;
    }

    public void setPosicion(double x, double y) {
        this.posX = x;
        this.posY = y;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }


}
