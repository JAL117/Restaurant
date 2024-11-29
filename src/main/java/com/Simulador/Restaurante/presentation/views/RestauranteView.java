/*package com.Simulador.Restaurante.presentation.views;

import com.Simulador.Restaurante.business.models.Mesa;
import com.Simulador.Restaurante.business.models.EstadoMesa;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestauranteView {
    private final Pane root;
    private final Pane restaurantLayout;
    private final Map<Integer, Circle> mesasVisuales;
    private final Map<Integer, Circle> comensalesVisuales;
    private final Map<Integer, Circle> meserosVisuales;
    private final Map<Integer, Circle> cocinerosVisuales;
    private Circle recepcionistaVisual;
    private final HBox statusBar;
    private final Label totalComensalesLabel;
    private final Label ordenesProcesadasLabel;

    private final VBox ordenesBox;
    private final Map<Integer, Label> ordenesEstados;

    private final double entranceX = 50;
    private final double entranceY = 50;

    private final double kitchenX = 850;
    private final double kitchenY = 300;

    private final double receptionX = 150;
    private final double receptionY = 100;

    private int totalCocineros;

    private final List<Mesa> mesas;

    public RestauranteView(List<Mesa> mesas) {
        this.mesas = mesas;

        root = new Pane();
        root.setPrefSize(1000, 600);

        restaurantLayout = new Pane();
        restaurantLayout.setPrefSize(800, 600);

        mesasVisuales = new HashMap<>();
        comensalesVisuales = new HashMap<>();
        meserosVisuales = new HashMap<>();
        cocinerosVisuales = new HashMap<>();
        ordenesEstados = new HashMap<>();

        inicializarAreas();
        inicializarMesas();
        inicializarRecepcionista();

        statusBar = new HBox(20);
        statusBar.setPadding(new Insets(10));
        statusBar.setAlignment(Pos.CENTER_LEFT);
        statusBar.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #CCCCCC;");
        statusBar.setLayoutY(570);
        statusBar.setPrefWidth(1000);

        totalComensalesLabel = new Label("Total Comensales: 0");
        ordenesProcesadasLabel = new Label("Órdenes Procesadas: 0");

        statusBar.getChildren().addAll(totalComensalesLabel, ordenesProcesadasLabel);

        ordenesBox = new VBox(5);
        ordenesBox.setPadding(new Insets(10));
        ordenesBox.setLayoutX(810);
        ordenesBox.setLayoutY(50);
        Label ordenesLabel = new Label("Órdenes");
        ordenesLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        ordenesBox.getChildren().add(ordenesLabel);

        root.getChildren().addAll(restaurantLayout, ordenesBox, statusBar);
    }

    private void inicializarAreas() {
        double areaWidth = 150;
        double areaHeight = 150;

        Rectangle entrance = new Rectangle(areaWidth, areaHeight, Color.LIGHTBLUE);
        entrance.setStroke(Color.BLACK);
        entrance.setLayoutX(entranceX - areaWidth / 2);
        entrance.setLayoutY(entranceY - areaHeight / 2);
        Label entranceLabel = new Label("Entrada");
        entranceLabel.setLayoutX(entranceX - 30);
        entranceLabel.setLayoutY(entranceY - areaHeight / 2 - 20);

        Rectangle reception = new Rectangle(areaWidth, areaHeight, Color.BEIGE);
        reception.setStroke(Color.BLACK);
        reception.setLayoutX(receptionX - areaWidth / 2);
        reception.setLayoutY(receptionY - areaHeight / 2);
        Label receptionLabel = new Label("Recepción");
        receptionLabel.setLayoutX(receptionX - 30);
        receptionLabel.setLayoutY(receptionY - areaHeight / 2 - 20);

        Rectangle kitchen = new Rectangle(areaWidth, areaHeight, Color.LIGHTGRAY);
        kitchen.setStroke(Color.BLACK);
        kitchen.setLayoutX(kitchenX - areaWidth / 2);
        kitchen.setLayoutY(kitchenY - areaHeight / 2);
        Label kitchenLabel = new Label("Cocina");
        kitchenLabel.setLayoutX(kitchenX - 25);
        kitchenLabel.setLayoutY(kitchenY - areaHeight / 2 - 20);

        restaurantLayout.getChildren().addAll(entrance, entranceLabel, reception, receptionLabel, kitchen, kitchenLabel);
    }

    private void inicializarMesas() {
        int totalMesas = mesas.size();
        int filas = 5;
        int columnas = 8;
        double startX = 200;
        double startY = 200;
        double offsetX = 70;
        double offsetY = 80;

        for (Mesa mesa : mesas) {
            int numeroMesa = mesa.getNumero();
            int index = numeroMesa - 1;

            int fila = index / columnas;
            int columna = index % columnas;

            double x = startX + columna * offsetX;
            double y = startY + fila * offsetY;

            mesa.setPosicion(x, y);

            Circle mesaVisual = new Circle(20, Color.GREEN);
            mesaVisual.setStroke(Color.BLACK);
            mesaVisual.setLayoutX(x);
            mesaVisual.setLayoutY(y);
            Label label = new Label(String.valueOf(numeroMesa));
            label.setLayoutX(x - 5);
            label.setLayoutY(y - 5);

            restaurantLayout.getChildren().addAll(mesaVisual, label);
            mesasVisuales.put(numeroMesa, mesaVisual);
        }
    }

    private void inicializarRecepcionista() {
        Platform.runLater(() -> {
            recepcionistaVisual = new Circle(15, Color.PURPLE);
            recepcionistaVisual.setLayoutX(receptionX);
            recepcionistaVisual.setLayoutY(receptionY);
            restaurantLayout.getChildren().add(recepcionistaVisual);
            Label recepcionistaLabel = new Label("Recepcionista");
            recepcionistaLabel.setLayoutX(receptionX - 40);
            recepcionistaLabel.setLayoutY(receptionY - 30);
            restaurantLayout.getChildren().add(recepcionistaLabel);
        });
    }

    public void setTotalCocineros(int totalCocineros) {
        this.totalCocineros = totalCocineros;
    }

    public void añadirCocinero(int cocineroId) {
        Platform.runLater(() -> {
            Circle cocineroCircle = new Circle(10, Color.RED);

            int index = cocineroId - 1;
            int nCols = 2;
            int nRows = (int) Math.ceil(totalCocineros / (double) nCols);

            int row = index / nCols;
            int col = index % nCols;

            double margin = 20;
            double areaWidth = 150;
            double areaHeight = 150;

            double xStep = nCols > 1 ? (areaWidth - 2 * margin) / (nCols - 1) : 0;
            double yStep = nRows > 1 ? (areaHeight - 2 * margin) / (nRows - 1) : 0;

            double x = kitchenX - areaWidth / 2 + margin + col * xStep;
            double y = kitchenY - areaHeight / 2 + margin + row * yStep;

            cocineroCircle.setLayoutX(x);
            cocineroCircle.setLayoutY(y);

            restaurantLayout.getChildren().add(cocineroCircle);
            cocinerosVisuales.put(cocineroId, cocineroCircle);
        });
    }

    public void añadirMesero(int meseroId) {
        Platform.runLater(() -> {
            Circle meseroCircle = new Circle(10, Color.GREEN);
            meseroCircle.setLayoutX(receptionX + 50);
            meseroCircle.setLayoutY(receptionY + meseroId * 15);
            restaurantLayout.getChildren().add(meseroCircle);
            meserosVisuales.put(meseroId, meseroCircle);
        });
    }

    public void moverMesero(int meseroId, double x, double y) {
        Platform.runLater(() -> {
            Circle meseroCircle = meserosVisuales.get(meseroId);
            if (meseroCircle != null) {
                Path path = new Path();
                path.getElements().add(new MoveTo(meseroCircle.getLayoutX(), meseroCircle.getLayoutY()));
                path.getElements().add(new LineTo(x, y));

                PathTransition transition = new PathTransition(Duration.seconds(2), path, meseroCircle);
                transition.play();
            }
        });
    }

    public Pane getRoot() {
        return root;
    }

    public void actualizarEstadoMesa(int numeroMesa, EstadoMesa estado) {
        Platform.runLater(() -> {
            Circle mesa = mesasVisuales.get(numeroMesa);
            if (mesa != null) {
                if (estado == EstadoMesa.LIBRE) {
                    mesa.setFill(Color.GREEN);
                } else {
                    mesa.setFill(Color.RED);
                }
            }
        });
    }

    public void actualizarComensalEstado(int comensalId, String estado) {
        Platform.runLater(() -> {
        });
    }

    public void moverComensalAMesa(int comensalId, int numeroMesa) {
        Platform.runLater(() -> {
            Circle comensalCircle = comensalesVisuales.get(comensalId);
            Mesa mesa = mesas.stream().filter(m -> m.getNumero() == numeroMesa).findFirst().orElse(null);
            if (comensalCircle != null && mesa != null) {
                double x = mesa.getPosX();
                double y = mesa.getPosY() - 15;

                Path path = new Path();
                path.getElements().add(new MoveTo(comensalCircle.getLayoutX(), comensalCircle.getLayoutY()));
                path.getElements().add(new LineTo(x, y));

                PathTransition transition = new PathTransition(Duration.seconds(2), path, comensalCircle);
                transition.play();
            }
        });
    }

    public void moverComensalAEntrada(int comensalId) {
        Platform.runLater(() -> {
            Circle comensalCircle = new Circle(10, Color.BLUE);
            comensalCircle.setLayoutX(entranceX - 70);
            comensalCircle.setLayoutY(entranceY);

            restaurantLayout.getChildren().add(comensalCircle);
            comensalesVisuales.put(comensalId, comensalCircle);
        });
    }

    public void moverComensalARecepcion(int comensalId) {
        Platform.runLater(() -> {
            Circle comensalCircle = comensalesVisuales.get(comensalId);
            if (comensalCircle != null) {
                Path path = new Path();
                path.getElements().add(new MoveTo(comensalCircle.getLayoutX(), comensalCircle.getLayoutY()));
                path.getElements().add(new LineTo(receptionX, receptionY));

                PathTransition transition = new PathTransition(Duration.seconds(1), path, comensalCircle);
                transition.play();
            }
        });
    }

    public void moverComensalFuera(int comensalId) {
        Platform.runLater(() -> {
            Circle comensalCircle = comensalesVisuales.get(comensalId);
            if (comensalCircle != null) {
                double exitX = entranceX - 100;
                double exitY = entranceY;

                Path path = new Path();
                path.getElements().add(new MoveTo(comensalCircle.getLayoutX(), comensalCircle.getLayoutY()));
                path.getElements().add(new LineTo(exitX, exitY));

                PathTransition transition = new PathTransition(Duration.seconds(2), path, comensalCircle);
                transition.setOnFinished(event -> {
                    restaurantLayout.getChildren().remove(comensalCircle);
                    comensalesVisuales.remove(comensalId);
                });
                transition.play();
            }
        });
    }

    public void actualizarComidaLista(int ordenId) {
        Platform.runLater(() -> {
            Label label;
            if (ordenesEstados.containsKey(ordenId)) {
                label = ordenesEstados.get(ordenId);
                label.setText("Orden " + ordenId + ": Lista");
            } else {
                label = new Label("Orden " + ordenId + ": Lista");
                ordenesBox.getChildren().add(label);
                ordenesEstados.put(ordenId, label);
            }
        });
    }

    public void actualizarComidaServida(int ordenId) {
        Platform.runLater(() -> {
            Label label = ordenesEstados.get(ordenId);
            if (label != null) {
                label.setText("Orden " + ordenId + ": Servida");
            }
        });
    }

    public void actualizarTotalComensales(int total) {
        Platform.runLater(() -> totalComensalesLabel.setText("Total Comensales: " + total));
    }

    public void actualizarOrdenesProcesadas(int total) {
        Platform.runLater(() -> ordenesProcesadasLabel.setText("Órdenes Procesadas: " + total));
    }

    public double getKitchenX() {
        return kitchenX;
    }

    public double getKitchenY() {
        return kitchenY;
    }

    public double getReceptionX() {
        return receptionX;
    }

    public double getReceptionY() {
        return receptionY;
    }
}

*/


