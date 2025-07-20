package org.buet.sky.airrecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;



public class RequestPageController {
    @FXML
    private Request request;

    @FXML
    private ComboBox companyName;

    @FXML
    private ComboBox departureTime;

    @FXML
    private ComboBox fuelCapacity;

    @FXML
    private ComboBox onTimeSelected;



    @FXML
    public void onRequest(ActionEvent event) throws IOException {
        return;
    }

    @FXML
    public void onMain(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        MainPage mainPage = new MainPage(stage);
        mainPage.show();
    }

    @FXML
    public void onRegistrationPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        RegistrationPage registrationPage = new RegistrationPage(stage);
        registrationPage.show();
    }

    @FXML
    public void onLoginPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        LoginPage loginPage = new LoginPage(stage);
        loginPage.show();
    }


    @FXML
    public void onSchedulePage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        SchedulePage schedulePage = new SchedulePage(stage);
        schedulePage.show();
    }




    @FXML
    public void setFlightId(ActionEvent event) throws IOException {
        request.setFlightId(Integer.parseInt(((ComboBox) event.getSource()).getValue().toString()));
    }

    @FXML
    public void setTime(ActionEvent event) throws IOException {
        request.setTime(Integer.parseInt(((ComboBox) event.getSource()).getValue().toString()));
    }

    @FXML
    public void setCapacity(ActionEvent event) throws IOException {
        request.setCapacity(Integer.parseInt(((ComboBox) event.getSource()).getValue().toString()));
    }

    @FXML
    public void setCompanyName(ActionEvent event) throws IOException {
        request.setCompanyName(((ComboBox) event.getSource()).getValue().toString());
    }


    @FXML
    public void setDeparture(ActionEvent event) throws IOException {
        request.setDeparture(((ComboBox) event.getSource()).getValue().toString());
    }



    @FXML
    public void setArrival(ActionEvent event) throws IOException {
        request.setArrival(((ComboBox) event.getSource()).getValue().toString());
    }



    @FXML
    public void onButtonClick(ActionEvent event) throws IOException {
        request.print();
    }

    @FXML
    private void onDepartureSelected(ActionEvent event) {
        // Your code here to handle departure selection
    }

    @FXML
    private void onDestinationSelected(ActionEvent event) {
        // Handle destination selection
    }

    @FXML
    private void onTimeSelected(ActionEvent event) {
        // Handle time selection
    }

    @FXML
    private void onCapacitySelected(ActionEvent event) {
        // Handle capacity selection
    }



    @FXML
    public void initialize() {
        request = new Request();

        URL url = getClass().getResource("/data/request.txt");
        try {
            assert url != null;
            Scanner sc = new Scanner(new File(url.toURI()));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
                companyName.getItems().add(line);
            }
        } catch (Exception e) {
            System.out.println("Error loading request file");
        }
    }
}
