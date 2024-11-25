module com.example.restaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.almasb.fxgl.all;

    opens com.Simulador.Restaurante to javafx.fxml;
    opens com.Simulador.Restaurante.entities to com.almasb.fxgl.core;

    exports com.Simulador.Restaurante;
}
