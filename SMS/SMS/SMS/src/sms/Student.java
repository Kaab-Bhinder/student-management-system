package sms;

import java.io.*;
import java.util.*;

// STUDENT CLASS
class Student extends Person implements Serializable {
    private String studentID;
    private Address address;
    private List<Course> enrolledCourses = new ArrayList<>();
    private List<Double> grades = new ArrayList<>();
    private static int totalStudents = 0;

    // CONSTRUCTORS
    public Student() {
        super();
        this.studentID = "";
        this.address = new Address();
        totalStudents++;
    }

    public Student(String studentID, String name, String email, String dateOfBirth, Address address) {
        super(name, email, dateOfBirth);
        this.studentID = studentID;
        this.address = address;
        totalStudents++;
    }

    // SETTERS
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
        this.grades = new ArrayList<>();
        for (int i = 0; i < enrolledCourses.size(); i++) {
            this.grades.add(null);
        }
    }

    // GETTERS
    public String getStudentID() {
        return studentID;
    }

    public Address getAddress() {
        return address;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public List<Double> getGrades() {
        return grades;
    }

    // METHODS
    // ENROLL IN COURSE
    public void enrollInCourse(Course course) {
        if (course != null) {
            enrolledCourses.add(course);
            grades.add(null);
            course.addStudent(this);
            System.out.println("Student " + studentID + " successfully enrolled in " + course.getCourseTitle());
        } else {
            System.out.println("Error: Invalid course.");
        }
    }

    // REMOVE COURSE
    public void removeCourse(Course course) {
        int index = enrolledCourses.indexOf(course);
        if (index != -1) {
            enrolledCourses.remove(index);
            grades.remove(index);
            System.out.println("Course " + course.getCourseTitle() + " removed for student " + studentID);
        } else {
            System.out.println("Error: Course " + course.getCourseTitle() + " not found for student " + studentID);
        }
    }

    // SET GRADE FOR A COURSE
    public void setGrade(Course course, Double grade) {
        int index = enrolledCourses.indexOf(course);
        if (index != -1) {
            grades.set(index, grade);
            System.out.println(
                    "Grade " + grade + " set for course " + course.getCourseTitle() + " for student " + studentID);
        } else {
            System.out.println("Error: Student " + studentID + " is not enrolled in " + course.getCourseTitle());
        }
    }

    // DISPLAY GRADES FOR ALL COURSES
    public void displayGrades() {
        if (enrolledCourses.isEmpty()) {
            System.out.println("Student " + studentID + " is not enrolled in any courses.");
        } else {
            System.out.println("Grades for Student " + studentID + ":");
            for (int i = 0; i < enrolledCourses.size(); i++) {
                Course course = enrolledCourses.get(i);
                Double grade = grades.get(i);
                if (grade != null) {
                    System.out.println("Course Title: " + course.getCourseTitle() + ", Grade: " + grade);
                } else {
                    System.out.println("Course Title: " + course.getCourseTitle() + ", Grade: NULL");
                }
            }
        }
    }

    // DISPLAY ALL COURSES
    public void displayCourses() {
        if (enrolledCourses.isEmpty()) {
            System.out.println("Student " + studentID + " is not enrolled in any courses.");
        } else {
            System.out.println("Courses for Student " + studentID + ":");
            for (int i = 0; i < enrolledCourses.size(); i++) {
                Course course = enrolledCourses.get(i);
                System.out.println("Course Title: " + course.getCourseTitle() + ", Credits: " + course.getCredits());
            }
        }
    }

    // GET GRADE FOR A SPECIFIC COURSE
    public Double getGradeForCourse(Course course) {
        int index = enrolledCourses.indexOf(course);
        if (index != -1) {
            return grades.get(index);
        }
        return null;
    }

    // TOTAL COUNT
    public static int displayStudentsCount() {
        return totalStudents;
    }

    // DISPLAY DETAILS
    @Override
    public void displayDetails() {
        System.out.println("Student ID: " + studentID + ", Name: " + getName() +
                ", Address: House " + address.gethouse() + ", " + address.getcity());
    }

    // TO STRING
    @Override
    public String toString() {
        return "Student ID: " + studentID + ", Name: " + getName() + ", Address: House " + address.gethouse() + ", "
                + address.getcity();
    }
}
