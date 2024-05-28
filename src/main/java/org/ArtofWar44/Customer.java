package org.ArtofWar44;

import java.util.ArrayList;
import java.util.Date;

public class Customer {
    private String firstName;
    private String lastName;
    private Date birthday;
    private String email;
    private int purchases;
    private ArrayList<Dog> dogs;

    public Customer(String firstName, String lastName, Date birthday, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.purchases = 0;
        this.dogs = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public int getPurchases() {
        return purchases;
    }

    public void addPurchase() {
        purchases++;
    }

    public boolean isEligibleForReward() {
        return purchases >= 10;
    }

    public void addDog(Dog dog) {
        if (dogs.size() < 2) {
            dogs.add(dog);
        } else {
            System.out.println("Maximum of 2 dogs per customer.");
        }
    }

    public ArrayList<Dog> getDogs() {
        return dogs;
    }
}
