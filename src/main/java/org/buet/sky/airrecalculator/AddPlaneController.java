package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class AddPlaneController {

    @FXML private TextField planeNameField;
    @FXML private TextField fuelCapacityField;
    @FXML private TextField flightIdField;
    @FXML private TextField companyIdField;

    @FXML
    public void initialize() {
        // auto‐generate Flight ID
        flightIdField.setText(generateFlightId());
        // auto‐fill Company ID from the logged‐in user (stubbed here)
        companyIdField.setText(getCurrentCompanyId());
        // make sure read‐only
        flightIdField.setEditable(false);
        companyIdField.setEditable(false);
    }

    private String generateFlightId() {
        String init = Main.company.getName().substring(0, 2).toUpperCase();
        return init + System.currentTimeMillis() % 100000;
    }

    private String getCurrentCompanyId() {
        return Integer.toString(Main.company.getId());
    }

    @FXML
    public void onSubmit(ActionEvent event) {
        String name = planeNameField.getText().trim();
        String fuelText = fuelCapacityField.getText().trim();
        if (name.isEmpty() || fuelText.isEmpty()) {
            Main.showPopup("Please fill all the fields");
            return;
        }

        int fuel;
        try {
            fuel = Integer.parseInt(fuelText);
        } catch (NumberFormatException e) {
            System.err.println("Fuel capacity must be a number");
            return;
        }

        int companyId = Main.company.getId();
        // currentLocation is assumed 0 or default; adjust as needed
        AirPlane plane = new AirPlane(name, fuel, 0, companyId);
        Command cmd = new Command(2,plane);
        Main.obj.writerPush(cmd);
        // Navigate back to plane info or clear form
        Main.showPopup("Plane added successfully");
    }


    @FXML
    public void onMain(ActionEvent event) throws IOException {
        Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        MainPage main = new MainPage(stage);
        main.show();
    }

    @FXML
    public void onSchedulePage(ActionEvent event) throws IOException {
        Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        SchedulePage schedule = new SchedulePage(stage);
        schedule.show();
    }

    @FXML
    public void onRequestPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        RequestPage request = new RequestPage(stage);
        request.show();
    }

    @FXML
    public void onLoginPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        ProfilePage profile = new ProfilePage(stage);
        profile.show();
    }

    @FXML
    public void onRegistrationPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage)((Node)event.getSource()).getScene().getWindow());
        RegistrationPage reg = new RegistrationPage(stage);
        reg.show();
    }
}
