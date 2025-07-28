package org.buet.sky.airrecalculator;

import java.util.*;

import static java.lang.Math.ceil;

public class Dijkstra implements Runnable {

    final int maxFuelCapacity;
    private AirPlane airplane;
    private City source, destination;
    private List<City> cities;
    private boolean time;

    Dijkstra(AirPlane airplane, City source, City destination, List<City> cities, boolean time) {
        maxFuelCapacity = airplane.getFuelCapacity();
        this.airplane = airplane;
        this.source = source;
        this.destination = destination;
        this.cities = cities;
        this.time = time;
        new Thread(this).start();
    }


    public List<City> format(List<Node> list) {
        List<City> lst =  new ArrayList<>();
        for (Node node : list) {
            if(lst.isEmpty() == false && node.city.equals(lst.get(lst.size() - 1))){
                lst.get(lst.size() - 1).buyOil();
            }else{
                lst.add(new City(node.city));
            }
        }
        return lst;
    }


    public List<City> getMinTime() {
        List<Node> list;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        list = new ArrayList<>();

        pq.offer(new Node(source, 0, 0.0, null));
        Node minNode = null;

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            if (node.city.equals(destination)) {
                minNode = node;
                break;
            }
            if(visited.contains(node)) continue;
            visited.add(node);
            if (node.fuel < maxFuelCapacity) {
                pq.offer(new Node(node.city, node.fuel + 1, node.cost + node.city.getFillingSpeed(), node));
            }

            for (City city : cities) {
                int newFuel = node.fuel - (int) ceil(city.dist(node.city) / airplane.getMileage());
                if (newFuel < 0) continue;
                int time = (int) ceil(city.dist(node.city) / airplane.getSpeed());

                Node newNode = new Node(city, newFuel, node.cost + time, node);
                if (!visited.contains(newNode)) {
                    pq.offer(newNode);
                }
            }
        }
        while (minNode != null) {
            list.add(minNode);
            minNode = minNode.prev;
        }
        Collections.reverse(list);
        return format(list);
    }


    public List<City> getMinCost() {
        List<Node> list;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        list = new ArrayList<>();

        pq.offer(new Node(source, 0, 0.0, null));


        Node minNode = null;

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            if (node.city.equals(destination)) {
                minNode = node;
                break;
            }
            if(visited.contains(node)) continue;
            visited.add(node);
            if (node.fuel < maxFuelCapacity) {
                pq.offer(new Node(node.city, node.fuel + 1, node.cost + node.city.getOilCost(), node));
            }

            for (City city : cities) {
                int newFuel = node.fuel - (int) ceil(city.dist(node.city) / airplane.getMileage());
                if (newFuel < 0) continue;
                Node newNode = new Node(city, newFuel, node.cost, node);
                if (!visited.contains(newNode)) {
                    pq.offer(newNode);
                }
            }
        }
        while (minNode != null) {
            list.add(minNode);
            minNode = minNode.prev;
        }
        Collections.reverse(list);
        return format(list);
    }

    public void run(){
        if(time){
            Main.obj.readerPush(new Command(15, getMinTime()));
        }else{
            Main.obj.readerPush(new Command(15, getMinCost()));
        }
    }
}
