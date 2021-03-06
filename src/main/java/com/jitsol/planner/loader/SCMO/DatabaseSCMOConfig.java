package com.jitsol.planner.loader.SCMO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseSCMOConfig {

    @Bean(name = "dsLoaderSCMO")
    @Primary
    @ConfigurationProperties(prefix="loader.scmo.db")
    public DataSource loaderDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcLoaderSCMO")
    @Autowired
    @Qualifier("dsLoaderSCMO")
    public JdbcTemplate loaderSCMOJdbcTemplate(DataSource dsSlave) {
        return new JdbcTemplate(dsSlave);
    }
}
