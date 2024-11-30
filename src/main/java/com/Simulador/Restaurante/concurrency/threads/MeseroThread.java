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
                // 1. Retirar comensal de la cola de espera
                Comensal comensal = comensalMonitor.retirarComensal();
                Mesa mesa = comensal.getMesaAsignada();

                // 2. Animación de atender al comensal
                view.atenderMesa(comensal.getId(), mesa.getNumero());
                view.moverMesero(mesero.getId(), mesa.getPosX() + 10, mesa.getPosY()-30);
                System.out.println("Mesero " + mesero.getId() + " está atendiendo al Comensal " + comensal.getId());

                Thread.sleep(1000); // Simular el tiempo de atención

                // 3. Crear y agregar la orden
                Orden orden = new Orden(comensal.getId(), comensal.getId());
                ordenMonitor.agregarOrden(orden);
                System.out.println("Orden " + orden.getId() + " añadida al buffer de órdenes.");

                // 4. Esperar a que la comida esté lista
                Orden comidaLista = comidaMonitor.obtenerComida(orden.getId());
                System.out.println("Comida " + comidaLista.getId() + " retirada del buffer de comidas.");

                // 5. Animación de entregar la comida

                System.out.println("Mesero " + mesero.getId() + " está sirviendo la Orden " + comidaLista.getId());

                Thread.sleep(2000); // Simular el tiempo de entrega
                view.entregarOrden(mesero.getId(),mesa.getNumero());

                
                // 6. Notificar al comensal para que pueda comer
                synchronized (comensal) {
                    comensal.notify();
                }

                // 7. Regresar a la recepción
                view.moverMesero(mesero.getId(), view.getReceptionX() + 50, view.getReceptionY() + mesero.getId() * 15);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}

