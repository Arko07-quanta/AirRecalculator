package org.buet.sky.airrecalculator;

import java.util.*;

import static java.lang.Math.ceil;

public class Dijkstra {

    final int maxFuelCapacity;
    private AirPlane airplane;
    private City source, destination;
    private List<City> cities;


    Dijkstra(AirPlane airplane, City source, City destination, List<City> cities) {
        maxFuelCapacity = airplane.getFuelCapacity();
        this.airplane = airplane;
        this.source = source;
        this.destination = destination;
        this.cities = cities;
    }


    public List<Node> getMinTime() {
        List<Node> list;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        list = new ArrayList<>();

        pq.offer(new Node(source, 0, 0, null));


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
        return list;
    }


    public List<Node> getMinCost() {
        List<Node> list;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        list = new ArrayList<>();

        pq.offer(new Node(source, 0, 0, null));


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
        return list;
    }

}
