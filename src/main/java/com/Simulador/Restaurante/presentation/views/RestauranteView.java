package com.Simulador.Restaurante.presentation.views;

import com.Simulador.Restaurante.business.models.EstadoMesa;
import com.Simulador.Restaurante.business.models.Mesa;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import com.almasb.fxgl.entity.components.TransformComponent;
import javafx.application.Platform;
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
        // Asegurarse de que las modificaciones de la GUI se ejecuten en el hilo de JavaFX
        Platform.runLater(() -> {
            Entity mesaEntity = mesasVisuales.get(numeroMesa);
            if (mesaEntity != null) {
                Color color = (estado == EstadoMesa.LIBRE) ? Color.GREEN : Color.RED;
                mesaEntity.getViewComponent().clearChildren(); // Limpia los nodos hijos
                mesaEntity.getViewComponent().addChild(new Rectangle(40, 40, color)); // Agrega un nuevo nodo
            }
        });
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
        });
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

    public void moverMesero(int meseroId, double x, double y, Runnable onFinish) {
        Entity mesero = meserosVisuales.get(meseroId);
        if (mesero != null) {
            FXGL.animationBuilder()
                    .interpolator(Interpolators.LINEAR.EASE_IN_OUT())
                    .translate(mesero)
                    .from(mesero.getPosition()) // Posición actual del mesero
                    .to(new Point2D(x, y))     // Posición destino
                    .onFinished(onFinish)      // Acción al terminar
                    .buildSequence()
                    .start();
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

    public void retirarComensal(int comensalId) {
        Entity comensal = comensalesVisuales.get(comensalId);
        if (comensal != null) {
            // Mover al comensal fuera de la pantalla
            moverComensal(comensalId, 1100, 50, () -> {
                System.out.println("Comensal " + comensalId + " se retiró.");
                FXGL.getGameWorld().removeEntity(comensal); // Eliminar el comensal visual
                comensalesVisuales.remove(comensalId);
            });
        }
    }




}