package com.Simulador.Restaurante.presentation.views;

import com.Simulador.Restaurante.business.models.EstadoMesa;
import com.Simulador.Restaurante.business.models.Mesa;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import com.almasb.fxgl.entity.components.TransformComponent;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.sun.javafx.application.PlatformImpl.runLater;

public class RestauranteView extends FXGLScene {

    private final List<Mesa> mesas;
    private final Map<Integer, Entity> mesasVisuales = new HashMap<>();
    private final Map<Integer, Entity> comensalesVisuales = new HashMap<>();
    private final Map<Integer, Entity> meserosVisuales = new HashMap<>();
    private final Map<Integer, Entity> cocinerosVisuales = new HashMap<>();
    private Entity recepcionistaVisual;

    private final VBox statusBox;
    private final Text totalComensalesText;
    private final Text ordenesProcesadasText;

    private final Point2D entrance = new Point2D(50, 50);
    private final Point2D kitchen = new Point2D(850, 300);
    private final Point2D reception = new Point2D(150, 100);

    private int totalCocineros;

    public RestauranteView(List<Mesa> mesas) {
        super(1000, 600);
        this.mesas = mesas;

        // Inicializar áreas principales
        inicializarAreas();

        // Inicializar mesas visuales
        inicializarMesas();

        // Inicializar recepcionista visual
        inicializarRecepcionista();

        // Crear barra de estado
        statusBox = new VBox(10);
        statusBox.setTranslateX(800);
        statusBox.setTranslateY(10);
        totalComensalesText = new Text("Total Comensales: 0");
        ordenesProcesadasText = new Text("Órdenes Procesadas: 0");
        statusBox.getChildren().addAll(totalComensalesText, ordenesProcesadasText);

        // Agregar la barra de estado a la escena
        FXGL.addUINode(statusBox);
    }

