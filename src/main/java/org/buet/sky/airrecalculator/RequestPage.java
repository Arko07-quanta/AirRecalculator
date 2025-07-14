package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RequestPage {
    private final Stage stage;

    RequestPage(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RequestPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}
