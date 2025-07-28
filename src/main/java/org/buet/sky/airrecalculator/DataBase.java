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
            System.out.println("at least trying to get");

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
                        rs.getInt("departureTime"),
                        rs.getInt("flightTime"),
                        rs.getInt("cost")
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

    public synchronized static boolean verify(AirPlane airplane) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AirPlane WHERE id = ?")
        ) {
            stmt.setInt(1, airplane.getId());
            ResultSet rs = stmt.executeQuery();
            return rs.next() == false;

        } catch (SQLException e) {
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







        private static AirPlane mapRowToAirPlane(ResultSet rs) throws SQLException {
        return new AirPlane(
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
                rs.getInt("departureTime"),
                rs.getInt("flightTime"),
                rs.getInt("cost")
        );
    }






    public synchronized static void addAirPlane(AirPlane airplane) {
        if(verify(airplane) == false) {
            modifyAirPlane(airplane);
            return;
        }

        String sql = "INSERT INTO AirPlane (name, fuelCapacity, speed, mileage, currentLocation, companyId, userRating, departureAirport, arrivalAirport, departureTime, flightTime, cost) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, airplane.getName());
            ps.setInt(2, airplane.getFuelCapacity());
            ps.setDouble(3, airplane.getSpeed());
            ps.setDouble(4, airplane.getMileage());
            ps.setInt(5, airplane.getCurrentLocation());
            ps.setInt(6, airplane.getCompanyId());
            ps.setDouble(7, airplane.getUserRating());
            ps.setInt(8, airplane.getDepartureAirport());
            ps.setInt(9, airplane.getArrivalAirport());
            ps.setInt(10, airplane.getDepartureTime());
            ps.setInt(11, airplane.getFlightTime());
            ps.setInt(12, airplane.getCost());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    airplane.setId(rs.getInt(1));
                }
            }
            // notify listeners to refresh
            Server.dataBaseListener.writerPush(new Command(8, airplane.getCompanyId()));
            Server.dataBaseListener.writerPush(new Command(9, null));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void modifyAirPlane(AirPlane airplane) {
        String sql = "UPDATE AirPlane SET departureTime = ?, arrivalAirport = ?, flightTime = ?, cost = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, airplane.getDepartureTime());
            ps.setInt(2, airplane.getArrivalAirport());
            ps.setLong(3, airplane.getFlightTime());
            ps.setInt(4, airplane.getCost());
            ps.setInt(5, airplane.getId());
            ps.executeUpdate();
            // after modifying, push refresh
            Server.dataBaseListener.writerPush(new Command(8, airplane.getCompanyId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static AirPlane getAirplaneById(int id) {
        String sql = "SELECT * FROM AirPlane WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAirPlane(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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


    public static void main(String args[]) throws SQLException {
        resetAllTables();

        Random ran = new Random();

        for(int i=0;i < 26; i++){
            City city = new City(i, "" + i, ran.nextInt(0, 700), ran.nextInt(0, 500), ran.nextInt(8, 15), ran.nextInt(8, 15));
            addCity(city);
        }
    }
}
