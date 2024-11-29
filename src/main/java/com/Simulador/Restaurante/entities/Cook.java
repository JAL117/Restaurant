package com.Simulador.Restaurante.entities;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cook extends Entity {
    private boolean isPreparing;
    public Cook(SpawnData data) {
        FXGL.entityBuilder(data)
                .view(new Circle(10, Color.RED))
                .type(EntityType.COOK)
                .buildAndAttach();

        this.isPreparing = false;
    }


    public void moveTo(double x, double y) {
        setPosition(x, y);
    }


    public void setPreparing(boolean isPreparing) {
        this.isPreparing = isPreparing;


        if (isPreparing) {
            getViewComponent().clearChildren();
            getViewComponent().addChild(new Circle(10, Color.GREEN));
        } else {
            getViewComponent().clearChildren();
            getViewComponent().addChild(new Circle(10, Color.RED));
        }
    }


    public boolean isPreparing() {
        return isPreparing;
    }
}
