package com.Simulador.Restaurante.business.services;

public class CocinaService {
    public static int getTiempoPreparacion() {
        return (int) (Math.random() * 3000) + 2000;
    }
}

