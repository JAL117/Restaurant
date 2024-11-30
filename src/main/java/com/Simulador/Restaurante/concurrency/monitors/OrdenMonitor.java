package com.Simulador.Restaurante.concurrency.monitors;

import com.Simulador.Restaurante.business.models.Orden;

import java.util.LinkedList;
import java.util.Queue;

public class OrdenMonitor {
    private final Queue<Orden> bufferOrdenes;

    public OrdenMonitor() {
        this.bufferOrdenes = new LinkedList<>();
    }

    public synchronized void agregarOrden(Orden orden) {
        bufferOrdenes.add(orden);

        notifyAll();
    }

    public synchronized Orden retirarOrden() throws InterruptedException {
        while (bufferOrdenes.isEmpty()) {
            wait();
        }
        return bufferOrdenes.poll();
    }
}
