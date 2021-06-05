package pa.project.windows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    private static boolean answer;
    public static boolean display(String title, String text) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(200);

        Label quitConfirmation = new Label();
        quitConfirmation.setText(text);

        Button noButton = new Button("Nu");
        noButton.setOnAction(event -> {
            answer = false;
            window.close();
        });

        Button yesButton = new Button("Da");
        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(quitConfirmation, noButton, yesButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
