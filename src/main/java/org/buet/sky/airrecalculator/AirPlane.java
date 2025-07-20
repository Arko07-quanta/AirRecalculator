package org.buet.sky.airrecalculator;



public class AirPlane {
    private int id;
    private String name;
    private int fuelCapacity;
    private int currentLocation;
    private int companyId;

    public AirPlane() {
    }

    public AirPlane(String name, int fuelCapacity, int currentLocation, int companyId) {
        this.name = name;
        this.fuelCapacity = fuelCapacity;
        this.currentLocation = currentLocation;
        this.companyId = companyId;
    }

    public AirPlane(int id, String name, int fuelCapacity, int currentLocation, int companyId) {
        this.id = id;
        this.name = name;
        this.fuelCapacity = fuelCapacity;
        this.currentLocation = currentLocation;
        this.companyId = companyId;
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

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(int currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fuelCapacity=" + fuelCapacity +
                ", currentLocation=" + currentLocation +
                ", companyId=" + companyId +
                '}';
    }
}