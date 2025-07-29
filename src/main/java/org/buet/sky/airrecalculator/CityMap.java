package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.random;

public class CityMap {

    @FXML
    private TextField nameField, xField, yField, oilCostField, fillingSpeedField;

    @FXML
    private Canvas mapCanvas;

    private int nextId = 1;

    @FXML
    public void initialize() {
        Main.controller.put(30, this);
        hoverTooltip.setStyle("""
        -fx-background-color: rgba(0, 0, 0, 0.8);
        -fx-text-fill: white;
        -fx-padding: 5;
        -fx-background-radius: 6;
        -fx-font-size: 12;
    """);
        hoverTooltip.setVisible(false);

        // Add to parent Pane
        ((Pane) mapCanvas.getParent()).getChildren().add(hoverTooltip);
    }

    @FXML
    private void onAddCity() {
        try {
            String name = nameField.getText().trim();
            double x = Double.parseDouble(xField.getText());
            double y = Double.parseDouble(yField.getText());
            double oilCost = Double.parseDouble(oilCostField.getText());
            double fillingSpeed = Double.parseDouble(fillingSpeedField.getText());

//            City city = new City();
//            city.setId(nextId++);
//            city.setName(name);
//            city.setX(x);
//            city.setY(y);
//            city.setOilCost(oilCost);
//            city.setFillingSpeed(fillingSpeed);

            Random ran =  new Random();
            for(int i=0; i < 26; i++){
                City city = new City();
                city.setName("" + i);
                city.setX(ran.nextInt(700));
                city.setY(ran.nextInt(500));

                city.setFillingSpeed(ran.nextInt(8, 20));
                city.setOilCost(ran.nextInt(8, 20));
                Main.obj.writerPush(new Command(7, city));
            }

            //Main.obj.writerPush(new Command(7, city));

            nameField.clear();
            xField.clear();
            yField.clear();
            oilCostField.clear();
            fillingSpeedField.clear();

        } catch (NumberFormatException e) {
            System.err.println("Invalid number input: " + e.getMessage());
        }
    }


    public void drawWithEdges(List<City> cities) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();

//        gc.setFill(Color.WHITE);
  //      gc.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1.5);
        for (int i = 0; i < cities.size() - 1; i++) {
            City c1 = cities.get(i);
            City c2 = cities.get(i + 1);
            gc.strokeLine(c1.getX(), c1.getY(), c2.getX(), c2.getY());
        }

        for (City city : cities) {
            double x = city.getX();
            double y = city.getY();

            gc.setFill(Color.RED);
            gc.fillOval(x - 5, y - 5, 10, 10);

            gc.setFill(Color.BLACK);
            gc.strokeText(city.getName(), x + 7, y);

            if (city.getOilBought() > 0) {
                gc.setFill(Color.BLUE);
                gc.fillText("+" + (int) city.getOilBought(), x + 7, y - 10);
            }
        }
    }

    public void redrawMap(List<City> cities) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();

        // 🌌 Dark canvas background
        gc.setFill(Color.web("#121212"));
        gc.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        for (City city : cities) {
            double x = city.getX();
            double y = city.getY();

            // 🌕 City dot (soft glow)
            gc.setFill(Color.web("#ff4081")); // pinkish neon
            gc.fillOval(x - 8, y - 8, 16, 16);

            // 📝 City name
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
