package org.ArtofWar44;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoyaltyProgram {
    private static final String INVENTORY_FILE = "/mnt/data/vendingmachine.csv";   //csv file goes here
    private static final int MAX_QUANTITY = 5;

    private Map<String, PawSomeVendingItem> inventory = new HashMap<>();
    private double pawPointsBalance = 20.00;

   // public static void main(String[] args) {
  //      VendingMachineCLI vendingMachineCLI = new VendingMachineCLI();
    //    vendingMachineCLI.restockInventory();
      //  vendingMachineCLI.run();
  //  }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Paw Points Vending Machine");
            System.out.println("1. Display Vending Machine Items");
            System.out.println("2. Purchase");
            System.out.println("3. Check Paw Points Balance");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                displayVendingMachineItems();
            } else if (choice == 2) {
                purchase(scanner);
            } else if (choice == 3) {
                checkPawPointsBalance();
            } else if (choice == 4) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private void displayVendingMachineItems() {
        System.out.println("Vending Machine Items:");
        for (Map.Entry<String, PawSomeVendingItem> entry : inventory.entrySet()) {
            PawSomeVendingItem item = entry.getValue();
            String status = item.getQuantity() > 0 ? item.getQuantity() + " remaining" : "SOLD OUT";
            System.out.println(entry.getKey() + ": " + item.getName() + " - $" + item.getPrice() + " (" + status + ")");
        }
        System.out.println();
    }

    private void purchase(Scanner scanner) {
        while (true) {
            System.out.println("Purchase Menu:");
            System.out.println("1. Feed Paw Points");
            System.out.println("2. Select Product");
            System.out.println("3. Finish Transaction");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                feedPawPoints(scanner);
            } else if (choice == 2) {
                selectProduct(scanner);
            } else if (choice == 3) {
                System.out.println("Transaction finished. Current Paw Points balance: $" + pawPointsBalance);
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println();
    }

    private void feedPawPoints(Scanner scanner) {
        System.out.print("Enter amount to feed: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        pawPointsBalance += amount;
        System.out.println("Current Paw Points Provided: $" + pawPointsBalance);
        System.out.println();
    }

    private void selectProduct(Scanner scanner) {
        System.out.print("Enter slot identifier: ");
        String slotId = scanner.nextLine().toUpperCase();
        PawSomeVendingItem item = inventory.get(slotId);

        if (item == null) {
            System.out.println("Invalid slot identifier. Please try again.");
        } else if (item.getQuantity() == 0) {
            System.out.println("Item is SOLD OUT. Please choose another item.");
        } else if (pawPointsBalance < item.getPrice()) {
            System.out.println("Insufficient Paw Points. Please feed more Paw Points.");
        } else {
            item.decreaseQuantity();
            pawPointsBalance -= item.getPrice();
            System.out.println("Dispensing " + item.getName() + ". Current Paw Points balance: $" + pawPointsBalance);
        }
        System.out.println();
    }

    private void checkPawPointsBalance() {
        System.out.println("Current Paw Points balance: $" + pawPointsBalance);
        System.out.println();
    }

    private void restockInventory() {
        try (Scanner fileScanner = new Scanner(new File(INVENTORY_FILE))) {
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(",");
                String slotId = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                inventory.put(slotId, new PawSomeVendingItem(name, price, MAX_QUANTITY));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Inventory file not found.");
        }
    }
}

