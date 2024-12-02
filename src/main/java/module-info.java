module com.example.restaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.almasb.fxgl.all;

    opens com.Simulador.Restaurante to javafx.fxml;


    exports com.Simulador.Restaurante;
}
