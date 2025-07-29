package org.buet.sky.airrecalculator;

import javafx.scene.Scene;

import java.util.Objects;

public class Node{
    City city;
    Integer fuel;
    double cost, time;
    Node prev;
    Node(City city, Integer fuel, Double cost,  Double time, Node prev){
        this.city = city;
        this.fuel = fuel;
        this.cost = cost;
        this.prev = prev;
        this.time = time;
    }



    public int hashCode(){
        return Objects.hash(city, fuel);
    }
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof Node)) return false;
        Node n = (Node)o;
        return city.equals(n.city) && fuel == n.fuel;
    }

}
