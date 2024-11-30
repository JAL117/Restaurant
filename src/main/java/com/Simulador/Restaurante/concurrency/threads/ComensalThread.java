package com.Simulador.Restaurante.concurrency.threads;

import com.Simulador.Restaurante.business.actors.Comensal;
import com.Simulador.Restaurante.business.actors.Recepcionista;
import com.Simulador.Restaurante.business.models.Mesa;
import com.Simulador.Restaurante.concurrency.monitors.ComensalMonitor;
import com.Simulador.Restaurante.concurrency.monitors.MesaMonitor;
import com.Simulador.Restaurante.concurrency.monitors.OrdenMonitor;
import com.Simulador.Restaurante.presentation.views.RestauranteView;

import java.util.Random;

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
            Thread.sleep(2000);

            view.moverComensalARecepcion(comensal.getId());
            System.out.println("Recepcionista está registrando al Comensal " + comensal.getId());

            Mesa mesa = mesaMonitor.asignarMesa(comensal);
            recepcionista.asignarMesa(comensal, mesa);

            view.moverComensalAMesa(comensal.getId(), mesa.getNumero());
            view.asignarMesa(comensal.getId(), mesa.getNumero());
            System.out.println("Comensal " + comensal.getId() + " asignado a la Mesa " + mesa.getNumero());

            view.actualizarEstadoMesa(mesa.getNumero(), mesa.getEstado());


            comensalMonitor.agregarComensal(comensal);


            synchronized (comensal) {
                comensal.wait();
            }


            Random random = new Random();
            int tiempoDeComida = 500 + random.nextInt(2000);

            System.out.println("Comensal " + comensal.getId() + " está comiendo.");
            Thread.sleep(tiempoDeComida); // Tiempo de comer


            System.out.println("Comensal " + comensal.getId() + " ha terminado de comer y se va.");
            mesaMonitor.liberarMesa(mesa);
            view.actualizarEstadoMesa(mesa.getNumero(), mesa.getEstado());
            view.retirarComensal(comensal.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}