package org.ArtofWar44;


import java.util.Calendar;
import java.util.Date;

public class TestData {
    public static void initialize(LoyaltyProgram loyaltyProgram) {
        // Example customers
        Customer customer1 = new Customer("John", "Doe", new Date(1985, Calendar.MARCH, 15), "john.doe@example.com");
        customer1.addDog(new Dog("Buddy"));
        customer1.addDog(new Dog("Bella"));
        loyaltyProgram.addCustomer(customer1);

        Customer customer2 = new Customer("Jane", "Smith", new Date(1990, Calendar.JULY, 22), "jane.smith@example.com");
        customer2.addDog(new Dog("Charlie"));
        loyaltyProgram.addCustomer(customer2);

        // Add more customers as needed

        for (int i = 3; i <= 20; i++) {
            Customer customer = new Customer("Customer" + i, "LastName" + i, new Date(1980 + i, Calendar.JANUARY, i), "customer" + i + "@example.com");
            if (i % 2 == 0) {
                customer.addDog(new Dog("Dog" + i));
            } else {
                customer.addDog(new Dog("Dog" + i + "A"));
                customer.addDog(new Dog("Dog" + i + "B"));
            }
            loyaltyProgram.addCustomer(customer);
        }
    }
}
