package org.buet.sky.airrecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaneCardController {
    @FXML public Label cost;
    @FXML public Button viewTicketsButton;
    @FXML private Label planeName;
    @FXML private Label planeId;
    @FXML private Label fuel;
    @FXML private Label departureAirport;
    @FXML private Label destinationAirport;
    @FXML private Label departureTime;
    @FXML private Label flightTime;
    private AirPlane airPlane;

    public void setPlaneData(String id, String name, String fuelVal, String dep, String dest, String depTime, String flightDuration, String cst, AirPlane airPlane) {
        planeName.setText(name);
        planeId.setText("ID: " + id);
        fuel.setText("Fuel: " + fuelVal);
        departureAirport.setText("From: " + dep);
        destinationAirport.setText("To: " + dest);
        departureTime.setText("Departure: " + depTime);
        flightTime.setText("Duration: " + flightDuration);
        cost.setText("Cost: " + cst);
        this.airPlane = airPlane;
        System.out.println("PlaneCardController: setPlaneData");
        System.out.println(airPlane);
    }

    public void onViewTickets(ActionEvent e) throws IOException {
        Stage newStage = new Stage();
        TicketInfo ticketInfo = new TicketInfo(newStage, airPlane);
        ticketInfo.show();
        return;
    }
}
