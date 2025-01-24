package sms;

import java.io.*;

// ADDRESS CLASS
class Address implements Serializable {
    private String city;
    private int houseNumber;

    // CONSTRUCTORS
    public Address() {
    }

    public Address(int number, String city) {
        this.houseNumber = number;
        this.city = city;
    }

    // SETTERS
    public void setcity(String city) {
        if (city.isEmpty()) {
            System.out.println("City cannot be empty. Please enter a valid city.");
        } else {
            this.city = city;
        }
    }

    public void sethouse(int number) {
        if (houseNumber <= 0) {
            System.out.println("House number must be greater than 0.");
        } else {
            this.houseNumber = number;
        }
    }

    // GETTERS
    public String getcity() {
        return city;
    }

    public int gethouse() {
        return houseNumber;
    }
}
