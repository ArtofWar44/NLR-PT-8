package org.ArtofWar44;

public abstract class Item {
     public enum Category {
         DOG_TOY {
             @Override
             public Item createItem(String name, double price) {
                 return new DogToy(name, price);
             }
         },
         RAWHIDE_BONE {
             @Override
             public Item createItem(String name, double price) {
                 return new RawhideBone(name, price);
             }
         },
         BULLY_STICK {
             @Override
             public Item createItem(String name, double price) {
                 return new BullyStick(name, price);
             }
         },
         MYSTERY_TREAT {
             @Override
             public Item createItem(String name, double price) {
                 return new MysteryTreat(name, price);
             }
         };

         // Use the enum as a factory
         public abstract Item createItem(String name, double price);
     }

    private final String name;
    private final double price;
    private final Category category;
    private int quantity = 5; // Default initial quantity

    public Item(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public abstract String getConsumeMessage();
}



