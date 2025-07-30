package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPageController {
    @FXML public Button loginProfile;
    @FXML public Canvas mapCanvas;
    @FXML Button profileChange;

    @FXML
    public void onRegistrationPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        RegistrationPage registrationPage = new RegistrationPage(stage);
        registrationPage.show();
    }

    @FXML
    public void onLoginPage(ActionEvent event) throws IOException {
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        if(Main.loginStatus == false) {
            LoginPage loginPage = new LoginPage(stage);
            loginPage.show();
        }else {
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

        loadData();
    }
    public void loadData(){
        if(Main.loginStatus){
            loginProfile.setText(Main.company.getName());
            profileChange.setText("Change Profile");

        }
        else{
            loginProfile.setText("Login");
        }

        redrawMap(Main.cityList);

        List<City> cityList = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for(AirPlane airPlane:Main.allPlaneList){
            if(airPlane.getDepartureTime() == 0) continue;
            labels.add(airPlane.getName());
            cityList.add(Main.cityList.get(airPlane.getDepartureAirport()));
            cityList.add(Main.cityList.get(airPlane.getArrivalAirport()));
        }
        drawWithEdges(cityList, labels);

    }
    public void drawWithEdges(List<City> cities, List<String> edgeLabels) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1.5);
        gc.setFill(Color.LIGHTBLUE);
        int labelIndex = 0;

        for (int i = 0; i < cities.size() - 1; i += 2) {
            City c1 = cities.get(i);
            City c2 = cities.get(i + 1);
            double x1 = c1.getX(), y1 = c1.getY();
            double x2 = c2.getX(), y2 = c2.getY();

            gc.strokeLine(x1, y1, x2, y2);


            if (labelIndex < edgeLabels.size()) {
                String label = edgeLabels.get(labelIndex++);
                double midX = (x1 + x2) / 2;
                double midY = (y1 + y2) / 2;
                gc.fillText(label, midX + 5, midY - 5);
            }
        }
    }


    public void redrawMap(List<City> cities) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();

        gc.setFill(Color.web("#121212"));
        gc.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        for (City city : cities) {
            double x = city.getX();
            double y = city.getY();

            gc.setFill(Color.web("#ff4081")); // pinkish neon
            gc.fillOval(x - 8, y - 8, 16, 16);

            gc.setFill(Color.web("#e0e0e0")); // light gray text
            gc.fillText(city.getName(), x + 12, y + 4);
        }

        attachCityTooltips(cities);
    }

    private final Label hoverTooltip = new Label();



    private void attachCityTooltips(List<City> cities) {
        mapCanvas.setOnMouseMoved(e -> {
            double mx = e.getX(), my = e.getY();
            boolean hovering = false;

            for (City city : cities) {
                double cx = city.getX(), cy = city.getY();
                double dx = mx - cx, dy = my - cy;

                if (dx * dx + dy * dy <= 64) { // within radius^2 = 8^2
                    mapCanvas.setCursor(javafx.scene.Cursor.HAND);

                    Tooltip tp = new Tooltip("City: " + city.getName() +
                            "\nOil Cost: " + city.getOilCost() +
                            "\nFilling Speed: " + city.getFillingSpeed());
                    Tooltip.install(mapCanvas, tp);
                    return;
                }
            }

            Tooltip.uninstall(mapCanvas, null);
            mapCanvas.setCursor(javafx.scene.Cursor.DEFAULT);
        });
    }







}
