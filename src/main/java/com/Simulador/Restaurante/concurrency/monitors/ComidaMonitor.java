package com.Simulador.Restaurante.concurrency.monitors;

import com.Simulador.Restaurante.business.models.Orden;

import java.util.HashMap;
import java.util.Map;

public class ComidaMonitor {
    private final Map<Integer, Orden> bufferComidas;

    public ComidaMonitor() {
        this.bufferComidas = new HashMap<>();
    }

    public synchronized void agregarComida(Orden orden) {
        bufferComidas.put(orden.getId(), orden);
        notifyAll();
    }

    public synchronized Orden obtenerComida(int ordenId) throws InterruptedException {
        while (!bufferComidas.containsKey(ordenId)) {
            wait();
        }
        return bufferComidas.remove(ordenId);
    }
}