package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaneInfoController implements Initializable {

    @FXML
    private VBox cardContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (AirPlane p : Main.airPlaneList) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("PlaneCard.fxml")
                );
                Node card = loader.load();
                PlaneCardController pc = loader.getController();

                String id                 = nullToNA(Integer.toString(p.getId()));
                String name               = nullToNA(p.getName());
                String fuel               = p.getFuelCapacity() + " L";
                String departureAirport   = nullToNA(p.getDepartureAirport());
                String destinationAirport = nullToNA(p.getDestinationAirport());
                String departureTime      = nullToNA(p.getDepartureTime());
                String flightTime         = nullToNA(p.getFlightTime());

                pc.setPlaneData(
                        name,
                        id,
                        fuel,
                        departureAirport,
                        destinationAirport,
                        departureTime,
                        flightTime
                );
                cardContainer.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String nullToNA(String s) {
        return (s == null || s.isBlank()) ? "N/A" : s;
    }

    // Navigation methods, mirroring MainPageController

    @FXML
    public void onMain(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        new MainPage(stage).show();
    }

    @FXML
    public void onSchedulePage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        new SchedulePage(stage).show();
    }

    @FXML
    public void onRequestPage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        new RequestPage(stage).show();
    }

    @FXML
    public void onLoginPage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        new LoginPage(stage).show();
    }

    @FXML
    public void onRegistrationPage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        new RegistrationPage(stage).show();
    }
}
