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
        try {

            Parent root = FXMLLoader.load(getClass().getResource("AddPlane.fxml"));
            System.out.println("getting FXML");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        //stage.centerOnScreen();
    }
}
