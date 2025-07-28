package org.buet.sky.airrecalculator;

import java.io.Serializable;

public class AirPlane implements Serializable {
    private int id;
    private String name;
    private int fuelCapacity;
    private double speed;
    private double mileage;
    private int currentLocation;
    private int companyId;
    private double userRating;
    private int departureAirport;
    private int arrivalAirport;
    private int departureTime;
    private int flightTime;
    private int cost;

    public AirPlane() {}


    public AirPlane(String name, int fuelCapacity, double speed, int currentLocation, int companyId) {
        this.name = name;
        this.fuelCapacity = fuelCapacity;
        this.currentLocation = currentLocation;
        this.companyId = companyId;
        this.speed = speed;
    }

    public AirPlane(int id,
                    String name,
                    int fuelCapacity,
                    double speed,
                    double mileage,
                    int currentLocation,
                    int companyId,
                    double userRating,
                    int departureAirport,
                    int arrivalAirport,
                    int departureTime,
                    int flightTime,
                    int cost) {
        this.id = id;
        this.name = name;
        this.fuelCapacity = fuelCapacity;
        this.speed = speed;
        this.mileage = mileage;
        this.currentLocation = currentLocation;
        this.companyId = companyId;
        this.userRating = userRating;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.flightTime = flightTime;
        this.cost = cost;
    }









    // --- Getters/Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getFuelCapacity() { return fuelCapacity; }
    public void setFuelCapacity(int fuelCapacity) { this.fuelCapacity = fuelCapacity; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    public double getMileage() { return mileage; }
    public void setMileage(double mileage) { this.mileage = mileage; }

    public int getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(int currentLocation) { this.currentLocation = currentLocation; }

    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }

    public double getUserRating() { return userRating; }
    public void setUserRating(double userRating) { this.userRating = userRating; }

    public int getDepartureAirport() { return departureAirport; }
    public void setDepartureAirport(int departureAirport) { this.departureAirport = departureAirport; }

    public int getArrivalAirport() { return arrivalAirport; }
    public void setArrivalAirport(int arrivalAirport) { this.arrivalAirport = arrivalAirport; }

    public int getDepartureTime() { return departureTime; }
    public void setDepartureTime(int departureTime) { this.departureTime = departureTime; }

    public int getFlightTime() { return flightTime; }
    public void setFlightTime(int flightTime) { this.flightTime = flightTime; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    // Legacy confusing methods
    public int getArrivalTime() { return departureTime; } // kept for backward compatibility
    public void setArrivalTime(int arrivalTime) { this.departureTime = arrivalTime; } // legacy

    @Override
    public String toString() {
        return "AirPlane{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fuelCapacity=" + fuelCapacity +
                ", speed=" + speed +
                ", mileage=" + mileage +
                ", currentLocation=" + currentLocation +
                ", companyId=" + companyId +
                ", userRating=" + userRating +
                ", departureAirport=" + departureAirport +
                ", arrivalAirport=" + arrivalAirport +
                ", departureTime=" + departureTime +
                ", flightTime=" + flightTime +
                ", cost=" + cost +
                '}';
    }
}
