package org.ArtofWar44.Dao;

import org.ArtofWar44.Model.Employee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

public class JdbcEmployeeDAO implements EmployeeDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcEmployeeDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Employee getEmployeeByUsernameAndPassword(String username, String password) {
        Employee employee = null;
        String sql = "SELECT employee_id, username, password FROM employees WHERE username = ? AND password = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username, password);
            if (results.next()) {
                employee = mapRowToEmployee(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataAccessException e) {
            throw new DaoException("Error finding employee by username and password", e);
        }
        return employee;
    }

    private Employee mapRowToEmployee(SqlRowSet results) {
        Employee employee = new Employee();
        employee.setEmployeeId(results.getInt("employee_id"));
        employee.setUsername(results.getString("username"));
        employee.setPassword(results.getString("password"));
        return employee;
    }
}

