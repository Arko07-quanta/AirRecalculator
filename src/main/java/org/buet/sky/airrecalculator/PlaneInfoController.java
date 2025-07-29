package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlaneInfoController{

    @FXML public Button profileName;
    @FXML
    private VBox cardContainer;

    public void initialize() {
        Main.controller.put(18, this);
        loadData();
    }

    public void loadData(){
        profileName.setText(Main.company.getName());
        refreshPlanes();
    }

    public void refreshPlanes() {
        cardContainer.getChildren().clear();

        if (Main.airPlaneList != null) {
            for (AirPlane p : Main.airPlaneList) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("PlaneCard.fxml"));
                    Node card = loader.load();
                    PlaneCardController pc = loader.getController();

                    String id = nullToNA(Integer.toString(p.getId()));
                    String name = nullToNA(p.getName());
                    String fuel = p.getFuelCapacity() + " L";
                    String departureAirport = nullToNA(Integer.toString(p.getDepartureAirport()));
                    String destinationAirport = nullToNA(Integer.toString(p.getArrivalAirport()));
                    String departureTime = nullToNA(Integer.toString(p.getDepartureTime()));
                    String flightTime = nullToNA(Integer.toString(p.getFlightTime()));
                    String cost = nullToNA(Integer.toString(p.getCost()));

                    pc.status = false;
                    pc.setPlaneData(
                            name,
                            id,
                            fuel,
                            departureAirport,
                            destinationAirport,
                            departureTime,
                            flightTime,
                            cost,
                            p
                    );
                    cardContainer.getChildren().add(card);

                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        new ProfilePage(stage).show();
    }

    @FXML
    public void onRegistrationPage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        new RegistrationPage(stage).show();
    }
}
