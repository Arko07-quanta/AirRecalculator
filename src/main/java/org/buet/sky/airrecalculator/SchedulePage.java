package org.buet.sky.airrecalculator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchedulePage {
    private final Stage stage;

    public SchedulePage(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SchedulePage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
