package org.ArtofWar44.Dao;


import org.ArtofWar44.Model.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getAllCustomers();
    Customer getCustomerById(int id);
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);
}