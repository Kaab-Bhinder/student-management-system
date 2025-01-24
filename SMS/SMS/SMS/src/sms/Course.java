package sms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;

//COURSE CLASS
class Course implements Serializable {
    private String courseID;
    private String courseTitle;
    private int credits;
    private Teacher assignedTeacher;
    private List<Student> enrolledStudents;
    private static int totalCourses = 0;

    // CONSTRUCTORS
    Course() {
        this.enrolledStudents = new ArrayList<>();
        totalCourses++;
    }

    Course(String courseID, String title, int credits, Teacher assignedTeacher) {
        this();
        this.courseID = courseID;
        this.courseTitle = title;
        this.credits = credits;
        this.assignedTeacher = assignedTeacher;
        totalCourses++;
    }

    // SETTERS
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setTitle(String title) {
        this.courseTitle = title;
    }

    public void setCredits(int credits) {
        if (credits > 0) {
            this.credits = credits;
        } else {
            System.out.println("Credits cannot be zero or negative.");
        }
    }

    public void setAssignedTeacher(Teacher assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }

    // GETTERS
    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseTitle;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCredits() {
        return credits;
    }

    public Teacher getAssignedTeacher() {
        return assignedTeacher;
    }

    // METHODS
    // ADD STUDENT
    public void addStudent(Student student) {
        if (student != null) {
            enrolledStudents.add(student);
            System.out.println(
                    "Student " + student.getStudentID() + " (" + student.getName() + ") added to " + courseTitle);
        } else {
            System.out.println("Error: Invalid student.");
        }
    }

    // REMOVE STUDENT
    public void removeStudent(Student student) {
        if (enrolledStudents.contains(student)) {
            enrolledStudents.remove(student);
            System.out.println(
                    "Student " + student.getStudentID() + " (" + student.getName() + ") removed from " + courseTitle);
        } else {
            System.out.println("Error: Student not found in " + courseTitle);
        }
    }

    // STUDENT IS ENROLLED OR NOT
    public boolean isStudentEnrolled(Student student) {
        return enrolledStudents.contains(student);
    }

    // AVERAGE GRADE
    public double calculateAverageGrade() {
        if (enrolledStudents.isEmpty()) {
            System.out.println("No students enrolled in the course.");
            return -1;
        } else {
            double total = 0;
            int count = 0;
            for (int i = 0; i < enrolledStudents.size(); i++) {
                Double grade = enrolledStudents.get(i).getGradeForCourse(this);
                if (grade != null) {
                    total += grade;
                    count++;
                }
            }
            if (count > 0) {
                return total / count;

            } else {
                System.out.println("No valid grades to calculate average.");
                return -1;
            }
        }
    }

    // MEDIAN GRADE
    public double calculateMedianGrade() {
        List<Double> validGrades = new ArrayList<>();
        for (int i = 0; i < enrolledStudents.size(); i++) {
            Double grade = enrolledStudents.get(i).getGradeForCourse(this);
            if (grade != null) {
                validGrades.add(grade);
            }
        }
        if (validGrades.isEmpty()) {
            System.out.println("No grades available to calculate median for " + courseTitle);
            return -1;
        }
        Collections.sort(validGrades);
        double median;
        int size = validGrades.size();

        if (size % 2 == 0) {
            median = (validGrades.get(size / 2 - 1) + validGrades.get(size / 2)) / 2.0;
        } else {
            median = validGrades.get(size / 2);
        }
        return median;
    }

    // TOTAL COUNT
    public static int displayCoursesCount() {
        return totalCourses;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Course ID: " + courseID + "\nTitle: " + courseTitle + "\nCredits: " + credits;
    }
}
