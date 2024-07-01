package com.ArtofWar44.dao;

import org.ArtofWar44.Dao.JdbcTransactionDAO;
import org.ArtofWar44.Model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcTransactionDaoTests extends BaseDaoTests {

    private JdbcTransactionDAO jdbcTransactionDao;

    @Before
    public void setup() {
        jdbcTransactionDao = new JdbcTransactionDAO(dataSource);
    }

    @Test
    public void getTransactionById_returns_correct_transaction_for_id() {
        Transaction transaction = jdbcTransactionDao.getTransactionById(1);
        Assert.assertNotNull("getTransactionById(1) returned null", transaction);
        Assert.assertEquals("getTransactionById(1) returned wrong customer ID", 1, transaction.getCustomerId());
        Assert.assertEquals("getTransactionById(1) returned wrong item ID", 1, transaction.getItemId());
        Assert.assertEquals("getTransactionById(1) returned wrong quantity", 1, transaction.getQuantity());
    }

    @Test
    public void getAllTransactions_returns_all_transactions() {
        List<Transaction> transactions = jdbcTransactionDao.getAllTransactions();
        Assert.assertNotNull("getAllTransactions returned null", transactions);
        Assert.assertFalse("getAllTransactions returned empty list", transactions.isEmpty());
    }

    @Test
    public void deleteTransaction_deletes_transaction() {
        jdbcTransactionDao.deleteTransaction(1);
        Transaction transaction = jdbcTransactionDao.getTransactionById(1);
        Assert.assertNull("deleteTransaction did not delete the transaction", transaction);
    }

    private void assertTransactionsMatch(String message, Transaction expected, Transaction actual) {
        Assert.assertEquals(message, expected.getTransactionId(), actual.getTransactionId());
        Assert.assertEquals(message, expected.getCustomerId(), actual.getCustomerId());
        Assert.assertEquals(message, expected.getItemId(), actual.getItemId());
        Assert.assertEquals(message, expected.getQuantity(), actual.getQuantity());
        Assert.assertEquals(message, expected.getTransactionDate(), actual.getTransactionDate());
    }
}

