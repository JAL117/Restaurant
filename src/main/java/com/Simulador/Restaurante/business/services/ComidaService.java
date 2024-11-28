package com.Simulador.Restaurante.business.services;

import java.util.concurrent.atomic.AtomicInteger;

public class ComidaService {
    private static final AtomicInteger comidaIdGenerator = new AtomicInteger(1);

    public static int getNextComidaId() {
        return comidaIdGenerator.getAndIncrement();
    }
}