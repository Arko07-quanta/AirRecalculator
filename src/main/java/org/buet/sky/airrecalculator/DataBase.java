package org.buet.sky.airrecalculator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class DataBase{

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



    public synchronized static List<City> getCity(){
        List<City> cityArrayList =  new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM city");
            while(rs.next()) {
                City city = new City();
                city.setId(rs.getInt("id"));
                city.setName(rs.getString("name"));
                city.setX(rs.getDouble("x"));
                city.setY(rs.getDouble("y"));
                cityArrayList.add(city);
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return cityArrayList;
    }


    public synchronized static List<AirPlane> getAirplane(){
        return getAirplane(-1);
    }


    public synchronized static List<AirPlane> getAirplane(int company_id){
        List<AirPlane> AirArrayList =  new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs;
            if(company_id == -1)
                rs = stmt.executeQuery("SELECT * FROM airplane");
            else rs = stmt.executeQuery("SELECT * FROM airplane WHERE company_id = " + company_id);

            while(rs.next()) {
                AirPlane airplane = new AirPlane(
                        rs.getString("name"),
                        rs.getInt("fuel_capacity"),
                        rs.getInt("current_location"),
                        rs.getInt("company_id")
                );
                AirArrayList.add(airplane);
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return AirArrayList;
    }




    public synchronized static void addAirPlane(AirPlane airplane){
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "INSERT INTO airplane (name, fuel_capacity, current_location, company_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, airplane.getName());
            stmt.setInt(2, airplane.getFuelCapacity());
            stmt.setInt(3, airplane.getCurrentLocation());
            stmt.setInt(4, airplane.getCompanyId());
            stmt.executeUpdate();
            Integer companyId = airplane.getCompanyId();


            Server.dataBaseListener.writerPush(new Command(8, companyId));
            Server.dataBaseListener.writerPush(new Command(9, null));

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public synchronized static void addCity(City city){
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "INSERT INTO city (name, x, y) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, city.getName());
            stmt.setDouble(2, city.getX());
            stmt.setDouble(3, city.getY());
            stmt.executeUpdate();
            System.out.println(city.getName() + " " + city.getX() + " " + city.getY());
            Server.dataBaseListener.writerPush(new Command(6, null));

        }catch (SQLException e){
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
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public synchronized static Boolean verify(Company company){
        try{
            Connection conn = DriverManager.getConnection(DB_URL);
            String sql = "SELECT * FROM company WHERE name = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getPassword());
            ResultSet rs = stmt.executeQuery();

            if(rs.next() == true && rs.next() == false) return true;
            return false;
        }catch(SQLException e){
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
            if(rs.next()) return false;
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}