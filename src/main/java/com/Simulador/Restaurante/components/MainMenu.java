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

import java.util.Objects;

public class MainMenu extends FXGLMenu {
    public MainMenu() {
        super(MenuType.MAIN_MENU);

        ImageView backgroundImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/textures/simulador de restaurante.png"))));

        backgroundImage.setFitWidth(getAppWidth());
        backgroundImage.setFitHeight(getAppHeight());
        backgroundImage.setPreserveRatio(false);

        VBox mainContainer = new VBox(50);
        mainContainer.setAlignment(Pos.CENTER);
        Text title = new Text("Simulador de Restaurante");
        title.setFont(Font.font(48));
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
        button.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10 20; -fx-min-width: 200px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #1976d2; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10 20; -fx-min-width: 200px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10 20; -fx-min-width: 200px;"));
        return button;
    }
}