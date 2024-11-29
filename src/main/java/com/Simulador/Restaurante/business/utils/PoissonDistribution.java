package com.Simulador.Restaurante.business.utils;

public class PoissonDistribution {


    public static int generarTiempoLlegada;

    public static double generarTiempoLlegada(double lambda) {
        double u = Math.random();
        return generarTiempoLlegada -Math.log(1 - u) / lambda;


    }
}