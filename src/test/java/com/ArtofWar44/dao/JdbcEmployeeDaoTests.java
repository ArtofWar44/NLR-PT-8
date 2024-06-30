package com.ArtofWar44.dao;

import org.ArtofWar44.Dao.JdbcEmployeeDAO;
import org.ArtofWar44.Model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JdbcEmployeeDaoTests extends BaseDaoTests {

    private JdbcEmployeeDAO jdbcEmployeeDao;

    @Before
    public void setup() {
        jdbcEmployeeDao = new JdbcEmployeeDAO(dataSource);
    }

    @Test
    public void getEmployeeByUsernameAndPassword_returns_correct_employee_for_credentials() {
        Employee employee = jdbcEmployeeDao.getEmployeeByUsernameAndPassword("admin", "admin");
        Assert.assertNotNull("getEmployeeByUsernameAndPassword returned null", employee);
        Assert.assertEquals("admin", employee.getUsername());
        Assert.assertEquals("admin", employee.getPassword());
    }

    @Test
    public void getEmployeeByUsernameAndPassword_returns_null_for_invalid_credentials() {
        Employee employee = jdbcEmployeeDao.getEmployeeByUsernameAndPassword("invalid", "invalid");
        Assert.assertNull("getEmployeeByUsernameAndPassword should return null for invalid credentials", employee);
    }
}
