package com.Simulador.Restaurante.entities;

import com.Simulador.Restaurante.business.models.Mesa;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class createEscene {

    private List<Entity> tables = new ArrayList<>();

    public void createGameEntities() {
        FXGL.entityBuilder()
                .at(0, 0)
                .view(createImageView("/assets/textures/background.png", 1300, 850))
                .buildAndAttach();

        FXGL.entityBuilder()
                .at(210, 70)
                .view(createImageView("/assets/textures/dining_area.jpg", 1200, 500))
                .buildAndAttach();

        FXGL.entityBuilder()
                .at(1100, 120)
                .view(createImageView("/assets/textures/kitchen.jpg", 500, 400))
                .buildAndAttach();

        FXGL.entityBuilder()
                .at(10, 500)
                .view(createImageView("/assets/textures/waiting_area.jpg", 100, 500))
                .buildAndAttach();

        addLabel("Cocina", 1150, 30);
        addLabel("Área de espera", 30, 30);
        addLabel("Área de mesas", 550, 30);

        createTables(4, 5, 350, 110, 50);
    }

    public void createTables(int rows, int cols, double startX, double startY, double spacing) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double posX = startX + j * (70 + spacing);
                double posY = startY + i * (70 + spacing);

                Mesa mesa = new Mesa(i * cols + j + 1);
                mesa.setPosicion(posX, posY);

                // Crear la entidad y asociar el componente TableEntity
                Entity tableEntity = FXGL.entityBuilder()
                        .at(posX, posY)
                        .view(createImageView("/assets/textures/table.jpg", 70, 70))
                        .with(new Table(mesa))
                        .buildAndAttach();

                tables.add(tableEntity);
            }
        }
    }


    public void addLabel(String text, double x, double y) {
        Text label = new Text(text);
        label.setFill(Color.WHITE);
        label.setFont(Font.font(20));
        FXGL.entityBuilder()
                .at(x, y)
                .view(label)
                .buildAndAttach();
    }

    public ImageView createImageView(String path, double width, double height) {
        ImageView imageView = new ImageView(loadImage(path));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        return imageView;
    }

    public Image loadImage(String path) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }


}
