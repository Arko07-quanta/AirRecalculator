package org.buet.sky.airrecalculator;

public class Request {
    private int flightId, time, capacity;
    private String companyName, arrival, departure;



    public void setFlightId(int flightId) {this.flightId = flightId;}
    public void setTime(int time) {this.time = time;}
    public void setCapacity(int capacity) {this.capacity = capacity;}


    public void setCompanyName(String companyName) {this.companyName = companyName;}
    public void setDeparture(String departure) {this.departure = departure;}
    public void setArrival(String arrival) {this.arrival = arrival;}
    public void print(){
        System.out.println("Flight ID: " + flightId);
        System.out.println("Time: " + time);
        System.out.println("Capacity: " + capacity);
        System.out.println("Company Name: " + companyName);
        System.out.println("Departure: " + departure);
        System.out.println("Arrival: " + arrival);
    }

}
