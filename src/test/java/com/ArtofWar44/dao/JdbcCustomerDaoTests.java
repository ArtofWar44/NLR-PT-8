package com.ArtofWar44.dao;

import org.ArtofWar44.Dao.JdbcCustomerDAO;
import org.ArtofWar44.Model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcCustomerDaoTests extends BaseDaoTests {

    private static final Customer CUSTOMER_1 = new Customer(1, "John Doe", "john.doe@example.com", 10.00);
    private static final Customer CUSTOMER_2 = new Customer(2, "Jane Smith", "jane.smith@example.com", 20.00);
    private static final Customer CUSTOMER_3 = new Customer(3, "Alice Johnson", "alice.johnson@example.com", 30.00);
    private static final Customer CUSTOMER_4 = new Customer(4, "Bob Brown", "bob.brown@example.com", 5.00);

    private JdbcCustomerDAO jdbcCustomerDao;

    @Before
    public void setup() {
        jdbcCustomerDao = new JdbcCustomerDAO(dataSource);
    }

    @Test
    public void getCustomerById_returns_correct_customer_for_id() {
        Customer customer = jdbcCustomerDao.getCustomerById(1);
        Assert.assertNotNull("getCustomerById(1) returned null", customer);
        assertCustomersMatch("getCustomerById(1) returned wrong or partial data", CUSTOMER_1, customer);

        customer = jdbcCustomerDao.getCustomerById(2);
        Assert.assertNotNull("getCustomerById(2) returned null", customer);
        assertCustomersMatch("getCustomerById(2) returned wrong or partial data", CUSTOMER_2, customer);

        customer = jdbcCustomerDao.getCustomerById(3);
        Assert.assertNotNull("getCustomerById(3) returned null", customer);
        assertCustomersMatch("getCustomerById(3) returned wrong or partial data", CUSTOMER_3, customer);

        customer = jdbcCustomerDao.getCustomerById(4);
        Assert.assertNotNull("getCustomerById(4) returned null", customer);
        assertCustomersMatch("getCustomerById(4) returned wrong or partial data", CUSTOMER_4, customer);

        customer = jdbcCustomerDao.getCustomerById(5);
        Assert.assertNull("getCustomerById(5) does not exist and should be null", customer);
    }

    @Test
    public void getAllCustomers_returns_all_customers() {
        List<Customer> customers = jdbcCustomerDao.getAllCustomers();
        Assert.assertEquals("getAllCustomers returned wrong number of customers", 4, customers.size());

        assertCustomersMatch("getAllCustomers returned wrong or partial data", CUSTOMER_1, customers.get(0));
        assertCustomersMatch("getAllCustomers returned wrong or partial data", CUSTOMER_2, customers.get(1));
        assertCustomersMatch("getAllCustomers returned wrong or partial data", CUSTOMER_3, customers.get(2));
        assertCustomersMatch("getAllCustomers returned wrong or partial data", CUSTOMER_4, customers.get(3));
    }

    @Test
    public void addCustomer_adds_customer() {
        Customer newCustomer = new Customer(5, "New Customer", "new.customer@example.com", 0.00);
        jdbcCustomerDao.addCustomer(newCustomer);

        Customer retrievedCustomer = jdbcCustomerDao.getCustomerById(5);
        Assert.assertNotNull("addCustomer did not add the customer", retrievedCustomer);
        assertCustomersMatch("addCustomer returned wrong or partial data", newCustomer, retrievedCustomer);
    }

    @Test
    public void updateCustomer_updates_customer() {
        Customer customer = jdbcCustomerDao.getCustomerById(1);
        customer.setName("Updated Name");
        customer.setEmail("updated.email@example.com");
        customer.setPawPointsBalance(50.00);

        jdbcCustomerDao.updateCustomer(customer);

        Customer updatedCustomer = jdbcCustomerDao.getCustomerById(1);
        assertCustomersMatch("updateCustomer did not update the customer", customer, updatedCustomer);
    }

    @Test
    public void deleteCustomer_deletes_customer() {
        jdbcCustomerDao.deleteCustomer(1);
        Customer customer = jdbcCustomerDao.getCustomerById(1);
        Assert.assertNull("deleteCustomer did not delete the customer", customer);
    }

    private void assertCustomersMatch(String message, Customer expected, Customer actual) {
        Assert.assertEquals(message, expected.getCustomerId(), actual.getCustomerId());
        Assert.assertEquals(message, expected.getName(), actual.getName());
        Assert.assertEquals(message, expected.getEmail(), actual.getEmail());
        Assert.assertEquals(message, expected.getPawPointsBalance(), actual.getPawPointsBalance(), 0.001);
    }
}
