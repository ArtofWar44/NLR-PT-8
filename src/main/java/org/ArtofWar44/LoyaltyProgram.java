package org.ArtofWar44;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


    public class LoyaltyProgram {
        private static final int MAX_QUANTITY = 5;
        private Map<String, Item> inventory = new HashMap<>();
        private double pawPointsBalance = 20.00;

        public static void main(String[] args) {
            LoyaltyProgram loyaltyProgram = new LoyaltyProgram();
            loyaltyProgram.restockInventory();
            loyaltyProgram.run();
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
            inventory.put("A1", Item.Category.DOG_TOY.createItem("Squeaky Ball", 6.00));
            inventory.put("A2", Item.Category.DOG_TOY.createItem("Rope Tug Toy", 8.00));
            inventory.put("A3", Item.Category.DOG_TOY.createItem("Plush Chew Toy", 10.00));
            inventory.put("A4", Item.Category.DOG_TOY.createItem("Rubber Chew Toy", 10.00));
            inventory.put("B1", Item.Category.RAWHIDE_BONE.createItem("Twisted Rawhide", 6.50));
            inventory.put("B2", Item.Category.RAWHIDE_BONE.createItem("Knotted Rawhide", 8.50));
            inventory.put("B3", Item.Category.RAWHIDE_BONE.createItem("Compressed", 11.50));
            inventory.put("B4", Item.Category.RAWHIDE_BONE.createItem("Flavored", 11.75));
            inventory.put("C1", Item.Category.BULLY_STICK.createItem("Standard", 14.00));
            inventory.put("C2", Item.Category.BULLY_STICK.createItem("Braided", 19.50));
            inventory.put("C3", Item.Category.BULLY_STICK.createItem("Jumbo", 15.50));
            inventory.put("C4", Item.Category.BULLY_STICK.createItem("Curly", 18.50));
            inventory.put("D1", Item.Category.MYSTERY_TREAT.createItem("Peanut Butter Surprise", 15.00));
            inventory.put("D2", Item.Category.MYSTERY_TREAT.createItem("Cheese & Bacon Surprise", 15.00));
            inventory.put("D3", Item.Category.MYSTERY_TREAT.createItem("Chicken & Sweet Potato Surprise", 15.00));
            inventory.put("D4", Item.Category.MYSTERY_TREAT.createItem("Beef and Apple Surprise", 15.00));
        }
    }