    private void inicializarAreas() {
        // Zona de entrada
        Rectangle entranceArea = new Rectangle(150, 150, Color.LIGHTBLUE);
        entranceArea.setTranslateX(entrance.getX() - 75);
        entranceArea.setTranslateY(entrance.getY() - 75);
        Text entranceLabel = new Text("Entrada");
        entranceLabel.setTranslateX(entrance.getX() - 25);
        entranceLabel.setTranslateY(entrance.getY() - 100);

        // Zona de recepción
        Rectangle receptionArea = new Rectangle(150, 150, Color.BEIGE);
        receptionArea.setTranslateX(reception.getX() - 75);
        receptionArea.setTranslateY(reception.getY() - 75);
        Text receptionLabel = new Text("Recepción");
        receptionLabel.setTranslateX(reception.getX() - 25);
        receptionLabel.setTranslateY(reception.getY() - 100);

        // Zona de cocina
        Rectangle kitchenArea = new Rectangle(150, 150, Color.LIGHTGRAY);
        kitchenArea.setTranslateX(kitchen.getX() - 75);
        kitchenArea.setTranslateY(kitchen.getY() - 75);
        Text kitchenLabel = new Text("Cocina");
        kitchenLabel.setTranslateX(kitchen.getX() - 25);
        kitchenLabel.setTranslateY(kitchen.getY() - 100);

        // Agregar a la escena
        FXGL.addUINode(entranceArea);
        FXGL.addUINode(entranceLabel);
        FXGL.addUINode(receptionArea);
        FXGL.addUINode(receptionLabel);
        FXGL.addUINode(kitchenArea);
        FXGL.addUINode(kitchenLabel);
    }

