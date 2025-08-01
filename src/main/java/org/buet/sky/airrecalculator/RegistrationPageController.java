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
    @FXML public Button loginProfile;
    @FXML Button profileChange;


    @FXML
    public void onCreateAccount(ActionEvent event) throws IOException {
        Main.serverStatus = false;
        String pass = regPass.getText();
        String passConfirm = regConfirmPass.getText();
        String name = regName.getText();
        String mail = regMail.getText();
        String phone = regPhone.getText();
        if(pass.isEmpty() || passConfirm.isEmpty() || name.isEmpty() || mail.isEmpty() || phone.isEmpty()){
            Main.showPopup("Please fill all the fields",Alert.AlertType.INFORMATION);
            return;
        }
        if(!pass.equals(passConfirm)){
            Main.showPopup("Passwords do not match",Alert.AlertType.ERROR);
            return;
        }


        Company newCompany = new Company(name, mail, phone, pass);
        if(Main.company != null) newCompany.setId(Main.company.getId());
        System.out.println("Company ID: " + newCompany.getId());
        System.out.println(newCompany);

        Command cmd = new Command(1,newCompany);
        Main.obj.writerPush(cmd);

        System.out.println("fucking thread");
        while(!Main.serverStatus){
            try{
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("fucked thread");



        if(Main.company == null){
            Main.showPopup("Company Already Exists",Alert.AlertType.INFORMATION);
            Main.serverStatus = false;
            regPass.clear();
            regConfirmPass.clear();
            regName.clear();
            regMail.clear();
            regPhone.clear();
            return;
        }

        System.out.println("successful");
        Main.showPopup("Successfully Created Company",Alert.AlertType.CONFIRMATION);
        regPass.clear();
        regConfirmPass.clear();
        regName.clear();
        regMail.clear();
        regPhone.clear();
        Main.serverStatus = false;
        Main.loginStatus = true;
        Stage stage = ((Stage)((Node) event.getSource()).getScene().getWindow());
        ProfilePage profilePage = new ProfilePage(stage);
        profilePage.show();
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

    public void initialize(){
        if(Main.loginStatus){
            loginProfile.setText(Main.company.getName());
            profileChange.setText("Change Profile");
            System.out.println(Main.company);
            regName.setText(Main.company.getName());
            regMail.setText(Main.company.getEmail());
            regPhone.setText(Main.company.getPhone());
            regName.setEditable(false);
        }
        else{
            loginProfile.setText("Login");
            regName.setEditable(true);
        }
    }

}
