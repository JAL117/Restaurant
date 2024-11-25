package com.Simulador.Restaurante;

import com.Simulador.Restaurante.components.MainMenu;
import com.Simulador.Restaurante.entities.RestaurantEntityFactory;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RestaurantSimulator extends GameApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1000);
        settings.setHeight(700);
        settings.setTitle("Simulador Restaurante");
        settings.setVersion("0.1");
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });
    }

    @Override
    protected void initGame() {
        createGameEntities();
        FXGL.getGameWorld().addEntityFactory(new RestaurantEntityFactory());
        spawnInitialEntities();
    }

    private void createGameEntities() {
        Entity background = FXGL.entityBuilder()
                .at(0, 0)
                .view(new Rectangle(1000, 700, Color.LIGHTGRAY))
                .buildAndAttach();

        Entity diningArea = FXGL.entityBuilder()
                .at(200, 150)
                .view(new Rectangle(600, 400, Color.WHITE))
                .buildAndAttach();

        Entity kitchen = FXGL.entityBuilder()
                .at(850, 150)
                .view(new Rectangle(120, 400, Color.LIGHTYELLOW))
                .buildAndAttach();

        Entity waitingArea = FXGL.entityBuilder()
                .at(30, 150)
                .view(new Rectangle(120, 400, Color.LIGHTBLUE))
                .buildAndAttach();

        addLabel("Cocina", 880, 120);
        addLabel("Área de espera", 40, 120);
        addLabel("Área de mesas", 450, 120);

        createTables();
    }

    private void createTables() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                FXGL.entityBuilder()
                        .at(300 + j * 200, 200 + i * 200)
                        .view(new Rectangle(80, 80, Color.BROWN))
                        .buildAndAttach();
            }
        }
    }

    private void addLabel(String text, double x, double y) {
        Text label = new Text(text);
        label.setFill(Color.BLACK);
        label.setFont(Font.font(20));
        FXGL.entityBuilder()
                .at(x, y)
                .view(label)
                .buildAndAttach();
    }

    private void spawnInitialEntities() {
        for (int i = 0; i < 3; i++) {
            FXGL.spawn("waiter", 100 + i * 50, 300);
        }
        for (int i = 0; i < 2; i++) {
            FXGL.spawn("cook", 880, 200 + i * 100);
        }
    }
}