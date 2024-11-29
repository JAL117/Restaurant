package com.Simulador.Restaurante.concurrency.monitors;

import com.Simulador.Restaurante.business.actors.Comensal;
import com.Simulador.Restaurante.business.models.EstadoMesa;
import com.Simulador.Restaurante.business.models.Mesa;

import java.util.ArrayList;
import java.util.List;

public class MesaMonitor {
    private final List<Mesa> mesas;

    public MesaMonitor(int capacidad) {
        mesas = new ArrayList<>();
        for (int i = 1; i <= capacidad; i++) {
            Mesa mesa = new Mesa(i);
            mesas.add(mesa);
        }
    }

    public synchronized Mesa asignarMesa(Comensal comensal) throws InterruptedException {
        while (true) {
            for (Mesa mesa : mesas) {
                if (mesa.getEstado() == EstadoMesa.LIBRE) {
                    mesa.asignarComensal();
                    System.out.println("Mesa " + mesa.getNumero() + " asignada al Comensal " + comensal.getId());
                    comensal.setMesaAsignada(mesa);
                    return mesa;
                }
            }
            wait(); // Esperar hasta que una mesa esté disponible
        }
    }

    public synchronized void liberarMesa(Mesa mesa) {
        mesa.liberarMesa();
        System.out.println("Mesa " + mesa.getNumero() + " liberada.");
        notifyAll(); // Notificar a los comensales que una mesa está disponible
    }

    public int getCapacidad() {
        return mesas.size();
    }

    public synchronized int mesasDisponibles() {
        return (int) mesas.stream().filter(mesa -> mesa.getEstado() == EstadoMesa.LIBRE).count();
    }

    public List<Mesa> getMesas() {
        return mesas;
    }
}