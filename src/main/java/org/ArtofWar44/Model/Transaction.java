package org.ArtofWar44.Model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int customerId;
    private int itemId;
    private int quantity;
    private Timestamp transactionDate;
    private String customerName;
    private String customerEmail;
    private String itemName;
    private double itemPrice;
    private String itemCategory;

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

}
//  When creating a new Transaction object -  constructors to initialize the object with specific values
/*
Transaction transaction1 = new Transaction();
Transaction transaction2 = new Transaction(1, Timestamp.valueOf("2024-01-01 10:00:00"));
Transaction transaction3 = new Transaction(1, 2, Timestamp.valueOf("2024-01-01 10:00:00"));
 */