import java.util.Objects;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import jeo.ui.Ui;

/**
 * Represents the controller for MainWindow. Provides the layout for the other controls.
 * @author Goh Jun How
 * @version 0.1
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private JeoBot jeo;
    private final Ui ui = new Ui();

    private final Image userImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream("/images/DaUser.png")));
    private final Image jeoImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream("/images/DaJeo.png")));

    /**
     * Initialize the start GUI screen.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getJeoDialog(ui.greetingMessage(), jeoImage));
    }

    public void setJeo(JeoBot d) {
        jeo = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = jeo.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJeoDialog(response, jeoImage)
        );
        userInput.clear();
        String rawResponse = response.split("\\n")[2];
        if (rawResponse.equals(ui.exitMessage())) {
            Stage sb = (Stage) scrollPane.getScene().getWindow();
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> sb.close());
            delay.play();
        }
    }
}
