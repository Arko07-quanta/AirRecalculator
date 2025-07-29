package org.buet.sky.airrecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class LoginPageController {


    @FXML public TextField userName;
    @FXML public PasswordField userPass;

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
    public void onRequestPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        RequestPage requestPage = new RequestPage(stage);
        requestPage.show();
    }

    @FXML
    public void onLogin(ActionEvent event) throws IOException {
        String name = userName.getText();
        String pass = userPass.getText();
        Main.serverStatus = false;
        if(pass.isEmpty() || name.isEmpty()){
            Main.showPopup("Please fill all the fields", Alert.AlertType.INFORMATION);
            return;
        }
        Company company = new Company(name,pass);
        Command cmd = new Command(0, company);
        Main.obj.writerPush(cmd);
        while(!Main.serverStatus){
            try{
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(Main.company != null){
            Main.loginStatus = true;
            Main.serverStatus = false;
            Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            ProfilePage profilePage = new ProfilePage(stage);
            profilePage.show();
        }
        else{
            Main.showPopup("Invalid username or password",Alert.AlertType.ERROR);
            Main.serverStatus = false;
        }
        return;
    }

    @FXML
    public void onSchedulePage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        SchedulePage schedulePage = new SchedulePage(stage);
        schedulePage.show();
    }

}