    private void inicializarMesas() {
        int columnas = 8;
        double startX = 200;
        double startY = 200;
        double offsetX = 70;
        double offsetY = 80;

        for (Mesa mesa : mesas) {
            int numeroMesa = mesa.getNumero();
            int index = numeroMesa - 1;

            int fila = index / columnas;
            int columna = index % columnas;

            double x = startX + columna * offsetX;
            double y = startY + fila * offsetY;

            mesa.setPosicion(x, y);

            Entity mesaEntity = FXGL.entityBuilder()
                    .at(x, y)
                    .viewWithBBox(new Rectangle(40, 40, Color.GREEN))
                    .buildAndAttach();

            Text label = new Text(String.valueOf(numeroMesa));
            label.setTranslateX(x - 10);
            label.setTranslateY(y - 10);
            FXGL.addUINode(label);

            mesasVisuales.put(numeroMesa, mesaEntity);
        }
    }

    private void inicializarRecepcionista() {
        recepcionistaVisual = FXGL.entityBuilder()
                .at(reception)
                .viewWithBBox(new Rectangle(30, 30, Color.PURPLE))
                .buildAndAttach();
        Text label = new Text("Recepcionista");
        label.setTranslateX(reception.getX() - 40);
        label.setTranslateY(reception.getY() - 50);
        FXGL.addUINode(label);
    }

