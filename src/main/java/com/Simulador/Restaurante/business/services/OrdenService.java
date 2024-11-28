package com.Simulador.Restaurante.business.services;

import java.util.concurrent.atomic.AtomicInteger;

public class OrdenService {
    private static final AtomicInteger ordenIdGenerator = new AtomicInteger(1);

    public static int getNextOrdenId() {
        return ordenIdGenerator.getAndIncrement();
    }
}
