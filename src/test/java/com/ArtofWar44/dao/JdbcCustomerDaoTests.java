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
        super.setupDataSource();
        jdbcCustomerDao = new JdbcCustomerDAO(dataSource);
    }

    @Test
    public void getCustomerById_returns_correct_customer_for_id() {
        Customer customer = jdbcCustomerDao.getCustomerById(5);
        Assert.assertNotNull("getCustomerById(1) returned null", customer);
        Assert.assertEquals("getCustomerById(1) returned wrong name", "Catherine Williams", customer.getName());
        Assert.assertEquals("getCustomerById(1) returned wrong email", "catherine.williams@example.com", customer.getEmail());
        Assert.assertEquals("getCustomerById(1) returned wrong paw points balance", 25.00, customer.getPawPointsBalance(), 0);
    }

    @Test
    public void getAllCustomers_returns_all_customers() {
        List<Customer> customers = jdbcCustomerDao.getAllCustomers();
        Assert.assertNotNull("getAllCustomers returned null", customers);
        Assert.assertFalse("getAllCustomers returned empty list", customers.isEmpty());
    }

    @Test
    public void updateCustomer_updates_customer() {
        Customer customer = jdbcCustomerDao.getCustomerById(7);
        Assert.assertNotNull("Customer with ID 1 should not be null", customer);
        customer.setName("Updated Name");
        customer.setEmail("updated.email@example.com");
        customer.setPawPointsBalance(20.00);

        jdbcCustomerDao.updateCustomer(customer);

        Customer updatedCustomer = jdbcCustomerDao.getCustomerById(7);
        assertCustomersMatch("updateCustomer did not update the customer", customer, updatedCustomer);
    }


    @Test
    public void addCustomer_adds_customer() {
        Customer newCustomer = new Customer();
        newCustomer.setName("New Customer");
        newCustomer.setEmail("new.customer@example.com");
        newCustomer.setPawPointsBalance(50.00);

        jdbcCustomerDao.addCustomer(newCustomer);

        List<Customer> customers = jdbcCustomerDao.getAllCustomers();
        boolean found = false;
        for (Customer customer : customers) {
            if (customer.getEmail().equals("new.customer@example.com")) {
                found = true;
            }
        }

        Assert.assertTrue("addCustomer did not add the customer", found);


        for (Customer customer : customers) {
            if (customer.getEmail().equals("new.customer@example.com")) {
                jdbcCustomerDao.deleteCustomer(customer.getCustomerId()); // Clean up
            }
        }

    }
    @Test
    public void deleteCustomer_deletes_customer() {
        Customer deleteTestCustomer = new Customer();
        deleteTestCustomer.setName("Delete Test");
        deleteTestCustomer.setEmail("delete.test@example.com");
        deleteTestCustomer.setPawPointsBalance(60.00);
        jdbcCustomerDao.addCustomer(deleteTestCustomer);

        List<Customer> customersBeforeDelete = jdbcCustomerDao.getAllCustomers();

        Customer customerToDelete = null;
        for (Customer customer : customersBeforeDelete) {
            if (customer.getEmail().equals("delete.test@example.com")) {
                customerToDelete = customer;
            }
        }
        Assert.assertNotNull("Customer to delete should not be null", customerToDelete);
        jdbcCustomerDao.deleteCustomer(customerToDelete.getCustomerId());

        Customer deletedCustomer = jdbcCustomerDao.getCustomerById(customerToDelete.getCustomerId());
        Assert.assertNull("deleteCustomer did not delete the customer", deletedCustomer);
    }


    private void assertCustomersMatch(String message, Customer expected, Customer actual) {
        Assert.assertEquals(message, expected.getCustomerId(), actual.getCustomerId());
        Assert.assertEquals(message, expected.getName(), actual.getName());
        Assert.assertEquals(message, expected.getEmail(), actual.getEmail());
        Assert.assertEquals(message, expected.getPawPointsBalance(), actual.getPawPointsBalance(), 0);
    }

    }