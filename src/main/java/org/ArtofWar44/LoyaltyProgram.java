package org.ArtofWar44;

import org.ArtofWar44.Dao.CustomerDAO;
import org.ArtofWar44.Dao.ItemDAO;
import org.ArtofWar44.Dao.JdbcCustomerDAO;
import org.ArtofWar44.Dao.JdbcItemDAO;
import org.ArtofWar44.Model.Customer;
import org.ArtofWar44.Model.Item;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LoyaltyProgram {
    private static final int MAX_QUANTITY = 5;
    private final Map<String, Item> inventory = new HashMap<>();
    private double pawPointsBalance = 20.00;
    private final CustomerDAO customerDAO;
    private Customer currentCustomer;

    public LoyaltyProgram(CustomerDAO customerDAO, ItemDAO itemDAO) {
        this.customerDAO = customerDAO;
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
            System.out.println("Welcome to PawSome Vending");
            System.out.println("1. Customer Login");
            System.out.println("2. Add New Customer");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                customerLogin(scanner);
            } else if (choice == 2) {
                addNewCustomer(scanner);
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private void customerLogin(Scanner scanner) {
        System.out.print("Enter customer name or email: ");
        String input = scanner.nextLine();
        List<Customer> customers = customerDAO.getAllCustomers();
        currentCustomer = null;

        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(input) || customer.getEmail().equalsIgnoreCase(input)) {
                currentCustomer = customer;
                break;
            }
        }

        if (currentCustomer != null) {
            pawPointsBalance = currentCustomer.getPawPointsBalance();
            System.out.println("Welcome, " + currentCustomer.getName() + "!");
            mainMenu(scanner);
        } else {
            System.out.println("Loyalty rewards customer not found. Please try again.");
        }
    }

    private void addNewCustomer(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Customer newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setEmail(email);
        newCustomer.setPawPointsBalance(20.00);

        customerDAO.addCustomer(newCustomer);
        System.out.println("Loyalty rewards customer added successfully. Please log in.");

        customerLogin(scanner);
    }

    private void mainMenu(Scanner scanner) {
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
            System.out.println("1. Redeem Paw Points");
            System.out.println("2. Select Rewards Item");
            System.out.println("3. Finish Transaction");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                if (redeemPawPoints(scanner)) {
                    System.out.println("Paw Points redeemed. Current balance: $" + pawPointsBalance);
                } else {
                    System.out.println("Failed to redeem Paw Points. Please try again.");
                }
            } else if (choice == 2) {
                selectProduct(scanner);
            } else if (choice == 3) {
                System.out.println("Transaction complete. Current Paw Points balance: $" + pawPointsBalance);
                currentCustomer.setPawPointsBalance(pawPointsBalance);
                customerDAO.updateCustomer(currentCustomer);
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println();
    }

    // redeemPawPoints method prompts the EU to enter a 4-digit loyalty code (0000).
    //If the entered code is correct, the EU earns 1 Paw Point. If not, the error message is displayed, and the user is asked to try again.
    private boolean redeemPawPoints(Scanner scanner) {
        System.out.print("Enter 4-digit loyalty code to redeem 1 point: ");
        String loyaltyCode = scanner.nextLine();
        if ("0000".equals(loyaltyCode)) {
            pawPointsBalance += 1.00;
            return true;
        } else {
            System.out.println("Incorrect code, please try again.");
            return false;
        }
    }

    private void selectProduct(Scanner scanner) {
        System.out.print("Enter selection: ");
        String slotId = scanner.nextLine().toUpperCase();
        Item item = inventory.get(slotId);

        if (item == null) {
            System.out.println("Invalid selection. Please try again.");
        } else if (item.getQuantity() == 0) {
            System.out.println("Item is SOLD OUT. Please choose another item.");
        } else if (pawPointsBalance < item.getPrice()) {
            System.out.println("Insufficient Paw Points. Please add more Paw Points. Don't let this count as one of those times you let your dog down!");
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


    //eventually I'd like to add an employee login option on the main menu to restock items and do inventory
    // ability to pull data from Transactions w/ timestamps
    //Welcome to PawSome Vending
    //1. Customer Login
    //2. Add New Customer
    //3. Exit
    //4. Employee Login  ***
    //Choose an option:

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


