package org.buet.sky.airrecalculator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class SchedulePageController {
    @FXML
    public TableView planeTable;
    @FXML
    public TableColumn departureAirport;
    @FXML
    public TableColumn flightID;
    @FXML
    public TableColumn companyID;
    @FXML
    public TableColumn departureTime;
    @FXML
    public TableColumn destinationAirport;
    @FXML
    public TableColumn arrivalTime;
    @FXML
    public TableColumn flightTime;
    @FXML
    public TableColumn userRating;
    @FXML public Button loginProfile;

    @FXML
    public void onMain(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        MainPage mainPage = new MainPage(stage);
        mainPage.show();
    }

    @FXML
    public void onLoginPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        LoginPage loginPage = new LoginPage(stage);
        loginPage.show();
    }

    @FXML
    public void onRequestPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        RequestPage requestPage = new RequestPage(stage);
        requestPage.show();
    }

    @FXML
    public void onRegistrationPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        RegistrationPage registrationPage = new RegistrationPage(stage);
        registrationPage.show();
    }

    @FXML
    public void initialize(){
        if(Main.loginStatus){
            loginProfile.setText(Main.company.getName());
        }
        else{
            loginProfile.setText("Login");
        }
    }
}