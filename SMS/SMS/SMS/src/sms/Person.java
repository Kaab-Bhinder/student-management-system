package sms;

import java.io.*;

// BASE CLASS
abstract class Person implements Serializable {
    protected String name;
    protected String email;
    protected String dateOfBirth;

    // CONSTRUCTORS
    public Person() {
    }

    public Person(String name, String email, String dateOfBirth) {
        setName(name);
        setEmail(email);
        setDateOfBirth(dateOfBirth);
    }

    // SETTERS
    public void setName(String name) {
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty. Please enter a valid name.");
        } else {
            this.name = name;
        }
    }

    public void setDateOfBirth(String dateOfBirth) {
        String[] dateParts = dateOfBirth.split("-");
        if (dateParts.length != 3) {
            System.out.println("Invalid date format. Please use DD-MM-YYYY.");
            return;
        }
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        if (month < 1 || month > 12) {
            System.out.println("Invalid month! Please enter a value between 01 and 12.");
            return;
        }
        int maxDays = 31;
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                maxDays = 30;
                break;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    maxDays = 29;
                } else {
                    maxDays = 28;
                }
                break;
        }
        if (day < 1 || day > maxDays) {
            System.out
                    .println("Invalid day for the selected month! Please enter a day between 01 and " + maxDays + ".");
        } else {
            this.dateOfBirth = String.format("%02d-%02d-%04d", day, month, year);
        }
    }

    public void setEmail(String email) {
        if (email.isEmpty()) {
            System.out.println("Email cannot be empty. Please enter a valid email.");
        } else {
            this.email = email;
        }
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    // DISPLAY DETAILS
    public abstract void displayDetails();

    // TO-STRING
    @Override
    public String toString() {
        return "Name: " + name + "\nDate Of Birth: " + dateOfBirth + "\nEmail: " + email;
    }
}
