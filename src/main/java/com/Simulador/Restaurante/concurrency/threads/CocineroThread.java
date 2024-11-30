package com.Simulador.Restaurante.concurrency.threads;

import com.Simulador.Restaurante.business.actors.Cocinero;
import com.Simulador.Restaurante.business.models.Orden;
import com.Simulador.Restaurante.concurrency.monitors.ComidaMonitor;
import com.Simulador.Restaurante.concurrency.monitors.OrdenMonitor;
import com.Simulador.Restaurante.presentation.views.RestauranteView;
import com.Simulador.Restaurante.business.services.RestauranteService;

public class CocineroThread extends Thread {
    private final Cocinero cocinero;
    private final OrdenMonitor ordenMonitor;
    private final ComidaMonitor comidaMonitor;
    private final RestauranteView view;
    private final RestauranteService service;

    public CocineroThread(Cocinero cocinero, OrdenMonitor ordenMonitor, ComidaMonitor comidaMonitor, RestauranteView view, RestauranteService service) {
        this.cocinero = cocinero;
        this.ordenMonitor = ordenMonitor;
        this.comidaMonitor = comidaMonitor;
        this.view = view;
        this.service = service;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Orden orden = ordenMonitor.retirarOrden();
                System.out.println("Orden " + orden.getId() + " retirada del buffer de órdenes.");
                System.out.println("Cocinero " + cocinero.getId() + " está preparando la Orden " + orden.getId());

                Thread.sleep(5000);

                comidaMonitor.agregarComida(orden);
                System.out.println("Cocinero " + cocinero.getId() + " ha terminado de preparar la Orden " + orden.getId());
                System.out.println("Comida " + orden.getId() + " añadida al buffer de comidas.");

                service.incrementarOrdenesProcesadas();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
