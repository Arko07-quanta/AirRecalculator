package org.buet.sky.airrecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProfilePageController {
    @FXML public Label nameLabel;
    @FXML public Label idLabel;
    @FXML public Label planeCountLabel;
    @FXML public Button planeInfoButton;
    @FXML public Button addPlaneButton;
    @FXML public Button logoutButton;
    @FXML public Button profileName;
    private int cnt;

    @FXML
    public void onPlaneInfoClicked(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        PlaneInfo planeInfo = new PlaneInfo(stage);
        planeInfo.show();
        return;
    }

    @FXML
    public void onAddPlaneClicked(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        System.out.println("getting clicked");
        AddPlane addPlane = new AddPlane(stage);
        addPlane.show();
        return;
    }


    @FXML
    public void onLogoutClicked(ActionEvent event) throws IOException {
        Main.loginStatus = false;
        Main.company = null;
        if(Main.airPlaneList != null)Main.airPlaneList.clear();
        if(Main.controller != null)Main.controller.clear();
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        LoginPage loginPage = new LoginPage(stage);
        loginPage.show();
        return;
    }

    @FXML
    public void initialize() {
        Main.controller.put(15,this);
        List<Integer> req = new ArrayList<>(); req.add(18);
        Main.obj.writerPush(new Command(-1,req));
        profileName.setText(Main.company.getName());
        nameLabel.setText(Main.company.getName());
        idLabel.setText(Integer.toString(Main.company.getId()));
        if(Main.airPlaneList != null) cnt = Main.airPlaneList.size();
        else cnt = 0;
        planeCountLabel.setText(Integer.toString(cnt));
        return;
    }

    @FXML
    public void onMain(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        MainPage mainPage = new MainPage(stage);
        mainPage.show();
    }

    @FXML
    public void onSchedulePage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        SchedulePage schedulePage = new SchedulePage(stage);
        schedulePage.show();
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
}