    public void setTotalCocineros(int totalCocineros) {
        this.totalCocineros = totalCocineros;
    }

    public void actualizarEstadoMesa(int numeroMesa, EstadoMesa estado) {
        Entity mesaEntity = mesasVisuales.get(numeroMesa);
        if (mesaEntity != null) {
            Color color = estado == EstadoMesa.LIBRE ? Color.GREEN : Color.RED;
            mesaEntity.getViewComponent().clearChildren();
            mesaEntity.getViewComponent().addChild(new Rectangle(40, 40, color));
        }
    }

    public void actualizarTotalComensales(int total) {
        totalComensalesText.setText("Total Comensales: " + total);
    }

    public void actualizarOrdenesProcesadas(int total) {
        ordenesProcesadasText.setText("Órdenes Procesadas: " + total);
    }

    public void añadirMesero(int meseroId) {
        runOnce(() -> {
            Entity mesero = entityBuilder()
                    .at(reception.add(20 * meseroId, 0)) // Posicionar meseros en fila en la recepción
                    .view(new Rectangle(10, 10, Color.GREEN))
                    .buildAndAttach();
            meserosVisuales.put(meseroId, mesero);
            return null;
        }, Duration.seconds(0)); // Ejecutar inmediatamente
    }

    public void moverMesero(int meseroId, double x, double y) {
        Entity mesero = meserosVisuales.get(meseroId);
        if (mesero != null) {
            animationBuilder()
                    .duration(Duration.seconds(2))
                    .interpolator(Interpolators.LINEAR.EASE_IN_OUT())
                    .translate(mesero)
                    .to(new Point2D(x, y))
                    .buildAndPlay();
        }
    }


    public void añadirCocinero(int cocineroId) {
        runOnce(() -> {
            Entity cocinero = entityBuilder()
                    .at(kitchen.add(30 * (cocineroId - 1), 0)) // Posicionar cocineros en fila en la cocina
                    .view(new Rectangle(10, 10, Color.RED))
                    .buildAndAttach();
            cocinerosVisuales.put(cocineroId, cocinero);
            return null;
        }, Duration.seconds(0)); // Ejecutar inmediatamente
    }

