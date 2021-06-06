package pa.project.windows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EndBox {
    private static boolean answer;

    /**
     * This method creates a window for starting a new game or leave the game
     * @param title represents the title of the window
     * @param text represents represents the text which will be added in the window
     * @return true if the answer is "leave the game", false otherwise
     */
    public static boolean display(String title, String text) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(200);

        Label quitConfirmation = new Label();
        quitConfirmation.setText(text);

        Label label = new Label();
        label.setText(text);

        Button resetButton = new Button("Joaca din nou");
        resetButton.setOnAction(event -> {
            answer = false;
            window.close();
        });

        Button quitButton = new Button("Paraseste Jocul");
        quitButton.setOnAction(event -> {
            answer = true;
            window.close();
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(quitConfirmation, resetButton, quitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
