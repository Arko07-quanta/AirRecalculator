package org.buet.sky.airrecalculator;

import java.io.InputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DataBase {

    private static final String DB_URL;
    private static final Object DATABASE_LOCK = new Object();
    private static final int MAX_RETRY_ATTEMPTS = 5;
    private static final int RETRY_DELAY_MS = 100;

    static {
        InputStream input = DataBase.class.getResourceAsStream("/database/config.properties");
        Properties prop = new Properties();
        try {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String baseUrl = prop.getProperty("sqlite.url");
        DB_URL = baseUrl + "?busy_timeout=30000&journal_mode=WAL";
    }

    private static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA busy_timeout = 30000");
            stmt.execute("PRAGMA journal_mode = WAL");
            stmt.execute("PRAGMA synchronous = NORMAL");
            stmt.execute("PRAGMA cache_size = 10000");
            stmt.execute("PRAGMA temp_store = MEMORY");
        }
        return conn;
    }

    private static <T> T executeWithRetry(DatabaseOperation<T> operation) {
        for (int attempt = 0; attempt < MAX_RETRY_ATTEMPTS; attempt++) {
            try {
                synchronized (DATABASE_LOCK) {
                    return operation.execute();
                }
            } catch (SQLException e) {
                if (e.getErrorCode() == 5 && attempt < MAX_RETRY_ATTEMPTS - 1) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS * (attempt + 1));
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Operation interrupted", ie);
                    }
                } else {
                    System.out.println("Database error: " + e.getMessage());
                    if (operation.getDefaultValue() != null) {
                        return operation.getDefaultValue();
                    }
                    throw new RuntimeException(e);
                }
            }
        }
        return operation.getDefaultValue();
    }

    @FunctionalInterface
    private interface DatabaseOperation<T> {
        T execute() throws SQLException;
        default T getDefaultValue() { return null; }
    }

    public static List<City> getCity() {
        return executeWithRetry(new DatabaseOperation<List<City>>() {
            @Override
            public List<City> execute() throws SQLException {
                List<City> cities = new ArrayList<>();
                try (Connection conn = getConnection();
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
                }
                return cities;
            }

            @Override
            public List<City> getDefaultValue() {
                return new ArrayList<>();
            }
        });
    }

    public static List<AirPlane> getAirplane() {
        return getAirplane(-1);
    }

    public static List<AirPlane> getAirplane(int companyId) {
        return executeWithRetry(new DatabaseOperation<List<AirPlane>>() {
            @Override
            public List<AirPlane> execute() throws SQLException {
                List<AirPlane> airplanes = new ArrayList<>();
                try (Connection conn = getConnection();
                     Statement stmt = conn.createStatement()) {

                    ResultSet rs = (companyId == -1)
                            ? stmt.executeQuery("SELECT * FROM AirPlane ORDER BY departureTime")
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
                                rs.getInt("cost"),
                                rs.getLong("ticket"),
                                rs.getBoolean("timeEfficient")
                        );
                        airplanes.add(airplane);
                    }
                }
                return airplanes;
            }

            @Override
            public List<AirPlane> getDefaultValue() {
                return new ArrayList<>();
            }
        });
    }

    public static void addCity(City city) {
        executeWithRetry(new DatabaseOperation<Void>() {
            @Override
            public Void execute() throws SQLException {
                try (Connection conn = getConnection();
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO City (name, x, y, oilCost, fillingSpeed) VALUES (?, ?, ?, ?, ?)")
                ) {
                    stmt.setString(1, city.getName());
                    stmt.setDouble(2, city.getX());
                    stmt.setDouble(3, city.getY());
                    stmt.setDouble(4, city.getOilCost());
                    stmt.setDouble(5, city.getFillingSpeed());
                    stmt.executeUpdate();
                    Server.dataBaseListener.writerPush(new Command(6, null));
                }
                return null;
            }
        });
    }

    public static void addCompany(Company company) {
        executeWithRetry(new DatabaseOperation<Void>() {
            @Override
            public Void execute() throws SQLException {
                try (Connection conn = getConnection();
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO company (name, email, phone, password) VALUES (?, ?, ?, ?)")) {
                    stmt.setString(1, company.getName());
                    stmt.setString(2, company.getEmail());
                    stmt.setString(3, company.getPhone());
                    stmt.setString(4, company.getPassword());
                    stmt.executeUpdate();
                    Server.dataBaseListener.writerPush(new Command(1, null));
                }
                return null;
            }
        });
    }

    public static int getCompanyId(Company company) {
        return executeWithRetry(new DatabaseOperation<Integer>() {
            @Override
            public Integer execute() throws SQLException {
                try (Connection conn = getConnection();
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Company WHERE name = ? AND password = ?")) {
                    stmt.setString(1, company.getName());
                    stmt.setString(2, company.getPassword());
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        return rs.getInt("id");
                    }
                    return -1;
                }
            }

            @Override
            public Integer getDefaultValue() {
                return -1;
            }
        });
    }

    public static boolean verify(Company company) {
        return executeWithRetry(new DatabaseOperation<Boolean>() {
            @Override
            public Boolean execute() throws SQLException {
                try (Connection conn = getConnection();
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Company WHERE name = ? AND password = ?")) {
                    stmt.setString(1, company.getName());
                    stmt.setString(2, company.getPassword());
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next() && !rs.next()) {
                        company.setId(getCompanyId(company));
                        return true;
                    }
                    return false;
                }
            }

            @Override
            public Boolean getDefaultValue() {
                return false;
            }
        });
    }

    public static boolean verify(AirPlane airplane) {
        return executeWithRetry(new DatabaseOperation<Boolean>() {
            @Override
            public Boolean execute() throws SQLException {
                try (Connection conn = getConnection();
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AirPlane WHERE id = ?")) {
                    stmt.setInt(1, airplane.getId());
                    ResultSet rs = stmt.executeQuery();
                    return rs.next() == false;
                }
            }

            @Override
            public Boolean getDefaultValue() {
                return false;
            }
        });
    }

    public static Boolean validate(Company company) {
        return executeWithRetry(new DatabaseOperation<Boolean>() {
            @Override
            public Boolean execute() throws SQLException {
                try (Connection conn = getConnection();
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM company WHERE name = ?")) {
                    stmt.setString(1, company.getName());
                    ResultSet rs = stmt.executeQuery();
                    return !rs.next();
                }
            }

            @Override
            public Boolean getDefaultValue() {
                return false;
            }
        });
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
                rs.getInt("cost"),
                rs.getLong("ticket"),
                rs.getBoolean("timeEfficient")
        );
    }

    public static void addAirPlane(AirPlane airplane) {
        executeWithRetry(new DatabaseOperation<Void>() {
            @Override
            public Void execute() throws SQLException {
                System.out.println("adding airplane");
                System.out.println("getting called");
                if (verify(airplane) == false) {
                    System.out.println("modify");
                    System.out.println(airplane.getFlightTime());
                    modifyAirPlane(airplane);
                    return null;
                }

                String sql = "INSERT INTO AirPlane (name, fuelCapacity, speed, mileage, currentLocation, companyId, userRating, departureAirport, arrivalAirport, departureTime, flightTime, cost, ticket, timeEfficient) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (Connection conn = getConnection();
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
                    ps.setLong(13, airplane.getTicket());
                    ps.setBoolean(14, airplane.isTimeEfficient());

                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            airplane.setId(rs.getInt(1));
                        }
                    }
                    Server.dataBaseListener.writerPush(new Command(18, airplane.getCompanyId()));
                    Server.dataBaseListener.writerPush(new Command(9, null));
                }
                return null;
            }
        });
    }

    public static Company getCompanyById(int id) {
        return executeWithRetry(new DatabaseOperation<Company>() {
            @Override
            public Company execute() throws SQLException {
                String sql = "SELECT * FROM Company WHERE id = ?";
                try (Connection conn = getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();
                    rs.next();

                    return new Company(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("password")
                    );
                }
            }
        });
    }

    public static void modifyCompany(Company company) {
        executeWithRetry(new DatabaseOperation<Void>() {
            @Override
            public Void execute() throws SQLException {
                System.out.println("modifying company");
                String sql = "UPDATE Company SET  password = ? WHERE id = ?";
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, company.getPassword());
                    ps.setInt(2, company.getId());
                    ps.executeUpdate();
                    Server.dataBaseListener.writerPush(new Command(50, company));
                }
                return null;
            }
        });
    }

    public static void modifyTicket(AirPlane airplane) {
        executeWithRetry(new DatabaseOperation<Void>() {
            @Override
            public Void execute() throws SQLException {
                String sql = "UPDATE AirPlane SET  ticket = ? WHERE id = ?";
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setLong(1, airplane.getTicket());
                    ps.setInt(2, airplane.getId());
                    ps.executeUpdate();
                    Server.dataBaseListener.writerPush(new Command(9, airplane.getCompanyId()));
                }
                return null;
            }
        });
    }

    public static void modifyAirPlane(AirPlane airplane) {
        executeWithRetry(new DatabaseOperation<Void>() {
            @Override
            public Void execute() throws SQLException {
                String sql = "UPDATE AirPlane SET  departureTime = ?, arrivalAirport = ?, flightTime = ?, cost = ?, departureAirport = ?, ticket = ?, timeEfficient=? WHERE id = ?";
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, airplane.getDepartureTime());
                    ps.setInt(2, airplane.getArrivalAirport());
                    ps.setInt(3, airplane.getFlightTime());
                    ps.setInt(4, airplane.getCost());
                    ps.setInt(5, airplane.getDepartureAirport());
                    ps.setLong(6, airplane.getTicket());
                    ps.setBoolean(7, airplane.isTimeEfficient());
                    ps.setInt(8, airplane.getId());
                    ps.executeUpdate();
                    Server.dataBaseListener.writerPush(new Command(18, airplane.getCompanyId()));
                    Server.dataBaseListener.writerPush(new Command(9, null));
                }
                return null;
            }
        });
    }

    public static AirPlane getAirplaneById(int id) {
        return executeWithRetry(new DatabaseOperation<AirPlane>() {
            @Override
            public AirPlane execute() throws SQLException {
                String sql = "SELECT * FROM AirPlane WHERE id = ?";
                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return mapRowToAirPlane(rs);
                        }
                    }
                }
                return null;
            }
        });
    }

    public static void resetAllTables() {
        executeWithRetry(new DatabaseOperation<Void>() {
            @Override
            public Void execute() throws SQLException {
                try (Connection conn = getConnection();
                     Statement stmt = conn.createStatement()) {

                    stmt.execute("PRAGMA foreign_keys = OFF;");

                    String[] tables = {"City", "AirPlane", "Company", "Schedule"};

                    for (String table : tables) {
                        stmt.executeUpdate("DELETE FROM " + table);

                        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM sqlite_sequence WHERE name = ?")) {
                            ps.setString(1, table);
                            ps.executeUpdate();
                        }
                    }

                    stmt.execute("PRAGMA foreign_keys = ON;");

                    System.out.println("All tables have been reset.");
                }
                return null;
            }
        });
    }


    public static void main(String args[]) throws SQLException {
        resetAllTables();

        Random ran = new Random();

        for (int i = 0; i < 26; i++) {
            City city = new City(i, "" + i, ran.nextInt(0, 700), ran.nextInt(0, 500), ran.nextInt(8, 100), ran.nextInt(8, 100));
            addCity(city);
        }
    }
}