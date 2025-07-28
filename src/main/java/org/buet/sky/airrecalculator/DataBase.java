package org.buet.sky.airrecalculator;

import java.io.InputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Properties;

public class DataBase {

    private static final String DB_URL;

    static {
        InputStream input = DataBase.class.getResourceAsStream("/database/config.properties");
        Properties prop = new Properties();
        try {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DB_URL = prop.getProperty("sqlite.url");
    }

    public synchronized static List<City> getCity() {
        List<City> cities = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM City")) {

            while (rs.next()) {
                City city = new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getDouble("oilCost"),
                        rs.getDouble("fillingSpeed")
                );
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cities;
    }

    public synchronized static List<AirPlane> getAirplane() {
        return getAirplane(-1);
    }

    public synchronized static List<AirPlane> getAirplane(int companyId) {
        List<AirPlane> airplanes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = (companyId == -1)
                    ? stmt.executeQuery("SELECT * FROM AirPlane")
                    : stmt.executeQuery("SELECT * FROM AirPlane WHERE companyId = " + companyId);

            while (rs.next()) {
                AirPlane airplane = new AirPlane(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("fuelCapacity"),
                        rs.getDouble("speed"),
                        rs.getDouble("mileage"),
                        rs.getInt("currentLocation"),
                        rs.getInt("companyId"),
                        rs.getDouble("userRating"),
                        rs.getInt("departureAirport"),
                        rs.getInt("arrivalAirport"),
                        rs.getInt("arrivalTime")
                );
                airplanes.add(airplane);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return airplanes;
    }

    public synchronized static void addCity(City city) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO City (name, x, y, oilCost, fillingSpeed) VALUES (?, ?, ?, ?, ?)")
        ) {
            stmt.setString(1, city.getName());
            stmt.setDouble(2, city.getX());
            stmt.setDouble(3, city.getY());
            stmt.setDouble(4, city.getOilCost());
            stmt.setDouble(5, city.getFillingSpeed());
            stmt.executeUpdate();
            //Server.dataBaseListener.writerPush(new Command(6, null));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized static void addAirPlane(AirPlane airplane) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO AirPlane (name, fuelCapacity, speed, mileage, currentLocation, companyId, userRating, departureAirport, arrivalAirport, arrivalTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
        ) {
            stmt.setString(1, airplane.getName());
            stmt.setInt(2, airplane.getFuelCapacity());
            stmt.setDouble(3, airplane.getSpeed());
            stmt.setDouble(4, airplane.getMileage());
            stmt.setInt(5, airplane.getCurrentLocation());
            stmt.setInt(6, airplane.getCompanyId());
            stmt.setDouble(7, airplane.getUserRating());
            stmt.setInt(8, airplane.getDepartureAirport());
            stmt.setInt(9, airplane.getArrivalAirport());
            stmt.setInt(10, airplane.getArrivalTime());
            stmt.executeUpdate();

            Server.dataBaseListener.writerPush(new Command(8, airplane.getCompanyId()));
            Server.dataBaseListener.writerPush(new Command(9, null));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public synchronized static void addCompany(Company company){
        try {

            System.out.println("adding");
            System.out.println(company);
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "INSERT INTO company (name, email, phone, password) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getEmail());
            stmt.setString(3, company.getPhone());
            stmt.setString(4, company.getPassword());
            stmt.executeUpdate();
            
            Server.dataBaseListener.writerPush(new Command(1, null));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized static boolean verify(Company company) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Company WHERE name = ? AND password = ?")
        ) {
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getPassword());
            ResultSet rs = stmt.executeQuery();
            return rs.next() && !rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    

    public synchronized static Boolean validate(Company company){
        try{
            System.out.println("Validating");
            System.out.println(company);



            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "SELECT * FROM company WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, company.getName());
            ResultSet rs = stmt.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public synchronized static AirPlane getAirplaneById(Integer airplaneId) {
        try(Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AirPlane WHERE id = ?")
        ){
            stmt.setInt(1, airplaneId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                AirPlane airplane = new AirPlane(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("fuelCapacity"),
                        rs.getDouble("speed"),
                        rs.getDouble("mileage"),
                        rs.getInt("currentLocation"),
                        rs.getInt("companyId"),
                        rs.getDouble("userRating"),
                        rs.getInt("departureAirport"),
                        rs.getInt("arrivalAirport"),
                        rs.getInt("arrivalTime")
                );
                return airplane;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }



    // have to repair this shit and have to addSchedule function

    public synchronized static List<AirPlane> getSchedule(){
        List<AirPlane> airplanes = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Schedule WHERE start_time >= ? ORDER BY start_time DESC");
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                AirPlane airplane = getAirplaneById(rs.getInt("air_plane_id"));
                airplanes.add(airplane);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return airplanes;
    }


    public synchronized static void resetAllTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = OFF;");

            String[] tables = {"City", "AirPlane", "Company", "Schedule"};

            for (String table : tables) {
                // Clear data
                stmt.executeUpdate("DELETE FROM " + table);

                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM sqlite_sequence WHERE name = ?")) {
                    ps.setString(1, table);
                    ps.executeUpdate();
                }
            }

            stmt.execute("PRAGMA foreign_keys = ON;");

            System.out.println("All tables have been reset.");
//
//            Server.dataBaseListener.writerPush(new Command(6, null));
//            Server.dataBaseListener.writerPush(new Command(9, null));
//            Server.dataBaseListener.writerPush(new Command(1, null));

        } catch (SQLException e) {
            System.out.println("Error resetting tables: " + e.getMessage());
        }
    }


    public static void main(String args[]){
        resetAllTables();

        Random ran = new Random();

        for(int i=0;i < 26; i++){
            City city = new City(i, "" + i, ran.nextInt(0, 700), ran.nextInt(0, 500), ran.nextInt(8, 15), ran.nextInt(8, 15));
            addCity(city);
        }
    }
}
