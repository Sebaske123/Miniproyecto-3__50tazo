package org.example.mini_proyecto_50tazo.view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.mini_proyecto_50tazo.controller.GameUnoController;
import org.example.mini_proyecto_50tazo.model.game.GameUnoModel;

public class GameUnoStage {

    private final Stage stage;

    public GameUnoStage(Stage stage) {
        this.stage = stage;
    }

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
