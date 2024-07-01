package com.ArtofWar44.dao;


/*
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestingDatabaseConfig.class)
public abstract class BaseDaoTests {

    @Autowired
    protected DataSource dataSource;

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }
 */

import org.junit.After;
import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDaoTests {

    protected static DataSource dataSource;
    protected JdbcTemplate jdbcTemplate;

    @Before
    public void setupDataSource() {
        dataSource = createDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @After
    public void closeDataSource() throws SQLException {
        if (dataSource != null) {
            try (Connection ignored = dataSource.getConnection()) {

            }
        }
    }

    private DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/PawPointsDB");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        return dataSource;
    }
}


/*



}
 */