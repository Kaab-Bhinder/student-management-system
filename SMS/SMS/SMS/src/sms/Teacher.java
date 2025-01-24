package sms;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

// TEACHER CLASS
class Teacher extends Person implements Serializable, Reportable {

    private final String teacherID;
    private Address address;
    private String specialization;
    private List<Course> coursesTaught = new ArrayList<>();
    private static int totalTeachers = 0;

    // CONSTRUCTORS
    Teacher() {
        super();
        teacherID = "";
        this.address = new Address();
        totalTeachers++;
    }

    Teacher(String name, String ID, String email, String dateOfBirth, Address address, String specialization) {
        super(name, email, dateOfBirth);
        this.teacherID = ID;
        this.address = address;
        this.specialization = specialization;
        totalTeachers++;
    }

    // SETTERS

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setCoursesTaught(List<Course> coursesTaught) {
        this.coursesTaught = coursesTaught;
    }

    // GETTERS
    public String getTeacherID() {
        return teacherID;
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }

    // METHODS
    // ASSIGN COURSE
    public void assignCourse(Course course) {
        if (course != null) {
            coursesTaught.add(course);
            System.out.println("Teacher " + teacherID + " assigned to teach " + course.getCourseTitle() + ".");
        } else {
            System.out.println("Invalid course. Cannot assign.");
        }
    }

    // ASSIGN GRADE
    public void assignGrade(Student student, Course course, Double grade) {
        if (!coursesTaught.contains(course)) {
            System.out.println("Error: Teacher " + teacherID + " is not assigned to teach the course "
                    + course.getCourseTitle() + ".");
            return;
        }
        if (!course.isStudentEnrolled(student)) {
            System.out.println("Error: Student " + student.getName() + " is not enrolled in the course "
                    + course.getCourseTitle() + ".");
            return;
        }
        student.setGrade(course, grade);
        System.out.println("Grade " + grade + " assigned to student " + student.getName() + " for course "
                + course.getCourseTitle() + " by teacher " + teacherID + ".");
    }

    // DISPLAY COURSES
    public void displayCourses() {
        if (coursesTaught.isEmpty()) {
            System.out.println("No courses assigned to this teacher.");
        } else {
            System.out.println("Courses taught by " + getName() + ":");
            for (int i = 0; i < coursesTaught.size(); i++) {
                Course course = coursesTaught.get(i);
                course.toString();
                System.out.println();
            }
        }
    }

    @Override
    public String generateReport(List<Object> object) {
        String report = "Teacher Report:\n";
        for (Object obj : object) {
            if (obj instanceof Teacher) {
                Teacher teacher = (Teacher) obj;
                report += "Teacher ID: " + teacher.getTeacherID() +
                        ", Name: " + teacher.getName() +
                        ", Specialization: " + teacher.getSpecialization() + "\n";
                teacher.displayCourses();
            }
        }
        return report;
    }

    @Override
    public void exportToFile() {
        try {
            FileOutputStream writeInFile = new FileOutputStream("Teacher-File(export).txt");
            ObjectOutputStream writeObject = new ObjectOutputStream(writeInFile);
            writeObject.writeObject(this);
            writeObject.close();
            writeInFile.close();
        } catch (IOException e) {
            System.out.println(
                    "An error occurred while writing the object to the file. Please ensure the file path is correct and writable.");
        }
    }

    @Override
    public void displayDetails() {
        System.out.println("Teacher Details:");
        System.out.println("Teacher ID: " + teacherID);
        System.out.println("Name: " + getName());
        System.out.println("Date of Birth: " + getDateOfBirth());
        System.out.println("Specialization: " + specialization);
        System.out.println("Address: House " + address.gethouse() + ", " + address.getcity());
    }

    // TOTAL COUNT
    public static int displayTeachersCount() {
        return totalTeachers;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Teacher ID: " + teacherID + "\nName: " + getName() + "\nSpecialization: " + specialization +
                "\nAddress: House " + address.gethouse() + ", " + address.getcity();
    }
}
