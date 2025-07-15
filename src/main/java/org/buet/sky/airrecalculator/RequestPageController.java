package org.buet.sky.airrecalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;







public class RequestPageController {

    @FXML
    private ComboBox companyName;

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

    @FXML
    public void setCombo(ActionEvent event) throws IOException {
        String company = ((ComboBox) event.getSource()).getValue().toString();
        //request.setCompanyName(company);
    }



    @FXML
    public void initialize() {
        //request = new Request();

        URL url = getClass().getResource("/data/request.txt");
        try {
            assert url != null;
            Scanner sc = new Scanner(new File(url.toURI()));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
                companyName.getItems().add(line);
            }
        } catch (Exception e) {
            System.out.println("Error loading request file");
        }
    }




}
