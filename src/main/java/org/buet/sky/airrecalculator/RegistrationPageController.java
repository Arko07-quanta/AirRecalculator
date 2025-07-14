package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;


public class RegistrationPageController {
    @FXML
    public void onCreateAccount(ActionEvent event) throws IOException {
        return;
    }

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
}
