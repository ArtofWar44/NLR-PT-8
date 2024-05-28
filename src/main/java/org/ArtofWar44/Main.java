package org.ArtofWar44;

public class Main {
    public static void main(String[] args) {
        LoyaltyProgram loyaltyProgram = new LoyaltyProgram();
        TestData.initialize(loyaltyProgram);
        loyaltyProgram.run();
    }
}
