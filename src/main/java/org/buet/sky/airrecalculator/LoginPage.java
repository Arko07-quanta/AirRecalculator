package org.buet.sky.airrecalculator;

import com.almasb.fxgl.ui.FXGLButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginPage {
    private final Stage stage;

    LoginPage(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //stage.centerOnScreen();
    }
}