package org.ArtofWar44.Model;



public class Customer {
    private int customerId;
    private String name;
    private String email;
    private double pawPointsBalance;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPawPointsBalance() {
        return pawPointsBalance;
    }

    public void setPawPointsBalance(double pawPointsBalance) {
        this.pawPointsBalance = pawPointsBalance;
    }

    // to print customers in the Employee menu
    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + ", Email: " + email + ", Paw Points Balance: " + pawPointsBalance;
    }
}