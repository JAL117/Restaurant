package com.Simulador.Restaurante.concurrency.threads;

import com.Simulador.Restaurante.business.actors.Comensal;
import com.Simulador.Restaurante.business.actors.Recepcionista;
import com.Simulador.Restaurante.business.models.Mesa;
import com.Simulador.Restaurante.concurrency.monitors.ComensalMonitor;
import com.Simulador.Restaurante.concurrency.monitors.MesaMonitor;
import com.Simulador.Restaurante.concurrency.monitors.OrdenMonitor;
import com.Simulador.Restaurante.presentation.views.RestauranteView;

public class ComensalThread extends Thread {
    private final Comensal comensal;
    private final Recepcionista recepcionista;
    private final MesaMonitor mesaMonitor;
    private final ComensalMonitor comensalMonitor;
    private final RestauranteView view;
    private final OrdenMonitor ordenMonitor;
    private static int ordenIdCounter = 1;

    public ComensalThread(Comensal comensal, Recepcionista recepcionista, MesaMonitor mesaMonitor, ComensalMonitor comensalMonitor, RestauranteView view, OrdenMonitor ordenMonitor) {
        this.comensal = comensal;
        this.recepcionista = recepcionista;
        this.mesaMonitor = mesaMonitor;
        this.comensalMonitor = comensalMonitor;
        this.view = view;
        this.ordenMonitor = ordenMonitor;
    }

    @Override
    public void run() {
        try {
            view.moverComensalAEntrada(comensal.getId());
            Thread.sleep(500);

            view.moverComensalARecepcion(comensal.getId());
            System.out.println("Recepcionista está registrando al Comensal " + comensal.getId());

            Mesa mesa = mesaMonitor.asignarMesa(comensal);
            recepcionista.asignarMesa(comensal, mesa);
            System.out.println("Recepcionista ha asignado la Mesa " + mesa.getNumero());

            view.actualizarEstadoMesa(mesa.getNumero(), mesa.getEstado());

            view.moverComensalAMesa(comensal.getId(), mesa.getNumero());

            comensalMonitor.agregarComensal(comensal);
            System.out.println("Comensal " + comensal.getId() + " añadido a la cola de espera.");

            synchronized (comensal) {
                comensal.wait();
            }

            Thread.sleep(5000);

            System.out.println("Comensal " + comensal.getId() + " ha terminado de comer y se va.");
            view.moverComensalFuera(comensal.getId());

            mesaMonitor.liberarMesa(mesa);
            view.actualizarEstadoMesa(mesa.getNumero(), mesa.getEstado());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
