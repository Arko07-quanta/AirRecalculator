package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class CityMap {

    @FXML
    private TextField nameField, xField, yField;

    @FXML
    private Canvas mapCanvas;

    private List<City> cities = new ArrayList<>();
    private int nextId = 1;

    @FXML
    public void initialize() {
        redrawMap();
    }

    @FXML
    private void onAddCity() {
            String name = nameField.getText();
            double x = Double.parseDouble(xField.getText());
            double y = Double.parseDouble(yField.getText());

            City city = new City();
            city.setId(nextId++);
            city.setName(name);
            city.setX(x);
            city.setY(y);

            DataBase.addCity(city);
            redrawMap();

            nameField.clear();
            xField.clear();
            yField.clear();


    }

    private void redrawMap() {
        cities = DataBase.getCity();
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        gc.setFill(Color.RED);
        for (City city : cities) {
            double x = city.getX();
            double y = city.getY();
            gc.fillOval(x - 5, y - 5, 10, 10);
            gc.strokeText(city.getName(), x + 7, y);
        }
    }
}
