package org.buet.sky.airrecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;



public class RequestPageController {


    @FXML public Label companyNameLabel;
    @FXML public ComboBox flightId;
    @FXML public ComboBox departureAirport;
    @FXML public ComboBox destinationAirport;
    @FXML public ComboBox departureTime;
    @FXML public Button requestButton;
    @FXML public Button profileName;

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
        if(!Main.loginStatus) {
            Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            LoginPage loginPage = new LoginPage(stage);
            loginPage.show();
        }else{
            Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            ProfilePage profilePage = new ProfilePage(stage);
            profilePage.show();
        }
    }


    @FXML
    public void onSchedulePage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        SchedulePage schedulePage = new SchedulePage(stage);
        schedulePage.show();
    }


    @FXML
    private void disableEditing() {
        flightId.setDisable(true);
        departureAirport.setDisable(true);
        destinationAirport.setDisable(true);
        departureTime.setDisable(true);
    }

    @FXML
    private void enableEditing() {
        flightId.setDisable(false);
        departureAirport.setDisable(false);
        destinationAirport.setDisable(false);
        departureTime.setDisable(false);
    }

    @FXML
    private void populateComboBoxes(){
        // Populate flightId with planeList
        flightId.getItems().clear();
        if(Main.airPlaneList != null) flightId.getItems().addAll(Main.airPlaneList);

        // Populate departureAirport and destinationAirport with cityList
        departureAirport.getItems().clear();
        destinationAirport.getItems().clear();
        if(Main.cityList != null) departureAirport.getItems().addAll(Main.cityList);
        if(Main.cityList != null) destinationAirport.getItems().addAll(Main.cityList);

        // You can also populate departureTime if you have data for that
        // Example dummy times:
        departureTime.getItems().clear();
        departureTime.getItems().addAll("06:00 AM", "12:00 PM", "06:00 PM");
    }


    @FXML
    public void initialize() {
        if(!Main.loginStatus){
            profileName.setText("Login");
            disableEditing();
            return;
        }
        else{
            companyNameLabel.setText(Main.company.getName());
            profileName.setText(Main.company.getName());
            enableEditing();
            populateComboBoxes();
        }
    }
}
