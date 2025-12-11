package org.example.mini_proyecto_50tazo.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.mini_proyecto_50tazo.model.card.Card;
import org.example.mini_proyecto_50tazo.model.game.GameUnoModel;
import org.example.mini_proyecto_50tazo.model.player.Player;

import java.util.Optional;

/**
 * Controller for the main game view.
 * Handles UI updates and player interactions.
 *
 * @author Juan Sebastian Tapia
 * @version 1.0
 * @since 2024
 */
public class GameUnoController {

    @FXML private HBox humanHandBox;
    @FXML private Label lblCurrentSum;
    @FXML private Label lblTurn;
    @FXML private ImageView imgTopCard;
    @FXML private Button btnDraw;
    @FXML private Button btnSalir;

    private GameUnoModel model;

    /**
     * Initializes the controller with the game model.
     *
     * @param model the game model
     */
    public void init(GameUnoModel model) {
        this.model = model;

        // Draw card button
        btnDraw.setOnAction(e -> {
            Player human = model.getPlayers().get(0);

            // Check if it's human's turn BEFORE doing anything
            if (!model.getCurrentPlayer().equals(human)) {
                showAlert("No es tu turno", "Espera a que sea tu turno para robar.");
                return; // Exit immediately without drawing
            }

            try {
                model.drawCard(human);
                Platform.runLater(this::renderHumanHand);
                System.out.println("✅ Carta robada");
            } catch (Exception ex) {
                showAlert("Error", "No se pudo robar carta: " + ex.getMessage());
            }
        });

        // Exit button
        if (btnSalir != null) {
            btnSalir.setOnAction(e -> handleExit());
        }

        renderHumanHand();
        updateUI();

        // UI update loop - only update when game state changes
        Thread uiLoop = new Thread(() -> {
            int lastSum = model.getCurrentSum();
            Player lastPlayer = model.getCurrentPlayer();
            int lastHandSize = model.getPlayers().get(0).getHand().size();

            while (!model.isGameOver()) {
                try {
                    int currentSum = model.getCurrentSum();
                    Player currentPlayer = model.getCurrentPlayer();
                    int currentHandSize = model.getPlayers().isEmpty() ? 0 :
                            model.getPlayers().get(0).getHand().size();

                    // Only update if something actually changed
                    boolean stateChanged = (currentSum != lastSum) ||
                            (currentPlayer != lastPlayer) ||
                            (currentHandSize != lastHandSize);

                    if (stateChanged) {
                        Platform.runLater(() -> {
                            updateUI();
                            renderHumanHand();
                        });

                        lastSum = currentSum;
                        lastPlayer = currentPlayer;
                        lastHandSize = currentHandSize;
                    }

                    Thread.sleep(350);
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    System.err.println("UI loop error: " + e.getMessage());
                }
            }

            Platform.runLater(this::updateUI);
        });

        uiLoop.setDaemon(true);
        uiLoop.start();
    }

    /**
     * Handles the exit button action with confirmation dialog.
     */
    private void handleExit() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmar salida");
        confirmAlert.setHeaderText("¿Estás seguro que deseas salir?");
        confirmAlert.setContentText("Elige tu opción:");

        ButtonType btnReturnMenu = new ButtonType("Volver al menú");
        ButtonType btnCloseApp = new ButtonType("Cerrar aplicación");
        ButtonType btnCancel = new ButtonType("Cancelar");

