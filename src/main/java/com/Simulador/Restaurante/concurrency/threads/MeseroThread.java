package com.Simulador.Restaurante.concurrency.threads;

import com.Simulador.Restaurante.business.actors.Comensal;
import com.Simulador.Restaurante.business.actors.Mesero;
import com.Simulador.Restaurante.business.models.Mesa;
import com.Simulador.Restaurante.business.models.Orden;
import com.Simulador.Restaurante.concurrency.monitors.ComensalMonitor;
import com.Simulador.Restaurante.concurrency.monitors.ComidaMonitor;
import com.Simulador.Restaurante.concurrency.monitors.OrdenMonitor;
import com.Simulador.Restaurante.presentation.views.RestauranteView;
import com.Simulador.Restaurante.business.services.RestauranteService;

public class MeseroThread extends Thread {
    private final Mesero mesero;
    private final OrdenMonitor ordenMonitor;
    private final ComidaMonitor comidaMonitor;
    private final ComensalMonitor comensalMonitor;
    private final RestauranteView view;

    public MeseroThread(Mesero mesero, OrdenMonitor ordenMonitor, ComidaMonitor comidaMonitor, ComensalMonitor comensalMonitor, RestauranteView view, RestauranteService service) {
        this.mesero = mesero;
        this.ordenMonitor = ordenMonitor;
        this.comidaMonitor = comidaMonitor;
        this.comensalMonitor = comensalMonitor;
        this.view = view;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Comensal comensal = comensalMonitor.retirarComensal();
                System.out.println("Comensal " + comensal.getId() + " retirado de la cola de espera.");
                System.out.println("Mesero " + mesero.getId() + " está atendiendo al Comensal " + comensal.getId());

                Mesa mesa = comensal.getMesaAsignada();
                view.moverMesero(mesero.getId(), mesa.getPosX() + 20, mesa.getPosY());

                Thread.sleep(1000);
                Orden orden = new Orden(comensal.getId(), comensal.getId());
                ordenMonitor.agregarOrden(orden);
                System.out.println("Orden " + orden.getId() + " añadida al buffer de órdenes.");


                Orden comidaLista = comidaMonitor.obtenerComida(orden.getId());
                System.out.println("Comida " + comidaLista.getId() + " retirada del buffer de comidas.");

                System.out.println("Mesero " + mesero.getId() + " está sirviendo la Orden " + comidaLista.getId());

                view.entregarOrden(mesero.getId(), mesa.getNumero());

                synchronized (comensal) {
                    comensal.notify();
                }

                view.moverMesero(mesero.getId(), view.getReceptionX() + 50, view.getReceptionY() + mesero.getId() * 15);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

