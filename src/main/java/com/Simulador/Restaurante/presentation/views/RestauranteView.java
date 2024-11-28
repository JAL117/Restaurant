package com.Simulador.Restaurante.presentation.views;

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

