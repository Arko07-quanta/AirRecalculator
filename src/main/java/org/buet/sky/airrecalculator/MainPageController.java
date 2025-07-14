package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class MainPageController {
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
    public void onRequestPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        RequestPage requestPage = new RequestPage(stage);
        requestPage.show();
    }
}
