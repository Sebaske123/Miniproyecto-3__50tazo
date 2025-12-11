package org.example.mini_proyecto_50tazo.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.mini_proyecto_50tazo.controller.GameUnoController;
import org.example.mini_proyecto_50tazo.model.game.GameUnoModel;
/**
 * Clase de vista que gestiona el Stage (ventana) del juego Cincuentazo.
 * Se encarga de cargar el archivo FXML y configurar el controlador.
 *
 * @author Juan Sebastian Tapia
 * @version 1.0
 * @since 2025
 */

public class GameUnoStage {
    /** El Stage de JavaFX que representa la ventana del juego */

    private final Stage stage;

    /**
     * Constructor que inicializa el GameUnoStage con un Stage existente.
     *
     * @param stage el Stage de JavaFX donde se mostrará el juego
     */

    public GameUnoStage(Stage stage) {
        this.stage = stage;
    }
    /**
     * Muestra la vista del juego con el modelo especificado.
     * Carga el FXML, inicializa el controlador y muestra la ventana.
     *
     * @param model el modelo del juego que contiene la lógica y estado
     * @throws Exception si ocurre un error al cargar el FXML o inicializar
     */

    public void show(GameUnoModel model) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/GameView.fxml"));
        Scene scene = new Scene(loader.load());

        GameUnoController controller = loader.getController();
        controller.init(model);

        stage.setTitle("Cincuentazo - Juego");
        stage.setScene(scene);
        stage.show();
    }
}
