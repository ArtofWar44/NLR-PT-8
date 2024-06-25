package org.ArtofWar44;

import org.ArtofWar44.Dao.CustomerDAO;
import org.ArtofWar44.Dao.ItemDAO;
import org.ArtofWar44.Dao.JdbcCustomerDAO;
import org.ArtofWar44.Dao.JdbcItemDAO;
import org.ArtofWar44.Model.Item;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoyaltyProgram {
    private static final int MAX_QUANTITY = 5;
    private Map<String, Item> inventory = new HashMap<>();
    private double pawPointsBalance = 20.00;
    private final CustomerDAO customerDAO;
    private final ItemDAO itemDAO;

    public LoyaltyProgram(CustomerDAO customerDAO, ItemDAO itemDAO) {
        this.customerDAO = customerDAO;
        this.itemDAO = itemDAO;
        restockInventory();
    }

    public static void main(String[] args) {
        DataSource dataSource = createDataSource();
        CustomerDAO customerDAO = new JdbcCustomerDAO(dataSource);
        ItemDAO itemDAO = new JdbcItemDAO(dataSource);

        LoyaltyProgram loyaltyProgram = new LoyaltyProgram(customerDAO, itemDAO);
        loyaltyProgram.run();
    }

    private static DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/PawPointsDB");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        return dataSource;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("PawSome Vending");
            System.out.println("1. Display Doggy Vending Machine Items");
            System.out.println("2. Purchase");
            System.out.println("3. Check PawSome Points Balance");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

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
        System.out.println("PawSome Vending Items:");
        for (Map.Entry<String, Item> entry : inventory.entrySet()) {
            Item item = entry.getValue();
            String status = item.getQuantity() > 0 ? item.getQuantity() + " remaining" : "SOLD OUT";
            System.out.println(entry.getKey() + ": " + item.getName() + " - $" + item.getPrice() + " (" + status + ")");
        }
        System.out.println();
    }

    private void purchase(Scanner scanner) {
        while (true) {
            System.out.println("Purchase Menu:");
            System.out.println("1. Use Paw Points");
            System.out.println("2. Select Rewards Item");
            System.out.println("3. Finish Transaction");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

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
        System.out.print("Enter amount to redeem: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        pawPointsBalance += amount;
        System.out.println("Current Paw Points Provided: $" + pawPointsBalance);
        System.out.println();
    }

    private void selectProduct(Scanner scanner) {
        System.out.print("Enter slot identifier: ");
        String slotId = scanner.nextLine().toUpperCase();
        Item item = inventory.get(slotId);

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
        inventory.put("A1", new Item("Squeaky Ball", 6.00, Item.Category.DOG_TOY, MAX_QUANTITY));
        inventory.put("A2", new Item("Rope Tug Toy", 8.00, Item.Category.DOG_TOY, MAX_QUANTITY));
        inventory.put("A3", new Item("Plush Chew Toy", 10.00, Item.Category.DOG_TOY, MAX_QUANTITY));
        inventory.put("A4", new Item("Rubber Chew Toy", 10.00, Item.Category.DOG_TOY, MAX_QUANTITY));
        inventory.put("B1", new Item("Twisted Rawhide", 6.50, Item.Category.RAWHIDE_BONE, MAX_QUANTITY));
        inventory.put("B2", new Item("Knotted Rawhide", 8.50, Item.Category.RAWHIDE_BONE, MAX_QUANTITY));
        inventory.put("B3", new Item("Compressed", 11.50, Item.Category.RAWHIDE_BONE, MAX_QUANTITY));
        inventory.put("B4", new Item("Flavored", 11.75, Item.Category.RAWHIDE_BONE, MAX_QUANTITY));
        inventory.put("C1", new Item("Standard", 14.00, Item.Category.BULLY_STICK, MAX_QUANTITY));
        inventory.put("C2", new Item("Braided", 19.50, Item.Category.BULLY_STICK, MAX_QUANTITY));
        inventory.put("C3", new Item("Jumbo", 15.50, Item.Category.BULLY_STICK, MAX_QUANTITY));
        inventory.put("C4", new Item("Curly", 18.50, Item.Category.BULLY_STICK, MAX_QUANTITY));
        inventory.put("D1", new Item("Peanut Butter Surprise", 15.00, Item.Category.MYSTERY_TREAT, MAX_QUANTITY));
        inventory.put("D2", new Item("Cheese & Bacon Surprise", 15.00, Item.Category.MYSTERY_TREAT, MAX_QUANTITY));
        inventory.put("D3", new Item("Chicken & Sweet Potato Surprise", 15.00, Item.Category.MYSTERY_TREAT, MAX_QUANTITY));
        inventory.put("D4", new Item("Beef and Apple Surprise", 15.00, Item.Category.MYSTERY_TREAT, MAX_QUANTITY));
    }
}
