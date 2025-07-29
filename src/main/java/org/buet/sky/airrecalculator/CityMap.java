package org.buet.sky.airrecalculator;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
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

        List<Integer> require = new ArrayList<>();
        require.add(6);

        Command cmd = new Command(-1, require);
        Main.obj.writerPush(cmd);
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
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        gc.setFill(Color.RED);
        for (City city : cities) {
            double x = city.getX();
            double y = city.getY();
            gc.fillOval(x - 5, y - 5, 10, 10);
            gc.strokeText(city.getName() + " (" + city.getOilCost() + ", " + city.getFillingSpeed() + ")", x + 7, y);
        }
    }
}
