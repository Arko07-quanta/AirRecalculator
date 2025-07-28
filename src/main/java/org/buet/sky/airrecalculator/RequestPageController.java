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
import java.util.*;


public class RequestPageController {


    @FXML public Label companyNameLabel;
    @FXML public ComboBox flightId;
    @FXML public ComboBox departureAirport;
    @FXML public ComboBox destinationAirport;
    @FXML public ComboBox departureTime;
    @FXML public Button requestButton;
    @FXML public Button profileName;
    List<String> times = new ArrayList<>();
    HashMap<String,Integer> timeRef = new HashMap<>();

    int getTime(String time){
        String[] arr = time.split(":");
        return Integer.parseInt(arr[0])*60 +  Integer.parseInt(arr[1]);
    }

    @FXML
    public void onRequest(ActionEvent event) throws IOException {
        City departureCity = (City) departureAirport.getValue();
        City destinationCity = (City) destinationAirport.getValue();
        AirPlane airPlane = (AirPlane) flightId.getValue();
        int depTime = timeRef.get((String) departureTime.getValue());
//        airPlane.setFuelCapacity(100);
//        airPlane.setMileage(3);

        // give time

        new Dijkstra(airPlane, departureCity, destinationCity, Main.cityList, true, depTime);
        new Dijkstra(airPlane, departureCity, destinationCity, Main.cityList, false, depTime);
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
    public void populateComboBoxes(){
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
        departureTime.getItems().setAll(times);
    }


    @FXML
    public void initialize() {
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                times.add(String.format("%02d:%02d", hour, minute));
            }
        }
        for(String time : times) {
            timeRef.put(time,getTime(time));
        }
        Main.controller.put(6,this);
        List<Integer> flightIds = new ArrayList<>(); flightIds.add(6); flightIds.add(18);
        Main.obj.writerPush(new Command(-1,flightIds));
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
