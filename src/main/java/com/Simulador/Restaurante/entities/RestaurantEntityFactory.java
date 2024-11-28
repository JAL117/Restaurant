package com.Simulador.Restaurante.entities;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;


public class RestaurantEntityFactory implements EntityFactory {
    @Spawns("customer")
    public Entity newCustomer(SpawnData data) {
        return new Customer(data);
    }

    @Spawns("waiter")
    public Entity newWaiter(SpawnData data) {
        return new Waiter(data);
    }

    @Spawns("cook")
    public Entity newCook(SpawnData data) {
        return new Cook(data);
    }

    @Spawns("receptionist")
    public Entity newReceptionist(SpawnData data) {
        return new Receptionist(data);
    }



    public void spawnWaiters(int count) {
        for (int i = 0; i < count; i++) {
            FXGL.spawn(EntityType.WAITER.name().toLowerCase(), 240 + i * 50, 100);
        }
    }

    public void spawnCooks(int count) {
        for (int i = 0; i < count; i++) {
            FXGL.spawn(EntityType.COOK.name().toLowerCase(), 1220, 250 + i * 50);
        }
    }

    public void spawnCustomers(int count) {
        for (int i = 0; i < count; i++) {
            FXGL.spawn(EntityType.CUSTOMER.name().toLowerCase(), 60, 550 + i *  50);
        }
    }

    public void spawnReceptionist() {
        FXGL.spawn(EntityType.RECEPTIONIST.name().toLowerCase(), 90, 480);
    }



    public void spawnInitialEntities() {
        spawnWaiters(2);
        spawnCooks(3);
        spawnCustomers(10);
        spawnReceptionist();
    }
}


































/*package com.Simulador.Restaurante.entities;

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
                .view(new Circle(10, Color.GREEN))
                .type(EntityType.CUSTOMER)
                .build();
    }

    @Spawns("waiter")
    public Entity newWaiter(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new Circle(10, Color.BLUE))
                .type(EntityType.WAITER)
                .build();
    }

    @Spawns("cook")
    public Entity newCook(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new Circle(10, Color.RED))
                .type(EntityType.COOK)
                .build();
    }

    @Spawns("receptionist")
    public Entity newReceptionist(SpawnData data){
        return FXGL.entityBuilder(data)
                .view(new Circle(10 , Color.YELLOW))
                .type(EntityType.RECEPTIONIST)
                .build();
    }
}*/