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
    private int oilBought;

    public City() {}

    public City(City city) {
        this.id = city.id;
        this.name = city.name;
        this.x = city.x;
        this.y = city.y;
        this.oilCost = city.oilCost;
        this.fillingSpeed = city.fillingSpeed;
        this.oilBought = city.oilBought;
    }


    public City(String name, double x, double y, double oilCost, double fillingSpeed) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.oilCost = oilCost;
        this.fillingSpeed = fillingSpeed;
        this.oilBought = 0;
    }


    public City(int id, String name, double x, double y, double oilCost, double fillingSpeed) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.oilCost = oilCost;
        this.fillingSpeed = fillingSpeed;
        this.oilBought = 0;
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



    public int getOilBought() { return oilBought; }
    public void setOilBought(int oilBought) { this.oilBought = oilBought; }
    public void buyOil(){ oilBought++;}




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

        boolean flag =  id == other.id;
        return flag;
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
                ", oilBought=" + oilBought +
                '}';
    }
    }
