    package org.buet.sky.airrecalculator;

    import java.io.Serializable;
    import java.util.Objects;

    import static java.lang.Math.sqrt;

    public class City implements Serializable {
    private int id;
    private String name;
    private double x;
    private double y;
    private double oilCost;
    private double fillingSpeed;

    public City() {}

    public City(String name, double x, double y, double oilCost, double fillingSpeed) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.oilCost = oilCost;
        this.fillingSpeed = fillingSpeed;
    }

    public City(int id, String name, double x, double y, double oilCost, double fillingSpeed) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.oilCost = oilCost;
        this.fillingSpeed = fillingSpeed;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getOilCost() { return oilCost; }
    public void setOilCost(double oilCost) { this.oilCost = oilCost; }

    public double getFillingSpeed() { return fillingSpeed; }
    public void setFillingSpeed(double fillingSpeed) { this.fillingSpeed = fillingSpeed; }



    public double dist(City city) {
        double dx = this.x - city.x;
        double dy = this.y - city.y;
        return sqrt(dx * dx + dy * dy);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof City)) return false;
        City other = (City) obj;
        return id == other.id &&
                Double.compare(x, other.x) == 0 &&
                Double.compare(y, other.y) == 0 &&
                Double.compare(oilCost, other.oilCost) == 0 &&
                Double.compare(fillingSpeed, other.fillingSpeed) == 0 &&
                Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, x, y, oilCost, fillingSpeed);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", oilCost=" + oilCost +
                ", fillingSpeed=" + fillingSpeed +
                '}';
    }
    }
