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

import com.Simulador.Restaurante.entities.EntityType;
import com.Simulador.Restaurante.components.MainMenu;
import com.Simulador.Restaurante.entities.RestaurantEntityFactory;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.Simulador.Restaurante.entities.createEscene;

public class RestaurantSimulator extends GameApplication {

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initGame() {
        createEscene sceneCreator = new createEscene();
        sceneCreator.createGameEntities();
        FXGL.getGameWorld().addEntityFactory(new RestaurantEntityFactory());
        spawnInitialEntities();
    }



    private void spawnWaiters(int count) {
        for (int i = 0; i < count; i++) {
            FXGL.spawn(EntityType.WAITER.name().toLowerCase(), 500 + i * 50, 300);
        }
    }

    private void spawnCooks(int count) {
        for (int i = 0; i < count; i++) {
            FXGL.spawn(EntityType.COOK.name().toLowerCase(), 1220, 250 + i * 100);
        }
    }

    private void spawnCustomers(int count) {
        for (int i = 0; i < count; i++) {
            FXGL.spawn(EntityType.CUSTOMER.name().toLowerCase(), 100, 200 + i *  50);
        }
    }

    private void spawnReceptionist() {
        FXGL.spawn(EntityType.RECEPTIONIST.name().toLowerCase(), 150, 200);
    }



    private void spawnInitialEntities() {
        spawnWaiters(3);
        spawnCooks(2);
        spawnCustomers(2);
        spawnReceptionist();
    }








}

