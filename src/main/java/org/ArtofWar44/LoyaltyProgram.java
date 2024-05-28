package org.ArtofWar44;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoyaltyProgram {
    private Map<String, Customer> customers;
    private Scanner scanner;

    public LoyaltyProgram() {
        customers = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("Welcome to Gray Skies Cafe Paw Points!");
            System.out.println("1. Add Purchase");
            System.out.println("2. Check Loyalty Status");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                addPurchase();
            } else if (choice == 2) {
                checkLoyaltyStatus();
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                return;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addPurchase() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        Customer customer = customers.get(name);
        if (customer != null) {
            customer.addPurchase();
            customers.put(name, customer);
            System.out.println("Purchase added for " + name);
        } else {
            System.out.println("Customer not found.");
        }
    }

    private void checkLoyaltyStatus() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        Customer customer = customers.get(name);

        if (customer == null) {
            System.out.println("No records found for " + name);
        } else {
            String message = customer.getFirstName() + " " + customer.getLastName() + " has made " + customer.getPurchases() + " purchases.";
            if (customer.isEligibleForReward()) {
                message += " Congratulations! You have earned a free select menu item for your dog!";
            }
            System.out.println(message);
        }
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getFirstName() + " " + customer.getLastName(), customer);
    }
}
