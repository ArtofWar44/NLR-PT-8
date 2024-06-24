package org.ArtofWar44;

class RawhideBone extends Item {
    public RawhideBone(String name, double price) {
        super(name, price, Category.RAWHIDE_BONE);
    }

    @Override
    public String getConsumeMessage() {
        return "Enjoy your rawhide bone!";
    }
}
