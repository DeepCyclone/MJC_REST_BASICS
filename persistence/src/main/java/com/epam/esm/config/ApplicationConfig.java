package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    private final DataSource dataSource;

    @Autowired
    public ApplicationConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcOperations jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);//JdbcOperations
    }

//    @Bean
//    public TransactionTemplate transactionTemplate(){
//        return new TransactionTemplate(dataSource);
//    }
}
