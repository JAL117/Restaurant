package com.Simulador.Restaurante.business.actors;

import com.Simulador.Restaurante.business.models.Mesa;

public class Recepcionista {

    public void asignarMesa(Comensal comensal, Mesa mesa) {
        comensal.setMesaAsignada(mesa);
    }
}
