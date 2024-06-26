package org.ArtofWar44;

import org.ArtofWar44.Dao.*;
import org.ArtofWar44.Model.Customer;
import org.ArtofWar44.Model.Item;
import org.ArtofWar44.Model.Transaction;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 * Main class for the Pawsome Vending Machine application.
 * Handles customer and employee interactions with the vending machine.
 */
public class PawsomeVendingMachine {
    private static final int MAX_QUANTITY = 5;
    private final Map<String, Item> inventory = new HashMap<>();
    private double pawPointsBalance = 20.00;
    private final CustomerDAO customerDAO;
    private final ItemDAO itemDAO;
    private final TransactionDAO transactionDAO;
    private Customer currentCustomer;

    /*
     * Constructor for PawsomeVendingMachine.
     * Initializes DAOs and restocks the inventory.
     */
    public PawsomeVendingMachine(CustomerDAO customerDAO, ItemDAO itemDAO, TransactionDAO transactionDAO) {
        this.customerDAO = customerDAO;
        this.itemDAO = itemDAO;
        this.transactionDAO = transactionDAO;
        restockInventory();
    }

    /*
     * Main method to start the application.
     */
    public static void main(String[] args) {
        DataSource dataSource = createDataSource();
        CustomerDAO customerDAO = new JdbcCustomerDAO(dataSource);
        ItemDAO itemDAO = new JdbcItemDAO(dataSource);
        TransactionDAO transactionDAO = new JdbcTransactionDAO(dataSource);

        PawsomeVendingMachine pawsomeVendingMachine = new PawsomeVendingMachine(customerDAO, itemDAO, transactionDAO);
        pawsomeVendingMachine.run();
    }

