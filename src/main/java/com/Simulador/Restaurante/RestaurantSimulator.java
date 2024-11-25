package com.Simulador.Restaurante;

import com.Simulador.Restaurante.components.MainMenu;
import com.Simulador.Restaurante.entities.RestaurantEntityFactory;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

public class RestaurantSimulator extends GameApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1300);
        settings.setHeight(600);
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
                .view(createImageView("/assets/textures/background.png", 1300, 850))
                .buildAndAttach();

        Entity diningArea = FXGL.entityBuilder()
                .at(210, 70)
                .view(createImageView("/assets/textures/dining_area.jpg", 1200, 500))
                .buildAndAttach();


        Entity kitchen = FXGL.entityBuilder()
                .at(1100, 120)
                .view(createImageView("/assets/textures/kitchen.jpg", 500, 400))
                .buildAndAttach();


        Entity waitingArea = FXGL.entityBuilder()
                .at(30, 150)
                .view(createImageView("/assets/textures/waiting_area.jpg", 100, 500))
                .buildAndAttach();

        addLabel("Cocina", 1150, 30);
        addLabel("Área de espera", 30, 30);
        addLabel("Área de mesas", 550, 30);

        createTables();
    }

    private void createTables() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                FXGL.entityBuilder()
                        .at(300 + j * 150, 110 + i * 120)
                        .view(createImageView("/assets/textures/table.jpg", 70, 70))
                        .buildAndAttach();
            }
        }
    }

    private void addLabel(String text, double x, double y) {
        Text label = new Text(text);
        label.setFill(Color.WHITE);
        label.setFont(Font.font(20));
        FXGL.entityBuilder()
                .at(x, y)
                .view(label)
                .buildAndAttach();
    }

    private void spawnInitialEntities() {
        for (int i = 0; i < 3; i++) {
            FXGL.spawn("waiter", 500 + i * 50, 300);
        }
        for (int i = 0; i < 2; i++) {
            FXGL.spawn("cook", 1220, 250 + i * 100);
        }
        for (int i = 0; i < 2; i++) {
            FXGL.spawn("customer", 100, 200 + i * 50);
        }
        for (int i = 0; i < 1; i++) {
            FXGL.spawn("receptionist", 150, 200 + i * 50);
        }
    }

    private Image loadImage(String path) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }

    private ImageView createImageView(String path, double width, double height) {
        ImageView imageView = new ImageView(loadImage(path));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView .setPreserveRatio(true);
        imageView.setSmooth(true);
        return imageView;
    }
}