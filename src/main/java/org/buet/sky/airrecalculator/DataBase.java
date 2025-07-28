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
        String sql = "SELECT * FROM City";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cities.add(new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getDouble("oilCost"),
                        rs.getDouble("fillingSpeed")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public synchronized static List<AirPlane> getAirplane() {
        return getAirplane(-1);
    }

    public synchronized static List<AirPlane> getAirplane(int companyId) {
        List<AirPlane> airplanes = new ArrayList<>();
        String sql = (companyId < 0)
                ? "SELECT * FROM AirPlane"
                : "SELECT * FROM AirPlane WHERE companyId = " + companyId;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                airplanes.add(mapRowToAirPlane(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airplanes;
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

    public synchronized static void addCity(City city) {
        String sql = "INSERT INTO City (name, x, y, oilCost, fillingSpeed) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, city.getName());
            ps.setDouble(2, city.getX());
            ps.setDouble(3, city.getY());
            ps.setDouble(4, city.getOilCost());
            ps.setDouble(5, city.getFillingSpeed());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void addAirPlane(AirPlane airplane) {
        if (airplane.getId() > 0) {
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

    public synchronized static List<AirPlane> getSchedule() {
        List<AirPlane> schedule = new ArrayList<>();
        String sql = "SELECT * FROM Schedule WHERE start_time >= ? ORDER BY start_time";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    schedule.add(getAirplaneById(rs.getInt("air_plane_id")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    public synchronized static boolean verifyCompany(Company company) {
        String sql = "SELECT id FROM Company WHERE name = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, company.getName());
            ps.setString(2, company.getPassword());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized static boolean isCompanyNameAvailable(String name) {
        String sql = "SELECT id FROM Company WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return !rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized static void addCompany(Company company) {
        String sql = "INSERT INTO Company (name, email, phone, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, company.getName());
            ps.setString(2, company.getEmail());
            ps.setString(3, company.getPhone());
            ps.setString(4, company.getPassword());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) company.setId(rs.getInt(1));
            }
            Server.dataBaseListener.writerPush(new Command(1, company));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void resetAllTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = OFF");
            String[] tables = {"City","AirPlane","Company","Schedule"};
            for (String t : tables) {
                stmt.executeUpdate("DELETE FROM " + t);
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM sqlite_sequence WHERE name = ?")) {
                    ps.setString(1, t);
                    ps.executeUpdate();
                }
            }
            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void updateTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS AirPlane");
            String create =
                    "CREATE TABLE AirPlane (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT, " +
                            "fuelCapacity INTEGER, " +
                            "speed REAL, " +
                            "mileage REAL, " +
                            "currentLocation INTEGER, " +
                            "companyId INTEGER, " +
                            "userRating REAL, " +
                            "departureAirport INTEGER, " +
                            "arrivalAirport INTEGER, " +
                            "departureTime INTEGER, " +
                            "flightTime INTEGER, " +
                            "cost INTEGER" +
                            ")";
            stmt.executeUpdate(create);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