    /*
     * Creates and configures the DataSource for database connections.
     */
    private static DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/PawPointsDB");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        return dataSource;
    }

    /*
     * Main interactive menu for the application.
     * Allows customers and employees to log in and perform various actions.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to PawSome Vending");
            System.out.println("1. Customer Login");
            System.out.println("2. Add New Customer");
            System.out.println("3. Employee Login");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                customerLogin(scanner);
            } else if (choice == 2) {
                addNewCustomer(scanner);
            } else if (choice == 3) {
                employeeLogin(scanner);
            } else if (choice == 4) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    /*
     * Handles customer login.
     * Allows a customer to log in by entering their name or email.
     */
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
            System.out.println();
            System.out.println("Welcome, " + currentCustomer.getName() + "!");
            System.out.println();
            mainMenu(scanner);
        } else {
            System.out.println("Loyalty rewards customer not found. Please try again.");
        }
    }

    /*
     * Allows a new customer to be added to the system.
     */
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

    /*
     * Handles employee login.
     * Only allows access if the correct password ("admin") is entered.
     */
    private void employeeLogin(Scanner scanner) {
        System.out.print("Enter employee password: ");
        String password = scanner.nextLine();

        if ("admin".equals(password)) {
            System.out.println();
            employeeMenu(scanner);
        } else {
            System.out.println("Incorrect password. Please try again.");
        }
    }

    /*
     * Interactive menu for employees.
     * Allows employees to manage customers, items, and transactions.
     */
    private void employeeMenu(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Employee Menu:");
            System.out.println("1. List Customers");
            System.out.println("2. Delete Customer");
            System.out.println("3. List Items");
            System.out.println("4. Add Item");
            System.out.println("5. Update Item");
            System.out.println("6. Delete Item");
            System.out.println("7. List Transactions");
            System.out.println("8. Add Transaction");
            System.out.println("9. Update Transaction");
            System.out.println("10. Delete Transaction");
            System.out.println("11. Exit Employee Menu");
            System.out.println();
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                listCustomers();
            } else if (choice == 2) {
                deleteCustomer(scanner);
            } else if (choice == 3) {
                listItems();
            } else if (choice == 4) {
                addItem(scanner);
            } else if (choice == 5) {
                updateItem(scanner);
            } else if (choice == 6) {
                deleteItem(scanner);
            } else if (choice == 7) {
                listTransactions();
            } else if (choice == 8) {
                addTransaction(scanner);
            } else if (choice == 9) {
                updateTransaction(scanner);
            } else if (choice == 10) {
                deleteTransaction(scanner);
            } else if (choice == 11) {
                System.out.println("Exiting Employee Menu.");
                return;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /*
     * Lists all customers in the system.
     */
    private void listCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println("Customer ID: " + customer.getCustomerId() + ", Name: " + customer.getName() + ", Email: " + customer.getEmail() + ", Paw Points Balance: " + customer.getPawPointsBalance());
        }
    }

    /*
     * Deletes a customer from the system.
     */
    private void deleteCustomer(Scanner scanner) {
        System.out.print("Enter customer ID to delete: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();
        customerDAO.deleteCustomer(customerId);
        System.out.println("Customer deleted successfully.");
    }

    /*
     * Lists all items in the inventory.
     */
    private void listItems() {
        List<Item> items = itemDAO.getAllItems();
        for (Item item : items) {
            System.out.println("Item ID: " + item.getItemId() + ", Name: " + item.getName() + ", Price: " + item.getPrice() + ", Category: " + item.getCategory() + ", Quantity: " + item.getQuantity());
        }
        System.out.println(); // Add space after listing items
    }

    /*
     * Adds a new item to the inventory.
     */
    private void addItem(Scanner scanner) {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter item category: ");
        String category = scanner.nextLine();
        System.out.print("Enter item quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Item item = new Item(name, price, Item.Category.valueOf(category.toUpperCase()), quantity);
        itemDAO.addItem(item);
        System.out.println("Item added successfully.");
    }

    /*
     * Updates an existing item in the inventory.
     */
    private void updateItem(Scanner scanner) {
        System.out.print("Enter item ID to update: ");
        int itemId = scanner.nextInt();
        scanner.nextLine();
        Item item = itemDAO.getItemById(itemId);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        System.out.print("Enter new item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new item price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter new item category: ");
        String category = scanner.nextLine();
        System.out.print("Enter new item quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        item.setName(name);
        item.setPrice(price);
        item.setCategory(Item.Category.valueOf(category.toUpperCase()));
        item.setQuantity(quantity);
        itemDAO.updateItem(item);
        System.out.println("Item updated successfully.");
    }

    /*
     * Deletes an item from the inventory.
     */
    private void deleteItem(Scanner scanner) {
        System.out.print("Enter item ID to delete: ");
        int itemId = scanner.nextInt();
        scanner.nextLine();
        itemDAO.deleteItem(itemId);
        System.out.println("Item deleted successfully.");
    }

    /*
     * Lists all transactions in the system.
     */
    private void listTransactions() {
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        for (Transaction transaction : transactions) {
            System.out.println("Transaction ID: " + transaction.getTransactionId() + ", Customer ID: " + transaction.getCustomerId() + ", Item ID: " + transaction.getItemId() + ", Quantity: " + transaction.getQuantity() + ", Date: " + transaction.getTransactionDate());
        }
        System.out.println(); // Add space after listing transactions
    }

    /*
     * Adds a new transaction to the system.
     */
    private void addTransaction(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        System.out.print("Enter item ID: ");
        int itemId = scanner.nextInt();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Transaction transaction = new Transaction();
        transaction.setCustomerId(customerId);
        transaction.setItemId(itemId);
        transaction.setQuantity(quantity);
        transaction.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()));

        transactionDAO.addTransaction(transaction);
        System.out.println("Transaction added successfully.");
    }

    /*
     * Updates an existing transaction in the system.
     */
    private void updateTransaction(Scanner scanner) {
        System.out.print("Enter transaction ID to update: ");
        int transactionId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Transaction transaction = transactionDAO.getTransactionById(transactionId);
        if (transaction == null) {
            System.out.println("Transaction not found.");
            return;
        }

        System.out.print("Enter new customer ID: ");
        int customerId = scanner.nextInt();
        System.out.print("Enter new item ID: ");
        int itemId = scanner.nextInt();
        System.out.print("Enter new quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        transaction.setCustomerId(customerId);
        transaction.setItemId(itemId);
        transaction.setQuantity(quantity);
        transaction.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()));

        transactionDAO.updateTransaction(transaction);
        System.out.println("Transaction updated successfully.");
    }

    /*
     * Deletes a transaction from the system.
     */
    private void deleteTransaction(Scanner scanner) {
        System.out.print("Enter transaction ID to delete: ");
        int transactionId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        transactionDAO.deleteTransaction(transactionId);
        System.out.println("Transaction deleted successfully.");
    }

    /*
     * Main menu for customers to interact with the vending machine.
     */
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

    /*
     * Displays all items available in the vending machine.
     */
    private void displayVendingMachineItems() {
        System.out.println();
        System.out.println("PawSome Vending Items:");
        for (Map.Entry<String, Item> entry : inventory.entrySet()) {
            Item item = entry.getValue();
            String status = item.getQuantity() > 0 ? item.getQuantity() + " remaining" : "SOLD OUT";
            System.out.println(entry.getKey() + ": " + item.getName() + " - $" + item.getPrice() + " (" + status + ")");
        }
        System.out.println();
    }

    /*
     * Handles the purchase process for customers.
     */
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

    /*
     * Allows customers to redeem Paw Points by entering a loyalty code.
     * The correct code is "0000".
     */
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

    /*
     * Allows customers to select a product to purchase from the vending machine.
     */
    private void selectProduct(Scanner scanner) {
        System.out.print("Enter selection: ");
        String slotId = scanner.nextLine().toUpperCase();
        Item item = inventory.get(slotId);

        if (item == null) {
            System.out.println("Invalid selection. Please try again.");
        } else if (item.getQuantity() == 0) {
            System.out.println("Item is SOLD OUT. Please choose another item.");
        } else if (pawPointsBalance < item.getPrice()) {
            System.out.println("Insufficient Paw Points. Please add more Paw Points. Don't let your dog down!");
        } else {
            item.decreaseQuantity();
            pawPointsBalance -= item.getPrice();
            System.out.println("Dispensing " + item.getName() + ". Current Paw Points balance: $" + pawPointsBalance);
        }
        System.out.println();
    }

    /*
     * Displays the current Paw Points balance for the logged-in customer.
     */
    private void checkPawPointsBalance() {
        System.out.println("Current Paw Points balance: $" + pawPointsBalance);
        System.out.println();
    }

    /*
     * Restocks the inventory with default items.
     */
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
