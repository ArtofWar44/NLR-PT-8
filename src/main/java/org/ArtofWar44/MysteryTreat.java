package org.ArtofWar44;

public class MysteryTreat extends Item {

    public static final String CONSUME_MSG = "Munch Munch, Surprise!";

    public MysteryTreat(String name, double price) {
        super(name, price, Category.MYSTERY_TREAT);
    }

    @Override
    public String getConsumeMessage() {
        return CONSUME_MSG;
    }
}
