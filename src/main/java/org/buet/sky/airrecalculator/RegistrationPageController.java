package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;



public class RegistrationPageController {
    @FXML public TextField regName;
    @FXML public TextField regMail;
    @FXML public TextField regPhone;
    @FXML public PasswordField regPass;
    @FXML public PasswordField regConfirmPass;
    @FXML public Button createAccount;


    @FXML
    public void showPopup(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    @FXML
    public void onCreateAccount(ActionEvent event) throws IOException {
        String pass = regPass.getText();
        String passConfirm = regConfirmPass.getText();
        String name = regName.getText();
        String mail = regMail.getText();
        String phone = regPhone.getText();
        if(pass.isEmpty() || passConfirm.isEmpty() || name.isEmpty() || mail.isEmpty() || phone.isEmpty()){
            showPopup("Please fill all the fields");
            return;
        }
        if(!pass.equals(passConfirm)){
            showPopup("Passwords do not match");
            return;
        }
        Company newCompany = new Company(name, mail, phone, pass);
        Command cmd = new Command(1,newCompany);
        Main.obj.writerPush(cmd);
        showPopup("Successfully Created Company");
        regPass.clear();
        regConfirmPass.clear();
        regName.clear();
        regMail.clear();
        regPhone.clear();
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

    @FXML
    public void onSchedulePage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        SchedulePage schedulePage = new SchedulePage(stage);
        schedulePage.show();
    }


}
