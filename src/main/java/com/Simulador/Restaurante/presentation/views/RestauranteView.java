package com.Simulador.Restaurante.presentation.views;

import com.Simulador.Restaurante.business.models.EstadoMesa;
import com.Simulador.Restaurante.business.models.Mesa;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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


    private final Point2D entrance = new Point2D(50, 50);
    private final Point2D kitchen = new Point2D(750, 300);
    private final Point2D reception = new Point2D(50, 100);

    private int totalCocineros;

    public RestauranteView(List<Mesa> mesas) {
        super(1000, 600);
        this.mesas = mesas;

        inicializarAreas();

        inicializarMesas();

        inicializarRecepcionista();

    }

    private void inicializarAreas() {



        // Fondo de la cocina
        Image cocinaImage = new Image(getClass().getResource("/assets/textures/cocina.png").toExternalForm());
        ImageView cocinaImageView = new ImageView(cocinaImage);
        cocinaImageView.setFitWidth(250); // Ajusta según el tamaño
        cocinaImageView.setFitHeight(500); // Ajusta según el tamaño
        cocinaImageView.setTranslateX(kitchen.getX() + 100);
        cocinaImageView.setTranslateY(kitchen.getY() - 250);

        // Asegúrate de agregar el fondo de la cocina detrás de las entidades
        FXGL.getGameScene().getContentRoot().getChildren().add(0, cocinaImageView);

        // Fondo de la recepción
        Image recepcionImage = new Image(getClass().getResource("/assets/textures/recepcion.png").toExternalForm());
        ImageView recepcionImageView = new ImageView(recepcionImage);
        recepcionImageView.setFitWidth(120); // Ajusta según el tamaño
        recepcionImageView.setFitHeight(200); // Ajusta según el tamaño
        recepcionImageView.setTranslateX(reception.getX() - 40);
        recepcionImageView.setTranslateY(reception.getY() - 50);

        // Asegúrate de agregar el fondo de la recepción detrás de las entidades
        FXGL.getGameScene().getContentRoot().getChildren().add(0, recepcionImageView);




    }



    public void añadirCocinero(int cocineroId) {
        runOnce(() -> {
            // Ajustar las coordenadas para distribución vertical
            double xPos = kitchen.getX() + 200;  // Mantén la misma posición en el eje X (ajústalo según sea necesario)
            double yPos = kitchen.getY() - 80 * (cocineroId - 1);  // Desplazamiento vertical

            // Cargar la imagen del cocinero
            Image imagenCocinero = new Image(getClass().getResource("/assets/textures/cocinero1.png").toExternalForm());
            ImageView imagenViewCocinero = new ImageView(imagenCocinero);
            imagenViewCocinero.setFitWidth(80); // Ajusta el tamaño de la imagen
            imagenViewCocinero.setFitHeight(80); // Ajusta el tamaño de la imagen

            // Creación del cocinero
            Entity cocinero = entityBuilder()
                    .at(xPos, yPos)  // Usa las coordenadas ajustadas
                    .view(imagenViewCocinero) // Usa la imagen en lugar del rectángulo
                    .buildAndAttach();

            // Guardar al cocinero
            cocinerosVisuales.put(cocineroId, cocinero);
            return null;
        }, Duration.seconds(0));
    }




    private void inicializarMesas() {
        int columnas = 5;
        double startX = 200;
        double startY = 200;
        double offsetX = 130;
        double offsetY = 100;

        for (Mesa mesa : mesas) {
            int numeroMesa = mesa.getNumero();
            int index = numeroMesa - 1;

            int fila = index / columnas;
            int columna = index % columnas;

            double x = startX + columna * offsetX;
            double y = startY + fila * offsetY;

            mesa.setPosicion(x, y);

            // Cargar la imagen de la mesa (por defecto estará libre)
            Image mesaLibreImage = new Image(getClass().getResource("/assets/textures/mesa.png").toExternalForm());
            ImageView mesaLibreView = new ImageView(mesaLibreImage);
            mesaLibreView.setFitWidth(60);  // Ajusta el tamaño de la imagen
            mesaLibreView.setFitHeight(60); // Ajusta el tamaño de la imagen

            // Crear la entidad para la mesa
            Entity mesaEntity = FXGL.entityBuilder()
                    .at(x, y)
                    .view(mesaLibreView) // Usar la imagen por defecto
                    .buildAndAttach();

            // Agregar el número de la mesa
            Text label = new Text(String.valueOf(numeroMesa));
            label.setTranslateX(x - 10);
            label.setTranslateY(y - 10);
            FXGL.addUINode(label);

            mesasVisuales.put(numeroMesa, mesaEntity);
        }
    }

    public void actualizarEstadoMesa(int numeroMesa, EstadoMesa estado) {
        Platform.runLater(() -> {
            Entity mesaEntity = mesasVisuales.get(numeroMesa);

            if (mesaEntity != null) {
                Image mesaImage;

                // Dependiendo del estado, cambiar la imagen
                if (estado == EstadoMesa.LIBRE) {
                    mesaImage = new Image(getClass().getResource("/assets/textures/mesa.png").toExternalForm());
                } else {
                    mesaImage = new Image(getClass().getResource("/assets/textures/mesa_ocupada.png").toExternalForm());
                }

                // Cambiar la imagen de la mesa
                mesaEntity.getViewComponent().clearChildren();
                ImageView mesaView = new ImageView(mesaImage);
                mesaView.setFitWidth(60);  // Ajustar al tamaño deseado
                mesaView.setFitHeight(60); // Ajustar al tamaño deseado
                mesaEntity.getViewComponent().addChild(mesaView);
            }
        });
    }



    private void inicializarRecepcionista() {
        // Cargar la imagen de la recepcionista
        Image imagenRecepcionista = new Image(getClass().getResource("/assets/textures/recepcionista.png").toExternalForm());
        ImageView imagenViewRecepcionista = new ImageView(imagenRecepcionista);
        imagenViewRecepcionista.setFitWidth(60); // Ajusta el tamaño de la imagen
        imagenViewRecepcionista.setFitHeight(60); // Ajusta el tamaño de la imagen

        // Crear la entidad de la recepcionista
        recepcionistaVisual = FXGL.entityBuilder()
                .at(reception.add(25, -50)) // Posición de la recepcionista
                .view(imagenViewRecepcionista) // Usar la imagen en lugar del rectángulo
                .buildAndAttach();

        // Etiqueta con el nombre de la recepcionista
        Text label = new Text("Recepcionista");
        label.setTranslateX(reception.getX() - 15);
        label.setTranslateY(reception.getY() - 70);
        FXGL.addUINode(label);
    }

    public void setTotalCocineros(int totalCocineros) {
        this.totalCocineros = totalCocineros;
    }





    //MESERO
    public void añadirMesero(int meseroId) {
        runOnce(() -> {

            Image imagenMesero = new Image(getClass().getResource("/assets/textures/mesero.png").toExternalForm());
            ImageView imagenViewMesero = new ImageView(imagenMesero);
            imagenViewMesero.setFitWidth(60);  // Ajusta el tamaño de la imagen
            imagenViewMesero.setFitHeight(60); // Ajusta el tamaño de la imagen


            Entity mesero = entityBuilder()
                    .at(reception.add(30 * meseroId, 0))
                    .view(imagenViewMesero)
                    .buildAndAttach();


            meserosVisuales.put(meseroId, mesero);
            return null;
        }, Duration.seconds(0));
    }


    public void moverMesero(int meseroId, double x, double y) {
        Entity mesero = meserosVisuales.get(meseroId);
        if (mesero != null) {
            animationBuilder()
                    .duration(Duration.seconds(1))
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
                    .from(mesero.getPosition())
                    .to(new Point2D(x, y))
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

            });
        }
    }

    public void entregarOrden(int meseroId, int numeroMesa) {
        Mesa mesa = mesas.get(numeroMesa - 1);
        Point2D posicionMesa = new Point2D(mesa.getPosX(), mesa.getPosY());
        Point2D cocina = this.kitchen;


        moverMesero(meseroId, cocina.getX(), cocina.getY(), () -> {
            System.out.println("Mesero " + meseroId + " recogió la orden de la cocina.");


            moverMesero(meseroId, posicionMesa.getX(), posicionMesa.getY(), () -> {
                System.out.println("Mesero " + meseroId + " entregó la orden en la mesa " + numeroMesa);
            });
        });
    }

    //COMENSAL


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

            Entity comensal = comensalesVisuales.get(comensalId);
            if (comensal == null) {

                Image imagenComensal = new Image(getClass().getResource("/assets/textures/comensal.png").toExternalForm());
                ImageView imagenViewComensal = new ImageView(imagenComensal);
                imagenViewComensal.setFitWidth(60);
                imagenViewComensal.setFitHeight(60);


                comensal = FXGL.entityBuilder()
                        .at(entrance.add(-50, 10))
                        .view(imagenViewComensal)
                        .buildAndAttach();

                comensalesVisuales.put(comensalId, comensal);
            }


            animationBuilder()
                    .duration(Duration.seconds(1))
                    .interpolator(Interpolators.LINEAR.EASE_IN())
                    .translate(comensal)
                    .to(entrance)
                    .buildAndPlay();

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
                    .from(comensal.getPosition())
                    .to(new Point2D(x, y))
                    .onFinished(onFinish)
                    .buildSequence()
                    .start();
        }
    }

    public void asignarMesa(int comensalId, int numeroMesa) {
        Mesa mesa = mesas.get(numeroMesa - 1);
        Point2D posicionMesa = new Point2D(mesa.getPosX(), mesa.getPosY());


        moverComensal(comensalId, posicionMesa.getX(), posicionMesa.getY(), () -> {
            System.out.println("Comensal " + comensalId + " llegó a la mesa " + numeroMesa);


            double tiempoDeComida = 5 + Math.random() * 5;
            FXGL.runOnce(() -> {
                System.out.println("Comensal " + comensalId + " terminó de comer.");
                retirarComensal(comensalId);
            }, Duration.seconds(tiempoDeComida));
        });
    }

    public void retirarComensal(int comensalId) {
        Entity comensal = comensalesVisuales.get(comensalId);
        if (comensal != null) {
            animationBuilder()
                    .duration(Duration.seconds(2))
                    .interpolator(Interpolators.LINEAR.EASE_IN())
                    .translate(comensal)
                    .to(new Point2D(-500, 400))
                    .buildAndPlay();

            runOnce(() -> {
                System.out.println("Comensal " + comensalId + " se retiró.");
                FXGL.getGameWorld().removeEntity(comensal);
                comensalesVisuales.remove(comensalId);
                return null;
            }, Duration.seconds(1));
        }
    }


}
