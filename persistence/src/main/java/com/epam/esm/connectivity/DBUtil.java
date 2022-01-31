package com.epam.esm.connectivity;

import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.util.Properties;

public class DBUtil {
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_URL = "db.url";
    public static final String DB_DRIVER_CLASS = "driver.class.name";

    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(HikariDataSource dataSource) {
        DBUtil.dataSource = dataSource;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        DBUtil.properties = properties;
    }

    private static HikariDataSource dataSource;
    private static Properties properties;
    static {
        try{
            properties = new Properties();
            properties.load(new FileInputStream("db.properties"));
            dataSource = new HikariDataSource();
            dataSource.setDriverClassName(properties.getProperty(DB_DRIVER_CLASS));
            dataSource.setJdbcUrl(properties.getProperty(DB_URL));
            dataSource.setUsername(properties.getProperty(DB_USERNAME));
            dataSource.setPassword(properties.getProperty(DB_PASSWORD));
        }
        catch (Exception e){

        }
    }
}
