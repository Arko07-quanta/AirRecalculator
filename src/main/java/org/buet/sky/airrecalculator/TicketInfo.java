package org.buet.sky.airrecalculator;

import com.almasb.fxgl.ui.FXGLButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TicketInfo {
    private final Stage stage;
    private AirPlane airPlane;


    TicketInfo(Stage stage, AirPlane airPlane) {
        this.stage = stage;
        this.airPlane = airPlane;
    }

    public void show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TicketInfo.fxml"));
        Parent root = loader.load();
        TicketInfoController controller = loader.getController();
        controller.setAirPlane(airPlane);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.centerOnScreen();
    }
}
