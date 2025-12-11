module cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.mini_proyecto_50tazo to javafx.fxml;
    opens org.example.mini_proyecto_50tazo.controller to javafx.fxml;

    exports org.example.mini_proyecto_50tazo;
}
