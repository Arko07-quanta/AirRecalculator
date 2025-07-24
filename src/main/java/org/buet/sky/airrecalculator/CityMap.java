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



        // make Command with command -1 and a list of which we want updated information
        List<Integer> require = new ArrayList<>(); require.add(6);

        Command cmd = new Command(-1, require);
        Main.obj.writerPush(cmd);

    }



    @FXML
    private void onAddCity() {
            String name = nameField.getText();
            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());


            City city = new City();
            city.setId(nextId++);
            city.setName(name);
            city.setX(x);
            city.setY(y);

            Main.obj.writerPush(new Command(7, city));

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
            int x = city.getX();
            int y = city.getY();
            gc.fillOval(x - 5, y - 5, 10, 10);
            gc.strokeText(city.getName(), x + 7, y);
        }
    }
}
