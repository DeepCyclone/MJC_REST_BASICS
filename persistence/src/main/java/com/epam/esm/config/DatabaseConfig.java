package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${driver.class.name}")
    private String driverClassName;
    @Value("${db.url}")
    private String connectionURL;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Bean
    public DataSource hikariDataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/cerfiticatessystem");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }

}
