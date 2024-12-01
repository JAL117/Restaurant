package com.Simulador.Restaurante.business.services;

import com.Simulador.Restaurante.business.actors.Cocinero;
import com.Simulador.Restaurante.business.actors.Comensal;
import com.Simulador.Restaurante.business.actors.Recepcionista;
import com.Simulador.Restaurante.business.actors.Mesero;
import com.Simulador.Restaurante.concurrency.monitors.ComidaMonitor;
import com.Simulador.Restaurante.concurrency.monitors.ComensalMonitor;
import com.Simulador.Restaurante.concurrency.monitors.MesaMonitor;
import com.Simulador.Restaurante.concurrency.threads.MeseroThread;
import com.Simulador.Restaurante.concurrency.threads.CocineroThread;
import com.Simulador.Restaurante.concurrency.threads.ComensalThread;
import com.Simulador.Restaurante.concurrency.monitors.OrdenMonitor;
import com.Simulador.Restaurante.presentation.views.RestauranteView;
import com.Simulador.Restaurante.business.utils.PoissonDistribution;


import java.util.ArrayList;
import java.util.List;

public class RestauranteService {
    private final MesaMonitor mesaMonitor;
    private final OrdenMonitor ordenMonitor;
    private final ComidaMonitor comidaMonitor;
    private final ComensalMonitor comensalMonitor;
    private final Recepcionista recepcionista;
    private final List<Mesero> meseros;
    private final List<Cocinero> cocineros;
    private final List<Thread> threads;
    private final RestauranteView view;
    private volatile boolean running;
    private int totalComensales = 0;
    private int ordenesProcesadas = 0;

    public RestauranteService(int capacidad, int cantidadMeseros, int cantidadCocineros) {
        this.mesaMonitor = new MesaMonitor(capacidad);
        this.ordenMonitor = new OrdenMonitor();
        this.comidaMonitor = new ComidaMonitor();
        this.comensalMonitor = new ComensalMonitor();
        this.recepcionista = new Recepcionista();
        this.meseros = new ArrayList<>();
        this.cocineros = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.view = new RestauranteView(mesaMonitor.getMesas());
        this.running = false;

        for (int i = 1; i <= cantidadMeseros; i++) {
            meseros.add(new Mesero(i));
        }

        for (int i = 1; i <= cantidadCocineros; i++) {
            cocineros.add(new Cocinero(i));
        }
    }

    public void iniciarSimulacion() {
        if (view == null) {
            throw new IllegalStateException("La vista no ha sido inicializada correctamente");
        }
        running = true;

        view.setTotalCocineros(cocineros.size());

        for (Mesero mesero : meseros) {
            view.añadirMesero(mesero.getId());
        }

        for (Cocinero cocinero : cocineros) {
            view.añadirCocinero(cocinero.getId());
        }

        for (Mesero mesero : meseros) {
            MeseroThread meseroThread = new MeseroThread(mesero, ordenMonitor, comidaMonitor, comensalMonitor, view, this);
            threads.add(meseroThread);
            meseroThread.start();
        }

        for (Cocinero cocinero : cocineros) {
            CocineroThread cocineroThread = new CocineroThread(cocinero, ordenMonitor, comidaMonitor, view, this);
            threads.add(cocineroThread);
            cocineroThread.start();
        }

        Thread generadorComensales = new Thread(() -> {
            int comensalId = 1;
            while (running) {
                try {
                    double lambda = 1.0;
                    double tiempoEntreLlegadas = PoissonDistribution.generarTiempoLlegada(lambda);
                    Thread.sleep((long) (tiempoEntreLlegadas * 1000));

                    Comensal comensal = new Comensal(comensalId++);
                    ComensalThread comensalThread = new ComensalThread(comensal, recepcionista, mesaMonitor, comensalMonitor, view, ordenMonitor);
                    threads.add(comensalThread);
                    comensalThread.start();

                    totalComensales++;


                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        generadorComensales.start();
        threads.add(generadorComensales);
    }

    public void finalizarSimulacion() {
        running = false;
        for (Thread thread : threads) {
            thread.interrupt();
        }
        System.out.println("Simulación finalizada.");
    }

    public synchronized void incrementarOrdenesProcesadas() {
        ordenesProcesadas++;

    }

    public RestauranteView getView() {
        return view;
    }


}
