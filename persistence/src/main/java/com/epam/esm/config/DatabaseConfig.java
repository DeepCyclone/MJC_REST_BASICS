package com.epam.esm.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("../../../../../resources/db.properties")
public class DatabaseConfig {

    @Profile("prod")
    @Bean
    public DataSource hikariDataSource(@Value("${driver.class.name}") String driverClassName,
                                       @Value("${db.url}") String connectionURL,
                                       @Value("${db.username}") String username,
                                       @Value("${db.password}") String password){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(connectionURL);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}
