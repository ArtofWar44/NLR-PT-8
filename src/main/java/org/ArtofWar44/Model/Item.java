package org.ArtofWar44.Model;

public class Item {
    private int itemId;
    private String name;
    private double price;
    private Category category;
    private int quantity;

    public enum Category {
        DOG_TOY {
            @Override
            public Item createItem(String name, double price) {
                return new Item(name, price, DOG_TOY, 5); // Default quantity set to 5
            }
        },
        RAWHIDE_BONE {
            @Override
            public Item createItem(String name, double price) {
                return new Item(name, price, RAWHIDE_BONE, 5); // Default quantity set to 5
            }
        },
        BULLY_STICK {
            @Override
            public Item createItem(String name, double price) {
                return new Item(name, price, BULLY_STICK, 5); // Default quantity set to 5
            }
        },
        MYSTERY_TREAT {
            @Override
            public Item createItem(String name, double price) {
                return new Item(name, price, MYSTERY_TREAT, 5); // Default quantity set to 5
            }
        };

        public abstract Item createItem(String name, double price);
    }

    public Item() {
    }

    public Item(String name, double price, Category category, int quantity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

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
        if (this.quantity > 0) {
            this.quantity--;
        }
    }
}

