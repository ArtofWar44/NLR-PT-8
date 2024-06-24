package org.ArtofWar44;

public class BullyStick extends Item {
    public static final String CONSUME_MSG = "Chew Chew, Yummy!";

    public BullyStick(String name, double price) {
        super(name, price, Category.BULLY_STICK);
    }

    @Override
    public String getConsumeMessage() {
        return CONSUME_MSG;
    }
}

