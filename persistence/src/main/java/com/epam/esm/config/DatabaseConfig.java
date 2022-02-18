package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@PropertySource("classpath:db.properties")
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
    @Value("${db.maxPoolSize}")
    private int maxPoolSize;
    @Profile("prod")
    @Bean
    public DataSource hikariDataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl(connectionURL);
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }

}
