package com.Simulador.Restaurante.entities;

import com.Simulador.Restaurante.business.models.EstadoMesa;
import com.Simulador.Restaurante.business.models.Mesa;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class Table extends Component {

    private Mesa mesa;
    private Rectangle statusRectangle;
    private Text tableNumberText;

    public Table(Mesa mesa) {
        this.mesa = mesa;
    }

    @Override
    public void onAdded() {
        // Crear un cuadro para mostrar el estado de la mesa
        statusRectangle = new Rectangle(20, 20);
        updateTableStatus();  // Actualizar el estado de la mesa (color del cuadro)

        // Crear el texto con el número de la mesa, ajustado encima del cuadro de la mesa
        tableNumberText = new Text(String.valueOf(mesa.getNumero()));
        tableNumberText.setFont(Font.font(12));  // Ajustar el tamaño de la fuente
        tableNumberText.setFill(Color.WHITE);


        entity.getViewComponent().addChild(statusRectangle);

    }


    public void asignarComensal() {
        mesa.asignarComensal();
        updateTableStatus();  // Actualizar el estado después de asignar el comensal
    }

    public void liberarMesa() {
        mesa.liberarMesa();
        updateTableStatus();  // Actualizar el estado después de liberar la mesa
    }

    private void updateTableStatus() {
        // Cambiar el color del cuadro según el estado de la mesa
        if (mesa.getEstado() == EstadoMesa.OCUPADA) {
            statusRectangle.setFill(Color.RED);  // Rojo si está ocupada
        } else {
            statusRectangle.setFill(Color.GREEN);  // Verde si está libre
        }
    }

    public boolean isMesaOcupada() {
        return mesa.getEstado() == EstadoMesa.OCUPADA;
    }
}
