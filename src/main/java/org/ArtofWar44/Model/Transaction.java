package org.ArtofWar44.Model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int customerId;
    private int itemId;
    private int quantity;
    private Timestamp transactionDate;


    public Transaction() {
    }


    public Transaction(int transactionId, Timestamp transactionDate) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;

    }
    public Transaction(int customerId, int itemId, Timestamp transactionDate) {
        this.customerId = customerId;
        this.itemId = itemId;
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }
}
//  When creating a new Transaction object use these constructors to initialize the object with specific values
/*
Transaction transaction1 = new Transaction();
Transaction transaction2 = new Transaction(1, Timestamp.valueOf("2024-01-01 10:00:00"));
Transaction transaction3 = new Transaction(1, 2, Timestamp.valueOf("2024-01-01 10:00:00"));
 */