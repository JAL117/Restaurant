package com.Simulador.Restaurante;
import com.Simulador.Restaurante.business.services.RestauranteService;
import com.Simulador.Restaurante.presentation.views.RestauranteView;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class RestaurantSimulator extends GameApplication {

    private RestauranteService restauranteService;
    private RestauranteView restauranteView;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Simulador de Restaurante");
        settings.setVersion("1.0");
        settings.setWidth(1100);
        settings.setHeight(700);
        settings.setMainMenuEnabled(false);
    }


    @Override
    protected void initGame() {

        int capacidadMesas = 20;
        int cantidadMeseros = (int) Math.round(capacidadMesas * 0.10);
        int cantidadCocineros = (int) Math.round(capacidadMesas * 0.15);

        restauranteService = new RestauranteService(capacidadMesas, cantidadMeseros, cantidadCocineros);
        restauranteView = restauranteService.getView();


        Image backgroundImage = new Image(getClass().getResource("/assets/textures/fondo.png").toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(FXGL.getAppWidth());
        backgroundImageView.setFitHeight(FXGL.getAppHeight());

        FXGL.getGameScene().getContentRoot().getChildren().add(0, backgroundImageView);


        restauranteService.iniciarSimulacion();
        FXGL.getGameScene().addUINode(restauranteView.getRoot());
    }



    public static void main(String[] args) {
        launch(args);
    }
}






