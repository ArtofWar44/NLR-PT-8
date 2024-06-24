package org.ArtofWar44;

public class DogToy extends Item {
    public static final String CONSUME_MSG = "Woof Woof, Fun Time";

    public DogToy(String name, double price) {
        super(name, price, Category.DOG_TOY);
    }

    @Override
    public String getConsumeMessage() {
        return CONSUME_MSG;
    }
}