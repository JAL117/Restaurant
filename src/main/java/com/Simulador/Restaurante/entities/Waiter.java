package com.Simulador.Restaurante.entities;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Waiter extends Entity {
    public Waiter(SpawnData data) {
        super();
        setView(new Circle(15, Color.BLUE));
    }

    private void setView(Circle circle) {

    }
}