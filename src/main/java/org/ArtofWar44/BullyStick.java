package org.ArtofWar44;

class BullyStick extends Item {
    public BullyStick(String name, double price) {
        super(name, price, Category.BULLY_STICK);
    }

    @Override
    public String getConsumeMessage() {
        return "Enjoy your bully stick!";
    }
}
