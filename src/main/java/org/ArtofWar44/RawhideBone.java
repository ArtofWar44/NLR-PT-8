package org.ArtofWar44;

public class RawhideBone extends Item {
    public static final String CONSUME_MSG = "Chew Chew, Delicious!";

    public RawhideBone(String name, double price) {
        super(name, price, Category.RAWHIDE_BONE);
    }

    @Override
    public String getConsumeMessage() {
        return CONSUME_MSG;
    }
}


