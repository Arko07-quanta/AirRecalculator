package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CityMap {

    @FXML
    private TextField nameField, xField, yField;

    @FXML
    private Canvas mapCanvas;

    private int nextId = 1;



    @FXML
    public void initialize() {
        // adding this controller object to the controller hashmap
        Main.controller.put(6, this);


        // which information we want always updated, list of them
        List<Integer> nd = new ArrayList<>();
        nd.add(6);

        // writing command -1 which is for updated infromation
        Main.obj.writerPush(-1);
        Main.obj.writerPush(nd);
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

            Main.network.write(1);
            Main.network.write(city);



            nameField.clear();
            xField.clear();
            yField.clear();


    }


    public void redrawMap(List<City> cities) {
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
