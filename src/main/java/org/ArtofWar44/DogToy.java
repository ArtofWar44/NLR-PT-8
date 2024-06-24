package org.ArtofWar44;


class DogToy extends Item {
    public DogToy(String name, double price) {
        super(name, price, Category.DOG_TOY);
    }

    @Override
    public String getConsumeMessage() {
        return "Enjoy your new dog toy!";
    }
}
