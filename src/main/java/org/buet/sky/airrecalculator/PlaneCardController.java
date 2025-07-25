package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlaneCardController {
    @FXML private Label planeName;
    @FXML private Label planeId;
    @FXML private Label fuel;
    @FXML private Label departureAirport;
    @FXML private Label destinationAirport;
    @FXML private Label departureTime;
    @FXML private Label flightTime;

    public void setPlaneData(String id, String name, String fuelVal, String dep, String dest, String depTime, String flightDuration) {
        planeName.setText(name);
        planeId.setText("ID: " + id);
        fuel.setText("Fuel: " + fuelVal);
        departureAirport.setText("From: " + dep);
        destinationAirport.setText("To: " + dest);
        departureTime.setText("Departure: " + depTime);
        flightTime.setText("Duration: " + flightDuration);
    }
}
