package com.ArtofWar44.dao;

import org.ArtofWar44.Dao.JdbcCustomerDAO;
import org.ArtofWar44.Model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcCustomerDaoTests extends BaseDaoTests {

    private JdbcCustomerDAO jdbcCustomerDao;

    @Before
    public void setup() {
        jdbcCustomerDao = new JdbcCustomerDAO(dataSource);
    }

    @Test
    public void getCustomerById_returns_correct_customer_for_id() {
        Customer customer = jdbcCustomerDao.getCustomerById(1);
        Assert.assertNotNull("getCustomerById(1) returned null", customer);
        Assert.assertEquals("getCustomerById(1) returned wrong name", "Charles Edwards", customer.getName());
        Assert.assertEquals("getCustomerById(1) returned wrong email", "charles.edwards@example.com", customer.getEmail());
        Assert.assertEquals("getCustomerById(1) returned wrong paw points balance", 400.00, customer.getPawPointsBalance(), 0);
    }

    @Test
    public void getAllCustomers_returns_all_customers() {
        List<Customer> customers = jdbcCustomerDao.getAllCustomers();
        Assert.assertNotNull("getAllCustomers returned null", customers);
        Assert.assertFalse("getAllCustomers returned empty list", customers.isEmpty());
    }
}
