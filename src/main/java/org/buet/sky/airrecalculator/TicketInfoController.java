package org.buet.sky.airrecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketInfoController {


    @FXML public FlowPane ticketPane;
    @FXML public GridPane seatGrid;
    private AirPlane airPlane;
    List<Integer> seats = new ArrayList<>();

    public void setAirPlane(AirPlane airPlane) {
        this.airPlane = airPlane;
        loadSeats();
        System.out.println("Air Plane from ticketInfoController: " + airPlane);
    }

    public int getTotalSeats(){
        return 63 - Long.numberOfLeadingZeros(airPlane.getTicket());
    }

    public boolean isBooked(int i){
        return ((getTotalSeats()>>(i-1)) & 1)==1;
    }

    public void loadSeats(){
        for (int i = 1; i <= getTotalSeats(); i++) {
            Button seatBtn = new Button("Seat " + i);
            seatBtn.setMinWidth(80);
            seatBtn.setMinHeight(40);

            // Position calculation: two seats left, aisle, two seats right per row
            int row = (i - 1) / 4;
            int posInRow = (i - 1) % 4;
            int col = (posInRow < 2) ? posInRow : posInRow + 1; // skip aisle column 2

            // Style booked seats red and disable
            if (isBooked(i)) {
                seatBtn.setStyle("-fx-background-color: gray; -fx-text-fill: black;");
                seatBtn.setDisable(true);
            } else {
                seatBtn.setStyle("""
                                    -fx-background-color: transparent;
                                    -fx-border-color: white;
                                    -fx-text-fill: white;
                                    -fx-border-radius: 8px;
                                    -fx-background-radius: 8px;
                                    -fx-font-size: 14px;
                                """);
                int finalI = i;
                seatBtn.setOnAction(e -> {
                    System.out.println("Clicked seat: " + finalI);
                    seatBtn.setStyle("-fx-background-color: #3a86ff; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8px;");

                    seats.add(finalI-1);
                });
            }

            seatGrid.add(seatBtn, col, row);
        }
    }

    public void initialize() {
        Main.obj.writerPush(new Command(-1, (new ArrayList<Integer>()).add(18)));
        Main.controller.put(60,this);
    }

    private Optional<ButtonType> showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML public void onBookTickets(ActionEvent event) throws IOException {
        if(seats.size() != 1){
            showAlert("Booking Error", "Please select a single seat");
            return;
        }
//        long x = airPlane.getTicket() | seats.getFirst();
//        airPlane.setTicket(x);
//        Main.obj.writerPush(new Command(8,airPlane));
    }
}
