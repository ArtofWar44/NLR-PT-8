package org.ArtofWar44;

class MysteryTreat extends Item {
    public MysteryTreat(String name, double price) {
        super(name, price, Category.MYSTERY_TREAT);
    }

    @Override
    public String getConsumeMessage() {
        return "Enjoy your mystery treat!";
    }
}
