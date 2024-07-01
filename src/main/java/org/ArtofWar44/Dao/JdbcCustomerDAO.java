package org.ArtofWar44.Dao;

import org.ArtofWar44.Model.Customer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCustomerDAO implements CustomerDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcCustomerDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Customer getCustomerById(int customerId) {
        Customer customer = null;
        String sql = "SELECT customer_id, name, email, paw_points_balance FROM customers WHERE customer_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, customerId);
            if (results.next()) {
                customer = mapRowToCustomer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        }
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, name, email, paw_points_balance FROM customers ORDER BY name";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Customer customer = mapRowToCustomer(results);
                customers.add(customer);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        }
        return customers;
    }

    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name, email, paw_points_balance) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getPawPointsBalance());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataAccessException e) {
            throw new DaoException("Unable to add customer", e);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, email = ?, paw_points_balance = ? WHERE customer_id = ?";
        try {
            jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getPawPointsBalance(), customer.getCustomerId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataAccessException e) {
            throw new DaoException("Unable to update customer", e);
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try {
            jdbcTemplate.update(sql, customerId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataAccessException e) {
            throw new DaoException("Unable to delete customer", e);
        }
    }

    private Customer mapRowToCustomer(SqlRowSet results) {
        Customer customer = new Customer();
        customer.setCustomerId(results.getInt("customer_id"));
        customer.setName(results.getString("name"));
        customer.setEmail(results.getString("email"));
        customer.setPawPointsBalance(results.getDouble("paw_points_balance"));
        return customer;
    }
}
