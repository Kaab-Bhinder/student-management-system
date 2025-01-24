package sms;

import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class AdministrativeStaff extends Person implements Serializable, Reportable {
    private int staffID;
    private String role;
    private String department;
    private List<Object> managedEntities;

    // CONSTRUCTORS
    public AdministrativeStaff(String name, String email, String dateOfBirth, int staffID, String role,
            String department) {
        super(name, email, dateOfBirth);
        this.staffID = staffID;
        this.role = role;
        this.department = department;
        this.managedEntities = new ArrayList<>();
    }

    // GETTERS
    public int getStaffID() {
        return staffID;
    }

    public String getRole() {
        return role;
    }

    public String getDepartment() {
        return department;
    }

    // SETTERS
    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // GENERATE REPORT
    @Override
    public String generateReport(List<Object> entities) {
        int teacherCount = 0;
        int studentCount = 0;
        int courseCount = 0;
        for (Object entity : entities) {
            if (entity instanceof Teacher)
                teacherCount++;
            else if (entity instanceof Student)
                studentCount++;
            else if (entity instanceof Course)
                courseCount++;
        }
        return "Administrative Report:\n" +
                "----------------------------\n" +
                "Total Teachers: " + teacherCount + "\n" +
                "Total Students: " + studentCount + "\n" +
                "Total Courses: " + courseCount + "\n";
    }

    // SETTER
    public void setManagedEntities(List<Object> entities) {
        this.managedEntities = entities;
    }

    // EXPORT TO FILE
    @Override
    public void exportToFile() {
        try {
            FileOutputStream writeInFile = new FileOutputStream("Admin-File(export).txt");
            ObjectOutputStream writeObject = new ObjectOutputStream(writeInFile);
            writeObject.writeObject(this);
            writeObject.close();
            writeInFile.close();
        } catch (IOException e) {
            System.out.println(
                    "An error occurred while writing the object to the file. Please ensure the file path is correct and writable.");
        }
    }

    // TO STRING
    @Override
    public String toString() {
        return "AdministrativeStaff [ID=" + staffID + ", Name=" + getName() + ", Role=" + role + ", Department="
                + department + "]";
    }

    // DISPLAY
    @Override
    public void displayDetails() {
        System.out.println("Administrative Staff Details:");
        System.out.println("ID: " + staffID);
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Date of Birth: " + getDateOfBirth());
        System.out.println("Role: " + role);
        System.out.println("Department: " + department);
    }
}