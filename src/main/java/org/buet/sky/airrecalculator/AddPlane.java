package org.buet.sky.airrecalculator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPlane {

    private final Stage stage;

    AddPlane(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddPlane.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //stage.centerOnScreen();
    }
}
