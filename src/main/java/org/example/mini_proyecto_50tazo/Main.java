package org.example.mini_proyecto_50tazo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
/**
 * Clase principal de la aplicación Cincuentazo.
 * Extiende Application de JavaFX para iniciar la interfaz gráfica.
 *
 * El juego Cincuentazo es un juego de cartas donde el objetivo es evitar
 * que la suma de las cartas supere 50.
 *
 * @author Juan Sebastian Tapia
 * @version 1.0
 * @since 2025
 */

public class Main extends Application {
    /**
     * Inicia la aplicación JavaFX cargando la vista de selección de jugadores.
     *
     * @param stage el escenario principal de la aplicación
     * @throws Exception si ocurre un error al cargar el FXML
     */

    @Override
    public void start(Stage stage) throws Exception {

        // Carga compatible con Maven + MVND + JavaFX 20
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL fxmlLocation = cl.getResource("ui/PlayerSelectionView.fxml");

        // Mensaje de verificación
        if (fxmlLocation == null) {
            System.out.println("❌ ERROR: PlayerSelectionView.fxml NO ENCONTRADO");
            throw new RuntimeException("No se encontró PlayerSelectionView.fxml");
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(loader.load());

        stage.setTitle("Cincuentazo - Selección de jugadores");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
