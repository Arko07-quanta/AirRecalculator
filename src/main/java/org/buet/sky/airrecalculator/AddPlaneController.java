package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class AddPlaneController {

    @FXML public Button profileName;
    @FXML private TextField planeNameField;

    // Sliders and value labels
    @FXML private Slider fuelCapacitySlider;
    @FXML private Label fuelCapacityValue;

    @FXML private Slider speedSlider;
    @FXML private Label speedValue;

    @FXML private Slider mileageSlider;
    @FXML private Label mileageValue;

    @FXML private Slider seatCountSlider;
    @FXML private Label seatCountValue;

    @FXML private TextField departureAirportField;

    @FXML
    public void initialize() {
        String x = generateFlightId();
        planeNameField.setText(x);
        planeNameField.setEditable(false);

        profileName.setText(Main.company.getName());

        fuelCapacityValue.textProperty().bind(
                fuelCapacitySlider.valueProperty().asString("%.0f")
        );
        speedValue.textProperty().bind(
                speedSlider.valueProperty().asString("%.0f")
        );
        mileageValue.textProperty().bind(
                mileageSlider.valueProperty().asString("%.0f")
        );
        seatCountValue.textProperty().bind(
                seatCountSlider.valueProperty().asString("%.0f")
        );
    }

    private String generateFlightId() {
        String init = Main.company.getName().substring(0,1).toUpperCase();
        if (Main.company.getName().length() > 1)
            init = Main.company.getName().substring(0, 2).toUpperCase();
        return init + System.currentTimeMillis() % 100000;
    }

    @FXML
    public void onSubmit(ActionEvent event) {
        String name = planeNameField.getText();
        int fuel = (int) fuelCapacitySlider.getValue();
        double speed = speedSlider.getValue();
        double mileage = mileageSlider.getValue();
        int departureAirport = Integer.parseInt(
                departureAirportField.getText().trim()
        );
        long seats = (long) seatCountSlider.getValue();

        // Apply bitshift for seat count
        seats = 1L << seats;

        if (name.isEmpty() || fuel == 0 || mileage == 0 || departureAirport == 0) {
            Main.showPopup("Please fill all the fields", Alert.AlertType.INFORMATION);
            return;
        }

        int companyId = Main.company.getId();

        AirPlane plane = new AirPlane(
                name, fuel, speed, departureAirport, companyId, mileage, seats
        );
        Command cmd = new Command(8, plane);
        System.out.println(cmd.obj);
        Main.obj.writerPush(cmd);

        Main.showPopup("Plane added successfully", Alert.AlertType.CONFIRMATION);
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
