package org.ArtofWar44.Dao;

import org.ArtofWar44.Model.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionDAO implements TransactionDAO {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransactionDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.transaction_id, t.customer_id, t.item_id, t.quantity, t.transaction_date, " +
                "c.name AS customer_name, c.email AS customer_email, " +
                "i.name AS item_name, i.price AS item_price, i.category AS item_category " +
                "FROM transactions t " +
                "INNER JOIN customers c ON t.customer_id = c.customer_id " +
                "INNER JOIN items i ON t.item_id = i.item_id";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Transaction transaction = mapRowToTransaction(results);
                transactions.add(transaction);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataAccessException e) {
            throw new DaoException("Error getting all transactions", e);
        }

        return transactions;
    }

    @Override
    public Transaction getTransactionById(int id) {
        Transaction transaction = null;
        String sql = "SELECT t.transaction_id, t.customer_id, t.item_id, t.quantity, t.transaction_date, " +
                "c.name AS customer_name, c.email AS customer_email, " +
                "i.name AS item_name, i.price AS item_price, i.category AS item_category " +
                "FROM transactions t " +
                "INNER JOIN customers c ON t.customer_id = c.customer_id " +
                "INNER JOIN items i ON t.item_id = i.item_id " +
                "WHERE t.transaction_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                transaction = mapRowToTransaction(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database");
        } catch (DataAccessException e) {
            throw new DaoException("Error getting transaction by id", e);
        }

        return transaction;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (customer_id, item_id, quantity, transaction_date) VALUES (?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, transaction.getCustomerId(), transaction.getItemId(), transaction.getQuantity(), transaction.getTransactionDate());
        } catch (DataAccessException e) {
            throw new DaoException("Error adding transaction", e);
        }
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET customer_id = ?, item_id = ?, quantity = ?, transaction_date = ? WHERE transaction_id = ?";

        try {
            jdbcTemplate.update(sql, transaction.getCustomerId(), transaction.getItemId(), transaction.getQuantity(), transaction.getTransactionDate(), transaction.getTransactionId());
        } catch (DataAccessException e) {
            throw new DaoException("Error updating transaction", e);
        }
    }

    @Override
    public void deleteTransaction(int id) {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";

        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new DaoException("Error deleting transaction", e);
        }
    }

    private Transaction mapRowToTransaction(SqlRowSet rs) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getInt("transaction_id"));
        transaction.setCustomerId(rs.getInt("customer_id"));
        transaction.setItemId(rs.getInt("item_id"));
        transaction.setQuantity(rs.getInt("quantity"));
        transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
        transaction.setCustomerName(rs.getString("customer_name"));
        transaction.setCustomerEmail(rs.getString("customer_email"));
        transaction.setItemName(rs.getString("item_name"));
        transaction.setItemPrice(rs.getDouble("item_price"));
        transaction.setItemCategory(rs.getString("item_category"));
        return transaction;
    }
}

