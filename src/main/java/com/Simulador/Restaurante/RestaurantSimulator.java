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
}
*/



package com.Simulador.Restaurante;
import com.Simulador.Restaurante.components.MainMenu;
import com.Simulador.Restaurante.entities.RestaurantEntityFactory;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.Simulador.Restaurante.entities.createEscene;
import com.almasb.fxgl.entity.EntityFactory;

public class RestaurantSimulator extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1300);
        settings.setHeight(650);
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initGame() {
        createEscene sceneCreator = new createEscene();
        sceneCreator.createGameEntities();


        RestaurantEntityFactory entityFactory = new RestaurantEntityFactory();
        FXGL.getGameWorld().addEntityFactory(entityFactory);



        entityFactory.spawnInitialEntities();


    }















}

