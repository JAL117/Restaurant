package com.Simulador.Restaurante.components;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;

import java.util.Objects;

public class MainMenu extends FXGLMenu {
    public MainMenu() {
        super(MenuType.MAIN_MENU);

        ImageView backgroundImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/textures/restaurante.png"))));
        backgroundImage.setFitWidth(getAppWidth());
        backgroundImage.setFitHeight(getAppHeight());
        backgroundImage.setPreserveRatio(false);

        GaussianBlur blurEffect = new GaussianBlur();
        blurEffect.setRadius(10);
        backgroundImage.setEffect(blurEffect);

        VBox mainContainer = new VBox(50);
        mainContainer.setAlignment(Pos.CENTER);
        Text title = new Text("Simulador de Restaurante");
        title.setFont(Font.font(80));
        title.setFill(Color.WHITE);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.BLACK);
        title.setEffect(dropShadow);

        Button btnStart = createStyledButton("Iniciar Simulación");
        Button btnExit = createStyledButton("Salir");
        btnStart.setOnAction(e -> fireNewGame());
        btnExit.setOnAction(e -> fireExit());

        mainContainer.getChildren().addAll(title, btnStart, btnExit);
        StackPane root = new StackPane(backgroundImage, mainContainer);
        root.setPrefSize(getAppWidth(), getAppHeight());
        getContentRoot().getChildren().add(root);
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);


        button.setStyle("-fx-background-color: linear-gradient(#2196f3, #1976d2); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 20px; " +
                "-fx-padding: 10 20; " +
                "-fx-min-width: 200px; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 0, 1);");


        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: linear-gradient(#1976d2, #1565c0); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 20px; " +
                "-fx-padding: 10 20; " +
                "-fx-min-width: 200px; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 5, 0, 0, 1);"));


        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: linear-gradient(#2196f3, #1976d2); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 20px; " +
                "-fx-padding: 10 20; " +
                "-fx-min-width: 200px; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 0, 1);"));

        return button;
    }
}