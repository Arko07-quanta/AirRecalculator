package org.buet.sky.airrecalculator;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static final String URL;

    static {
        try (InputStream in = DatabaseUtil.class
                .getResourceAsStream("/database/config.properties")) {
            Properties props = new Properties();
            props.load(in);
            URL = props.getProperty("sqlite.url");
            if (URL == null || URL.isEmpty()) {
                throw new IllegalStateException("Missing sqlite.url in config.properties");
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to load DB config: " + e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}