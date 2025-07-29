package org.buet.sky.airrecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.util.ArrayList;

public class BookTicketController {
    @FXML public TextField passportIdField;
    @FXML public TextField phoneNumberField;
    @FXML public TextField creditCardField;
    @FXML public TextField seatNumberField;
    private AirPlane airPlane;
    private int number;

    public void getSetNumber(int number, String name){
        String str = name.substring(0, 1).toUpperCase();
        if(name.length()>1){
            str = name.substring(0,2).toUpperCase();
        }
        String formatted = String.format("%02d", number);
        String numberStr = str + formatted;
        seatNumberField.setText(numberStr);
    }

    public void setAtrributes(AirPlane airPlane, int number) {
        this.airPlane = airPlane;
        this.number = number;
        getSetNumber(number,airPlane.getName());
    }

    public void onConfirmBooking(ActionEvent event) throws IOException {
        String passportId = passportIdField.getText();
        String phoneNumber = phoneNumberField.getText();
        String creditCard = creditCardField.getText();
        if (passportId.isEmpty() || phoneNumber.isEmpty() || creditCard.isEmpty()) {
            Main.showPopup("Please fill all the fields");
        }
        else{
            long x = airPlane.getTicket();
            x = x|((long)1<<(number-1));
            airPlane.setTicket(x);
            Main.obj.writerPush(new Command(8,airPlane));
            Main.showPopup("Booking Successful");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
