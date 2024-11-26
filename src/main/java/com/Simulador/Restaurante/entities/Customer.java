// Archivo: Customer.java
package com.Simulador.Restaurante.entities;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Customer extends Entity {
    public Customer(SpawnData data) {
        FXGL.entityBuilder(data)
                .view(new Circle(10, Color.GREEN))
                .type(EntityType.CUSTOMER)
                .buildAndAttach();
    }
}