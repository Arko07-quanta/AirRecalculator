package org.buet.sky.airrecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class TicketInfoController {


    @FXML public FlowPane ticketPane;
    @FXML public GridPane seatGrid;
    @FXML public Button bookButton;
    private AirPlane airPlane;
    public boolean status = true;
    Set<Integer> selectedSeats = new HashSet<>();

    public void setAirPlane(AirPlane airPlane) {
        this.airPlane = airPlane;
        loadSeats();
        System.out.println("Air Plane from ticketInfoController: " + airPlane);
    }

    public int getTotalSeats(){
        return 63 - Long.numberOfLeadingZeros(airPlane.getTicket());
    }

    public boolean isBooked(int i){
        return ((airPlane.getTicket()>>(i-1)) & 1)==1;
    }

    public void loadSeats() {
        seatGrid.getChildren().clear();
        if(!status) bookButton.setVisible(false);
        for (int i = 1; i <= getTotalSeats(); i++) {
            Button seatBtn = new Button("Seat " + i);
            seatBtn.setMinWidth(80);
            seatBtn.setMinHeight(40);

            int row = (i - 1) / 4;
            int posInRow = (i - 1) % 4;
            int col = (posInRow < 2) ? posInRow : posInRow + 1;

            if (isBooked(i)) {
                seatBtn.setStyle("-fx-background-color: gray; -fx-text-fill: black;");
                seatBtn.setDisable(true);
            } else {
                if (selectedSeats.contains(i)) {
                    seatBtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white;");
                } else {
                    seatBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
                }

                int finalI = i;
                seatBtn.setOnAction(e -> {
                    if (selectedSeats.contains(finalI)) {
                        selectedSeats.remove(finalI);
                    } else {
                        selectedSeats.clear();
                        selectedSeats.add(finalI);
                    }
                    loadSeats();
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
        if(selectedSeats.size() != 1){
            showAlert("Booking Error", "Please select a single seat");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BookTicket.fxml"));
        Parent root = loader.load();
        BookTicketController controller = loader.getController();
        controller.setAtrributes(airPlane,selectedSeats.iterator().next());
        Stage stage = new Stage();
        stage.setTitle("Ticket Confirmation");
        stage.setScene(new Scene(root));
        stage.show();
        if(!stage.isShowing()){
            loadSeats();
        }
    }
}
