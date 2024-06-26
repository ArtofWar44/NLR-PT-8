package org.ArtofWar44.Dao;

import org.ArtofWar44.Model.Employee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

public class JdbcEmployeeDAO implements EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;

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
        } catch (DataAccessException e) {
            throw new DaoException("Error finding employee by username and password", e);
        }

        return employee;
    }

    private Employee mapRowToEmployee(SqlRowSet rs) {
        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("employee_id"));
        employee.setUsername(rs.getString("username"));
        employee.setPassword(rs.getString("password"));
        return employee;
    }
}
