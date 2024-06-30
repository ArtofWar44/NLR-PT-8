package org.ArtofWar44.Model;

public class Item {
    private int itemId;
    private String name;
    private double price;
    private Category category;
    private int quantity;

    // Constructors
    public Item() {
    }

    public Item(String name, double price, Category category, int quantity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public Item(int itemId, String name, double price, Category category, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantity() {
    }

    public enum Category {
        DOG_TOY,
        RAWHIDE_BONE,
        MYSTERY_TREAT,
        BULLY_STICK
    }
}
