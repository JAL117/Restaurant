package com.Simulador.Restaurante.concurrency.monitors;

import com.Simulador.Restaurante.business.actors.Comensal;

import java.util.LinkedList;
import java.util.Queue;

public class ComensalMonitor {
    private final Queue<Comensal> colaEspera;

    public ComensalMonitor() {
        this.colaEspera = new LinkedList<>();
    }

    public synchronized void agregarComensal(Comensal comensal) {
        colaEspera.add(comensal);
        notifyAll();
    }

    public synchronized Comensal retirarComensal() throws InterruptedException {
        while (colaEspera.isEmpty()) {
            wait();
        }
        return colaEspera.poll();
    }
}
