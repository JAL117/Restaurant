package com.Simulador.Restaurante.business.utils;

public class PoissonDistribution {
    public static double generarTiempoLlegada(double lambda) {
        double u = Math.random();
        return -Math.log(1 - u) / lambda;
    }
}