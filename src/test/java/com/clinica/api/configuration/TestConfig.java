package com.clinica.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {

    @Autowired
    private DataSource dataSource;

//    @PostConstruct
//    public void init() {
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
//        populator.execute(dataSource);
//    }
}
