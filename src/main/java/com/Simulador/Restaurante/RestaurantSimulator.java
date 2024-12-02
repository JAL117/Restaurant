/*package com.Simulador.Restaurante;

import com.Simulador.Restaurante.business.services.RestauranteService;
import com.Simulador.Restaurante.presentation.views.RestauranteView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RestaurantSimulator extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear instancia de RestauranteService
        int capacidadMesas = 40; // Ajusta según necesidad
        int cantidadMeseros = 4; // Ajusta según necesidad
        int cantidadCocineros = 6; // Ajusta según necesidad

        // Inicializar RestauranteService
        RestauranteService restauranteService = new RestauranteService(capacidadMesas, cantidadMeseros, cantidadCocineros);

        // Obtener la vista desde RestauranteService
        RestauranteView view = restauranteService.getView();

        // Configurar la escena con la vista
        Scene scene = new Scene(view.getRoot());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulador de Restaurante");
        primaryStage.show();

        // Iniciar la simulación
        restauranteService.iniciarSimulacion();
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/

package com.Simulador.Restaurante;

import com.Simulador.Restaurante.business.services.RestauranteService;
import com.Simulador.Restaurante.business.utils.PoissonDistribution;
import com.Simulador.Restaurante.presentation.views.RestauranteView;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.Scene;
import com.Simulador.Restaurante.business.utils.RandomUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class RestaurantSimulator extends GameApplication {

    private RestauranteService restauranteService;
    private RestauranteView restauranteView;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Simulador de Restaurante");
        settings.setVersion("1.0");
        settings.setWidth(1400);
        settings.setHeight(700);
        settings.setMainMenuEnabled(false);
    }






    @Override
    protected void initGame() {
        // Crear instancia del servicio y vista
        int capacidadMesas = 20;
        int cantidadMeseros = (int) Math.round(capacidadMesas * 0.10);
        int cantidadCocineros = (int) Math.round(capacidadMesas * 0.15);

        restauranteService = new RestauranteService(capacidadMesas, cantidadMeseros, cantidadCocineros);
        restauranteView = restauranteService.getView();

        // Cargar la textura del fondo


        Image backgroundImage = new Image(getClass().getResource("/assets/textures/fondo.png").toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(FXGL.getAppWidth());
        backgroundImageView.setFitHeight(FXGL.getAppHeight());

        FXGL.getGameScene().getContentRoot().getChildren().add(0, backgroundImageView);


        // Iniciar simulación y agregar vista
        restauranteService.iniciarSimulacion();
        FXGL.getGameScene().addUINode(restauranteView.getRoot());
    }



    public static void main(String[] args) {
        launch(args);
    }
}






