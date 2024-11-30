package com.Simulador.Restaurante.presentation.views;

import com.Simulador.Restaurante.business.models.EstadoMesa;
import com.Simulador.Restaurante.business.models.Mesa;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


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
    private final Point2D reception = new Point2D(50, 100);

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


        Rectangle kitchenArea = new Rectangle(500, 150, Color.YELLOW);
        kitchenArea.setTranslateX(kitchen.getX() - 75);
        kitchenArea.setTranslateY(kitchen.getY() - 75);
        Text kitchenLabel = new Text("Cocina");
        kitchenLabel.setTranslateX(kitchen.getX() - 25);
        kitchenLabel.setTranslateY(kitchen.getY() - 100);

        /*   FXGL.addUINode(kitchenArea);*/
        FXGL.addUINode(kitchenLabel);
    }

    private void inicializarMesas() {
        int columnas = 5;
        double startX = 300;
        double startY = 200;
        double offsetX = 100;
        double offsetY = 100;

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
                .at(reception.add(25,-50))
                .viewWithBBox(new Rectangle(25, 25, Color.PURPLE))
                .buildAndAttach();
        Text label = new Text("Recepcionista");
        label.setTranslateX(reception.getX() - 15);
        label.setTranslateY(reception.getY() - 70);
        FXGL.addUINode(label);
    }

    public void setTotalCocineros(int totalCocineros) {
        this.totalCocineros = totalCocineros;
    }

    public void actualizarEstadoMesa(int numeroMesa, EstadoMesa estado) {
        Platform.runLater(() -> {
            // Obtener la entidad de la mesa
            Entity mesaEntity = mesasVisuales.get(numeroMesa);

            // Verificar que la entidad de la mesa existe
            if (mesaEntity != null) {
                // Definir el color de la mesa dependiendo de su estado
                Color color = (estado == EstadoMesa.LIBRE) ? Color.GREEN : Color.RED;

                // Limpiar los nodos hijos de la mesa (por ejemplo, las visualizaciones anteriores)
                mesaEntity.getViewComponent().clearChildren();

                // Agregar un rectángulo con el color apropiado
                mesaEntity.getViewComponent().addChild(new Rectangle(40, 40, color));

            }
        });
    }

    public void actualizarTotalComensales(int total) {
        totalComensalesText.setText("Total Comensales: " + total);
    }

    public void actualizarOrdenesProcesadas(int total) {
        ordenesProcesadasText.setText("Órdenes Procesadas: " + total);
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

    //MESERO
    public void añadirMesero(int meseroId) {
        runOnce(() -> {
            Entity mesero = entityBuilder()
                    .at(reception.add(20 * meseroId, 0))
                    .view(new Rectangle(10, 10, Color.GREEN))
                    .buildAndAttach();
            meserosVisuales.put(meseroId, mesero);
            return null;
        }, Duration.seconds(0));
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

    public void moverMesero(int meseroId, double x, double y, Runnable onFinish) {
        Entity mesero = meserosVisuales.get(meseroId);
        if (mesero != null) {
            FXGL.animationBuilder()
                    .interpolator(Interpolators.LINEAR.EASE_IN_OUT())
                    .translate(mesero)
                    .from(mesero.getPosition()) // Posición actual del mesero
                    .to(new Point2D(x, y))     // Posición destino// Acción al terminar
                    .buildSequence()
                    .start();
        }
    }

    public void atenderMesa(int meseroId, int numeroMesa) {
        Entity mesero = meserosVisuales.get(meseroId);
        Mesa mesa = mesas.get(numeroMesa - 1);
        Point2D posicionMesa = new Point2D(mesa.getPosX(), mesa.getPosY());

        if (mesero != null) {
            moverMesero(meseroId, posicionMesa.getX(), posicionMesa.getY(), () -> {
                System.out.println("Mesero " + meseroId + " atendió la mesa " + numeroMesa);
                // Llamar a la lógica de creación de una orden
            });
        }
    }

    public void entregarOrden(int meseroId, int numeroMesa) {
        Mesa mesa = mesas.get(numeroMesa - 1);
        Point2D posicionMesa = new Point2D(mesa.getPosX(), mesa.getPosY());
        Point2D cocina = this.kitchen;

        // Mover al mesero a la cocina para recoger la orden
        moverMesero(meseroId, cocina.getX(), cocina.getY(), () -> {
            System.out.println("Mesero " + meseroId + " recogió la orden de la cocina.");

            // Mover al mesero a la mesa para entregar la orden
            moverMesero(meseroId, posicionMesa.getX(), posicionMesa.getY(), () -> {
                System.out.println("Mesero " + meseroId + " entregó la orden en la mesa " + numeroMesa);
            });
        });
    }

    //COMENSAL

    public void añadirComensal(int comensalId) {
        runOnce(() -> {
            Entity comensal = entityBuilder()
                    .at(entrance.add(-50, 0)) // Comenzar fuera de la pantalla
                    .view(new Rectangle(20, 20, Color.BLUE))
                    .buildAndAttach();

            comensalesVisuales.put(comensalId, comensal);

            // Animar la entrada
            var entradaAnimation = animationBuilder()
                    .duration(Duration.seconds(1))
                    .interpolator(Interpolators.SMOOTH.EASE_IN())
                    .translate(comensal)
                    .to(entrance)
                    .build();

            entradaAnimation.setOnFinished(() -> {
                // Mover a recepción después de entrar
                moverComensal(comensalId, reception.getX(), reception.getY(), () -> {
                    System.out.println("Comensal " + comensalId + " llegó a recepción");
                });
            });

            entradaAnimation.start();

            return null;
        }, Duration.ZERO);
    }

    public void moverComensalAMesa(int comensalId, int numeroMesa) {
        Entity comensal = comensalesVisuales.get(comensalId);
        Entity mesa = mesasVisuales.get(numeroMesa);
        if (comensal != null && mesa != null) {
            Point2D target = mesa.getPosition().subtract(21, -10);
            Point2D firstImpact = new Point2D(180,250);
            animationBuilder()
                    .onFinished(() -> {
                        animationBuilder()
                                .duration(Duration.seconds(1))
                                .translate(comensal)
                                .to(target)
                                .buildAndPlay();
                    })
                    .duration(Duration.seconds(1))
                    .translate(comensal)
                    .to(firstImpact)
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

                comensal = FXGL.entityBuilder()
                        .at(entrance.add(-50, 10))
                        .view(new Rectangle(20, 20, Color.BLUE))
                        .buildAndAttach();


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

    public void moverComensal(int comensalId, double x, double y, Runnable onFinish) {
        Entity comensal = comensalesVisuales.get(comensalId);
        if (comensal != null) {
            FXGL.animationBuilder()
                    .duration(Duration.seconds(2))
                    .interpolator(Interpolators.LINEAR.EASE_IN_OUT())
                    .translate(comensal)
                    .from(comensal.getPosition()) // Posición actual del comensal
                    .to(new Point2D(x, y))        // Posición destino
                    .onFinished(onFinish)        // Acción al terminar
                    .buildSequence()                     // Construir la animación
                    .start(); // Iniciar animación con el temporizador
        }
    }

    public void asignarMesa(int comensalId, int numeroMesa) {
        Mesa mesa = mesas.get(numeroMesa - 1); // Obtener mesa por su índice
        Point2D posicionMesa = new Point2D(mesa.getPosX(), mesa.getPosY());

        // Mover el comensal asignado hacia la mesa
        moverComensal(comensalId, posicionMesa.getX(), posicionMesa.getY(), () -> {
            System.out.println("Comensal " + comensalId + " llegó a la mesa " + numeroMesa);

            // Iniciar el temporizador de comida (por ejemplo, 5-10 segundos)
            double tiempoDeComida = 5 + Math.random() * 5; // Aleatorio entre 5 y 10 segundos
            FXGL.runOnce(() -> {
                System.out.println("Comensal " + comensalId + " terminó de comer.");
                retirarComensal(comensalId); // Retirar comensal después de comer
            }, Duration.seconds(tiempoDeComida)); // Temporizador para que se retire
        });
    }






    public void retirarComensal(int comensalId) {
        Entity comensal = comensalesVisuales.get(comensalId);
        if (comensal != null) {
            // Realizar animación hacia fuera de la pantalla (fuera de la vista)
            animationBuilder()
                    .duration(Duration.seconds(2)) // Duración de la animación
                    .interpolator(Interpolators.LINEAR.EASE_IN()) // Tipo de interpolación
                    .translate(comensal)
                    .to(new Point2D(-500, 200)) // Coordenadas fuera de la pantalla (ajusta según sea necesario)
                    .buildAndPlay();

            // Después de la animación, eliminar el comensal de la pantalla
            runOnce(() -> {
                System.out.println("Comensal " + comensalId + " se retiró.");
                FXGL.getGameWorld().removeEntity(comensal); // Eliminar el comensal visual
                comensalesVisuales.remove(comensalId);
                return null;
            }, Duration.seconds(1)); // Asegura que se elimine después de la animación
        }
    }








}
