package org.buet.sky.airrecalculator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchedulePageController {
    @FXML public Button profileName;
    @FXML public VBox cardContainer;

    @FXML
    public void onMain(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        MainPage mainPage = new MainPage(stage);
        mainPage.show();
    }

    @FXML
    public void onLoginPage(ActionEvent event) throws IOException {
        if(!Main.loginStatus){
            Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            LoginPage loginPage = new LoginPage(stage);
            loginPage.show();
        }else{
            Stage stage =  ((Stage) ((Node) event.getSource()).getScene().getWindow());
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
    public void onRegistrationPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        RegistrationPage registrationPage = new RegistrationPage(stage);
        registrationPage.show();
    }

    @FXML
    public void initialize(){
        if(Main.loginStatus){
            profileName.setText(Main.company.getName());
        }
        else{
            profileName.setText("Login");
        }


        Main.controller.put(9,this);
        List<Integer> req = new ArrayList<>(); req.add(9);
        Main.obj.writerPush(new Command(-1,req));
        loadPlanes();
    }

    public void loadPlanes(){
        cardContainer.getChildren().clear(); // Clear existing cards

        System.out.println(Main.allPlaneList);
        if (Main.allPlaneList != null) {
            for (AirPlane p : Main.allPlaneList) {
                if(p.getFlightTime() == 0) continue;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("PlaneCard.fxml"));
                    Node card = loader.load();
                    PlaneCardController pc = loader.getController();

                    String id = nullToNA(Integer.toString(p.getId()));
                    String name = nullToNA(p.getName());
                    String fuel = p.getFuelCapacity() + " L";
                    String departureAirport = nullToNA(Integer.toString(p.getDepartureAirport()));
                    String destinationAirport = nullToNA(Integer.toString(p.getArrivalAirport()));
                    String departureTime = nullToNA(Integer.toString(p.getDepartureTime()));
                    String flightTime = nullToNA(Integer.toString(p.getFlightTime()));
                    int cst = p.getCost();
                    cst = (int) (cst*1.5);
                    cst = cst/50;
                    String cost = nullToNA(Integer.toString(cst));

                    pc.setPlaneData(
                            name,
                            id,
                            fuel,
                            departureAirport,
                            destinationAirport,
                            departureTime,
                            flightTime,
                            cost,
                            p
                    );
                    cardContainer.getChildren().add(card);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String nullToNA(String s) {
        return (s == null || s.isBlank()) ? "N/A" : s;
    }
}