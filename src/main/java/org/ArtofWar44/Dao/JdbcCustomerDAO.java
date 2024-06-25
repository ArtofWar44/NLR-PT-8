package org.ArtofWar44.Dao;

import org.ArtofWar44.Model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcCustomerDAO implements CustomerDAO {

    private final String CUSTOMER_SELECT = "SELECT customer_id, name, email, paw_points_balance FROM customers";
    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Customer getCustomerById(int id) {
        String sql = CUSTOMER_SELECT + " WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(sql, new CustomerRowMapper(), id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = CUSTOMER_SELECT;
        return jdbcTemplate.query(sql, new CustomerRowMapper());
    }

    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name, email, paw_points_balance) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getPawPointsBalance());
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, email = ?, paw_points_balance = ? WHERE customer_id = ?";
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getPawPointsBalance(), customer.getCustomerId());
    }

    @Override
    public void deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static final class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer();
            customer.setCustomerId(rs.getInt("customer_id"));
            customer.setName(rs.getString("name"));
            customer.setEmail(rs.getString("email"));
            customer.setPawPointsBalance(rs.getDouble("paw_points_balance"));
            return customer;
        }
    }
}
