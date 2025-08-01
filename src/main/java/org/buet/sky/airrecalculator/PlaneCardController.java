package org.buet.sky.airrecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaneCardController {
    @FXML public Label cost;
    @FXML public Button viewTicketsButton;
    @FXML public Label availableTickets;
    @FXML private Label planeName;
    @FXML private Label planeId;
    @FXML private Label fuel;
    @FXML private Label departureAirport;
    @FXML private Label destinationAirport;
    @FXML private Label departureTime;
    @FXML private Label flightTime;
    private AirPlane airPlane;
    public boolean status = true;

    public void setPlaneData(String id, String name, String fuelVal, String dep, String dest, String depTime, String flightDuration, String cst, AirPlane airPlane) {
        if(airPlane.isTimeEfficient()){
            setTextColor("#ff4c4c", planeName, planeId, fuel, departureAirport, destinationAirport, departureTime, flightTime, cost, availableTickets);
        }else{
            setTextColor("#4caf50", planeName, planeId, fuel, departureAirport, destinationAirport, departureTime, flightTime, cost, availableTickets);
        }
        planeName.setText(name);
        planeId.setText("ID: " + id);
        fuel.setText("Fuel: " + fuelVal);
        try {
            int depId = Integer.parseInt(dep);
            City city = Main.hashCityMap.get(depId);

            if (city != null && city.getName() != null) {
                departureAirport.setText("From: " + city.getName());
            } else {
                departureAirport.setText("From: N/A");
            }
        } catch (NumberFormatException e) {
            departureAirport.setText("From: Invalid ID");
            System.err.println("Failed to parse dep: " + dep);
        }
        try {
            int destId = Integer.parseInt(dest);
            City city = Main.hashCityMap.get(destId);

            if (city != null && city.getName() != null) {
                destinationAirport.setText("To: " + city.getName());
            } else {
                destinationAirport.setText("To: N/A");
            }
        } catch (NumberFormatException e) {
            departureAirport.setText("To: Invalid ID");
            System.err.println("Failed to parse dest: " + dest);
        }

        departureTime.setText("Departure: " + depTime + " M");
        flightTime.setText("Duration: " + flightDuration + " M");
        cost.setText("Cost: " + cst + "$");
        this.airPlane = airPlane;
        availableTickets.setText(getTicketCount());
        System.out.println("PlaneCardController: setPlaneData");
        System.out.println(airPlane);
        System.out.println(status);
    }

    public void onViewTickets(ActionEvent e) throws IOException {
        Stage newStage = new Stage();
        newStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        newStage.setTitle("Book Ticket");
        newStage.setResizable(false);
        TicketInfo ticketInfo = new TicketInfo(newStage, airPlane);
        ticketInfo.status = this.status;
        ticketInfo.show();
        return;
    }

    private void setTextColor(String color, Label... labels) {
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web(color));       // Light cyan glow
        glow.setRadius(0.5);
        glow.setSpread(0.001);
        for (Label label : labels) {
            label.setStyle("-fx-text-fill: " + color + ";");
            label.setEffect(glow);
        }
        viewTicketsButton.setStyle("-fx-text-fill: " + color + ";");
        viewTicketsButton.setEffect(null);
    }

    private String getTicketCount(){
        Long x = airPlane.getTicket();
        int totalSeat = 63 - Long.numberOfLeadingZeros(x);
        int availableSeat = totalSeat - Long.bitCount(x) + 1;
        return  "Available Seats: " + availableSeat + "/" + totalSeat;
    }

}
