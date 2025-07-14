package org.buet.sky.airrecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class RequestPageController {
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
}
