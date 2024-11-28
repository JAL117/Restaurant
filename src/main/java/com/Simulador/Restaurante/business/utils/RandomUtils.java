package com.Simulador.Restaurante.business.utils;

public class RandomUtils {
    public static int generarTiempoAleatorio(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
