package com.Simulador.Restaurante.entities;

import com.Simulador.Restaurante.business.actors.Comensal;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import com.Simulador.Restaurante.business.actors.Recepcionista;
import com.Simulador.Restaurante.business.models.Mesa;

public class Receptionist extends Entity {

    private Recepcionista recepcionista;

    public Receptionist(SpawnData data) {
        FXGL.entityBuilder(data)
                .view(new Circle(10, Color.YELLOW))
                .type(EntityType.RECEPTIONIST)
                .buildAndAttach();

        this.recepcionista = new Recepcionista();
    }

    public void asignarMesaComensal(Comensal comensal, Mesa mesa) {
        recepcionista.asignarMesa(comensal, mesa);
    }
}