    public void moverComensalAMesa(int comensalId, int numeroMesa) {
        Entity comensal = comensalesVisuales.get(comensalId);
        Entity mesa = mesasVisuales.get(numeroMesa);
        if (comensal != null && mesa != null) {
            Point2D target = mesa.getPosition().subtract(0, 15); // Ajustar posición sobre la mesa
            animationBuilder()
                    .duration(Duration.seconds(2))
                    .translate(comensal)
                    .to(target)
                    .buildAndPlay();
        }
    }

    public void moverComensalARecepcion(int comensalId) {
        Entity comensal = comensalesVisuales.get(comensalId);
        if (comensal != null) {
            animationBuilder()
                    .duration(Duration.seconds(1))
                    .translate(comensal)
                    .to(reception)
                    .buildAndPlay();
        }
    }

    public void moverComensalFuera(int comensalId) {
        Entity comensal = comensalesVisuales.get(comensalId);
        if (comensal != null) {
            Point2D exitPoint = entrance.subtract(100, 0);
            var animation = animationBuilder()
                    .duration(Duration.seconds(2))
                    .translate(comensal)
                    .to(exitPoint)
                    .build(); // Construimos la animación sin reproducir aún

            // Configuramos lo que ocurre cuando la animación finaliza
            animation.setOnFinished(() -> {
                getGameWorld().removeEntity(comensal); // Eliminar de la escena
                comensalesVisuales.remove(comensalId);
            });

            // Reproducimos la animación
            double v = 3;
            animation.onUpdate(v);
        }
    }

    public void actualizarComidaLista(int ordenId) {
        runLater(() -> {
            Entity ordenVisual = getOrdenVisual(ordenId);
            if (ordenVisual != null) {
                ordenVisual.getViewComponent().clearChildren();
                Rectangle rect = new Rectangle(40, 20, Color.ORANGE); // Visualización de comida lista
                ordenVisual.getViewComponent().addChild(rect);
                Text label = new Text("Lista");
                label.setFill(Color.WHITE);
                ordenVisual.getViewComponent().addChild(label);
            }
        });
    }

    public void actualizarComidaServida(int ordenId) {
        runLater(() -> {
            Entity ordenVisual = getOrdenVisual(ordenId);
            if (ordenVisual != null) {
                ordenVisual.getViewComponent().clearChildren();
                Rectangle rect = new Rectangle(40, 20, Color.GREEN); // Visualización de comida servida
                ordenVisual.getViewComponent().addChild(rect);
                Text label = new Text("Servida");
                label.setFill(Color.WHITE);
                ordenVisual.getViewComponent().addChild(label);
            }
        });
    }

    private Entity getOrdenVisual(int ordenId) {
        // Obtiene la entidad visual de la orden. Puedes personalizar cómo se gestiona.
        // Aquí asumimos que las órdenes están mapeadas en un mapa similar al ejemplo anterior.
        return cocinerosVisuales.get(ordenId); // Modificar según estructura real
    }

    public double getReceptionX() {
        return reception.getX();
    }

    public double getReceptionY() {
        return reception.getY();
    }
    public void moverComensalAEntrada(int comensalId) {
        runOnce(() -> {
            // Verificar si el comensal ya existe
            Entity comensal = comensalesVisuales.get(comensalId);
            if (comensal == null) {
                // Crear un nuevo comensal si no existe
                comensal = FXGL.entityBuilder()
                        .at(entrance.add(-50, 0)) // Posicionar cerca de la entrada
                        .view(new Rectangle(20, 20, Color.BLUE))
                        .buildAndAttach();

                // Registrar el comensal en el mapa
                comensalesVisuales.put(comensalId, comensal);
            }

            // Realizar animación hacia la entrada
            animationBuilder()
                    .duration(Duration.seconds(1))
                    .interpolator(Interpolators.LINEAR.EASE_IN())
                    .translate(comensal)
                    .to(entrance) // Mover hacia la posición de entrada
                    .buildAndPlay();
            return null;
        }, Duration.ZERO); // Ejecutar inmediatamente
    }



}
