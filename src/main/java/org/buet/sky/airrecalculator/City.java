package org.buet.sky.airrecalculator;


import java.io.Serializable;
import java.util.Objects;

import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;

public class City implements Serializable {
    private int id;
    private String name;
    private int x;
    private int y;


    private int oilCost;
    private int fillingSpeed;

    public int getOilCost() {
        return oilCost;
    }
    public int getFillingSpeed() {return fillingSpeed;}



    public City() {

    }

    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public City(int id, String name, int x, int y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public double dist(City city){
        int delx = x - city.getX();
        int dely = y - city.getY();
        return sqrt(delx * delx + dely * dely);
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y, oilCost);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof City)) return false;
        City city = (City)obj;
        return x == city.x && y == city.y &&  oilCost == city.oilCost;
    }


    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}