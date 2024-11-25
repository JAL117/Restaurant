package com.Simulador.Restaurante.entities;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RestaurantEntityFactory implements EntityFactory {
    @Spawns("customer")
    public Entity newCustomer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new Circle(15, Color.GREEN))
                .build();
    }

    @Spawns("waiter")
    public Entity newWaiter(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new Circle(15, Color.BLUE))
                .build();
    }

    @Spawns("cook")
    public Entity newCook(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new Circle(15, Color.RED))
                .build();
    }
}