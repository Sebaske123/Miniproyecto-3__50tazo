package org.example.mini_proyecto_50tazo.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.example.mini_proyecto_50tazo.model.game.GameUnoModel;
import org.example.mini_proyecto_50tazo.model.machine.ThreadMachinePlayer;
import org.example.mini_proyecto_50tazo.model.player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for player selection screen.
 * Allows the user to choose the number of machine opponents.
 *
 * @author Juan Sebastian Tapia
 * @version 1.0
 * @since 2024
 */
public class PlayerSelectionController {

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btnSalir;

    private int selectedMachines = 0;

    /**
     * Initializes the controller and sets up button actions.
     */
    @FXML
    public void initialize() {
        btn1.setOnAction(e -> select(1));
        btn2.setOnAction(e -> select(2));
        btn3.setOnAction(e -> select(3));

        // Exit button
        if (btnSalir != null) {
            btnSalir.setOnAction(e -> exitApplication());
        }
    }

    /**
     * Handles machine player selection.
     *
     * @param n number of machine players selected
     */
    private void select(int n) {
        selectedMachines = n;
        startGame();
    }

    /**
     * Exits the application.
     */
    private void exitApplication() {
        System.out.println("🚪 Cerrando aplicación...");
        Platform.exit();
        System.exit(0);
    }

    /**
     * Starts the game with selected number of machines.
     */
    private void startGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/GameView.fxml"));
            Scene scene = new Scene(loader.load());

            // CORRECCIÓN: Obtener el controller correctamente
            org.example.mini_proyecto_50tazo.controller.GameUnoController controller = loader.getController();

            // Crear modelo con n máquinas
            GameUnoModel model = new GameUnoModel(selectedMachines);

            // Crear hilos para las máquinas
            List<Thread> threads = new ArrayList<>();
            for (Player p : model.getPlayers()) {
                if (!p.getName().equals("HUMAN")) {
                    ThreadMachinePlayer tmp = new ThreadMachinePlayer(model, p);
                    Thread th = new Thread(tmp);
                    th.setDaemon(true);
                    threads.add(th);
                }
            }

            // iniciar hilos
            for (Thread t : threads) t.start();

            // CORRECCIÓN: Llamar a init() en lugar de wait()
            controller.init(model);

            Stage stage = (Stage) btn1.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Cincuentazo - Juego");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR, "Error cargando el juego.");
            alert.show();
        }
    }
}