package org.ArtofWar44;

public class ItemSoldOutException extends Exception {
    public ItemSoldOutException(String itemName) {
        super(String.format("The item %s is sold out!", itemName));
    }
}