        confirmAlert.getButtonTypes().setAll(btnReturnMenu, btnCloseApp, btnCancel);

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == btnReturnMenu) {
                returnToMenu();
            } else if (result.get() == btnCloseApp) {
                closeApplication();
            }
            // If cancel, do nothing
        }
    }

    /**
     * Returns to the player selection menu.
     */
    private void returnToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/PlayerSelectionView.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Cincuentazo - Selección de jugadores");
            stage.show();

            System.out.println("🔙 Volviendo al menú...");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo volver al menú: " + e.getMessage());
        }
    }

    /**
     * Closes the application completely.
     */
    private void closeApplication() {
        System.out.println("🚪 Cerrando aplicación...");
        Platform.exit();
        System.exit(0);
    }

    /**
     * Renders the human player's hand as visual cards.
     */
    private void renderHumanHand() {
        humanHandBox.getChildren().clear();

        // Safety check: ensure player still exists
        if (model.getPlayers().isEmpty()) return;

        Player human = model.getPlayers().get(0);
        if (human == null || !human.getName().equals("HUMAN")) return;

        for (Card card : human.getHand()) {
            ImageView cardImage = createCardImage(card);

            // Check if card is playable
            boolean playable = card.canBePlayed(model.getCurrentSum());
            boolean isHumanTurn = model.getCurrentPlayer().equals(human);

            // Add visual feedback
            if (playable && isHumanTurn) {
                // Green glow for playable cards
                DropShadow greenGlow = new DropShadow();
                greenGlow.setColor(Color.LIME);
                greenGlow.setRadius(20);
                greenGlow.setSpread(0.7);
                cardImage.setEffect(greenGlow);
                cardImage.setStyle("-fx-cursor: hand;");
                cardImage.setOpacity(1.0);
            } else {
                // Red glow for unplayable cards
                DropShadow redGlow = new DropShadow();
                redGlow.setColor(Color.RED);
                redGlow.setRadius(15);
                redGlow.setSpread(0.5);
                cardImage.setEffect(redGlow);
                cardImage.setOpacity(0.6);
            }

            // Click handler
            cardImage.setOnMouseClicked(e -> {
                if (!isHumanTurn) {
                    showAlert("No es tu turno", "Espera a que sea tu turno para jugar.");
                    return;
                }

                if (playable) {
                    try {
                        int previousSum = model.getCurrentSum();
                        int cardValue = card.resolveValueForPlay(previousSum);
                        model.playCard(human, card);
                        System.out.println("✅ Jugaste: " + card + " (Valor: " +
                                (cardValue >= 0 ? "+" : "") + cardValue +
                                ") | Suma: " + previousSum + " → " + model.getCurrentSum());
                    } catch (Exception ex) {
                        showAlert("Error", "No se pudo jugar la carta: " + ex.getMessage());
                    }
                } else {
                    int cardValue = card.resolveValueForPlay(model.getCurrentSum());
                    int resultSum = model.getCurrentSum() + cardValue;
                    showAlert("Carta no jugable",
                            "❌ Esta carta haría que la suma exceda 50.\n\n" +
                                    "Suma actual: " + model.getCurrentSum() + "\n" +
                                    "Valor de la carta: " + (cardValue >= 0 ? "+" : "") + cardValue + "\n" +
                                    "Resultado: " + resultSum + " (Límite: 50)");
                }
            });

            // Hover effect
            cardImage.setOnMouseEntered(e -> {
                if (playable && isHumanTurn) {
                    cardImage.setScaleX(1.15);
                    cardImage.setScaleY(1.15);
                    cardImage.setTranslateY(-10);
                }
            });

            cardImage.setOnMouseExited(e -> {
                cardImage.setScaleX(1.0);
                cardImage.setScaleY(1.0);
                cardImage.setTranslateY(0);
            });

            humanHandBox.getChildren().add(cardImage);
        }
    }

    /**
     * Creates an ImageView for a card.
     *
     * @param card the card to display
     * @return ImageView with the card image
     */
    private ImageView createCardImage(Card card) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(145);
        imageView.setPreserveRatio(true);

        try {
            String imagePath = "/" + card.getImageName();
            System.out.println("🔍 Cargando: " + imagePath);

            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imageView.setImage(image);
            System.out.println("✅ Imagen cargada: " + card);
        } catch (Exception e) {
            System.err.println("❌ Error cargando: " + card.getImageName());
            System.err.println("   Ruta: /" + card.getImageName());
            e.printStackTrace();

            // Fallback: sin imagen
            imageView.setImage(null);
        }

        return imageView;
    }

    /**
     * Updates the UI with current game state.
     */
    private void updateUI() {
        int currentSum = model.getCurrentSum();
        lblCurrentSum.setText("Suma actual: " + currentSum);

        // Color code the sum
        if (currentSum >= 45) {
            lblCurrentSum.setStyle("-fx-font-size: 20px; -fx-text-fill: red; -fx-font-weight: bold;");
        } else if (currentSum >= 35) {
            lblCurrentSum.setStyle("-fx-font-size: 20px; -fx-text-fill: orange; -fx-font-weight: bold;");
        } else {
            lblCurrentSum.setStyle("-fx-font-size: 20px; -fx-text-fill: green; -fx-font-weight: bold;");
        }

        Player currentPlayer = model.getCurrentPlayer();
        if (currentPlayer != null) {
            lblTurn.setText("🎮 Turno: " + currentPlayer.getName());

            // Enable/disable draw button based on turn
            boolean isHumanTurn = currentPlayer.getName().equals("HUMAN");
            btnDraw.setDisable(!isHumanTurn);

            if (isHumanTurn) {
                lblTurn.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: blue;");
            } else {
                lblTurn.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black;");
            }
        }

        // Show top card
        Card topCard = model.peekTopCard();
        if (topCard != null) {
            try {
                String imagePath = "/" + topCard.getImageName();
                Image img = new Image(getClass().getResourceAsStream(imagePath));
                imgTopCard.setImage(img);
            } catch (Exception e) {
                System.err.println("❌ Error cargando carta superior");
            }
        }

        // Check game over
        if (model.isGameOver()) {
            lblTurn.setText("🏆 GANADOR: " + model.getWinner().getName() + " 🏆");
            lblTurn.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: gold;");
            btnDraw.setDisable(true);
            humanHandBox.setDisable(true);

            showAlert("¡Juego Terminado!",
                    "🎉 ¡" + model.getWinner().getName() + " ha ganado el juego! 🎉\n\n" +
                            "Suma final: " + model.getCurrentSum() + "\n" +
                            "Jugadores activos al final: " + model.getPlayers().size());
        }
    }

    /**
     * Shows an alert dialog.
     *
     * @param title the alert title
     * @param message the alert message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}