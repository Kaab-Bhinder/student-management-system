package sms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentMenuGUI extends JFrame {

  private University university;

  public StudentMenuGUI(University university) {
    this.university = university;
    setTitle("Student Menu");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setPreferredSize(new Dimension(400, 300));
    setLayout(new GridLayout(5, 1, 10, 10));

    JButton enrollButton = new JButton("Enroll In A Course");
    JButton seeGradesButton = new JButton("See Grades");
    JButton seeEnrolledCoursesButton = new JButton("See Enrolled Courses");
    JButton searchCoursesButton = new JButton("Search for courses by minimum credits");
    JButton backButton = new JButton("Return to Main Menu");

    add(enrollButton);
    add(seeGradesButton);
    add(seeEnrolledCoursesButton);
    add(searchCoursesButton);
    add(backButton);

    enrollButton.addActionListener(e -> enrollInCourse());
    seeGradesButton.addActionListener(e -> seeGrades());
    seeEnrolledCoursesButton.addActionListener(e -> seeEnrolledCourses());
    searchCoursesButton.addActionListener(e -> searchCoursesByCredits());
    backButton.addActionListener(e -> dispose()); // Close the student menu

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void enrollInCourse() {
    String name = JOptionPane.showInputDialog(this, "Enter your name:");
    if (name == null || name.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Name is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    List<Student> students = university.searchStudentByName(name);
    if (students.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No student found with the name " + name, "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Student student;

    if (students.size() > 1) {
      String[] studentDetails = new String[students.size()];
      for (int i = 0; i < students.size(); i++) {
        studentDetails[i] = "Name: " + students.get(i).getName() + ", ID: " + students.get(i).getStudentID();
      }

      String selectedStudent = (String) JOptionPane.showInputDialog(this, "Multiple students found. Select one:",
          "Select Student", JOptionPane.QUESTION_MESSAGE, null, studentDetails, studentDetails[0]);

      if (selectedStudent == null) {
        return;
      }

      student = null;
      for (Student s : students) {
        if (selectedStudent.contains(s.getStudentID())) {
          student = s;
          break;
        }
      }

      if (student == null) {
        JOptionPane.showMessageDialog(this, "Invalid selection. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
    } else {
      student = students.get(0);
    }

    List<Course> courses = university.getCourseRepository().getAll();
    if (courses.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No courses available to enroll.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String[] courseDetails = new String[courses.size()];
    for (int i = 0; i < courses.size(); i++) {
      courseDetails[i] = "Title: " + courses.get(i).getCourseTitle() + ", ID: " + courses.get(i).getCourseID();
    }

    String selectedCourse = (String) JOptionPane.showInputDialog(this, "Select a course to enroll in:",
        "Available Courses", JOptionPane.QUESTION_MESSAGE, null, courseDetails, courseDetails[0]);
    if (selectedCourse == null) {
      return;
    }

    Course course = null;
    for (Course c : courses) {
      if (selectedCourse.contains(c.getCourseID())) {
        course = c;
        break;
      }
    }

    if (course == null) {
      JOptionPane.showMessageDialog(this, "Invalid course selection. Please try again.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    student.enrollInCourse(course);
    JOptionPane.showMessageDialog(this, "Enrolled in course successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  // =================================================================================================================================
  private void seeGrades() {
    String name = JOptionPane.showInputDialog(this, "Enter your name:");
    if (name == null || name.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Name is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    List<Student> students = university.searchStudentByName(name);
    if (students.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Student student;

    if (students.size() > 1) {
      String[] studentDetails = new String[students.size()];
      for (int i = 0; i < students.size(); i++) {
        studentDetails[i] = "Name: " + students.get(i).getName() + ", ID: " + students.get(i).getStudentID();
      }

      String selectedStudent = (String) JOptionPane.showInputDialog(this,
          "Multiple students found. Select one:", "Select Student",
          JOptionPane.QUESTION_MESSAGE, null, studentDetails, studentDetails[0]);

      if (selectedStudent == null) {
        return;
      }

      student = null;
      for (Student s : students) {
        if (selectedStudent.contains(s.getStudentID())) {
          student = s;
          break;
        }
      }

      if (student == null) {
        JOptionPane.showMessageDialog(this, "Invalid selection. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
    } else {
      student = students.get(0);
    }

    StringBuilder gradesOutput = new StringBuilder("Grades for " + student.getName() + ":\n");
    List<Course> courses = student.getEnrolledCourses();
    List<Double> grades = student.getGrades();

    if (courses.isEmpty()) {
      gradesOutput.append("No courses enrolled.");
    } else {
      for (int i = 0; i < courses.size(); i++) {
        gradesOutput.append(courses.get(i).toString())
            .append(", Grade: ")
            .append(grades.get(i) == null ? "Not Assigned" : grades.get(i))
            .append("\n");
      }
    }

    JOptionPane.showMessageDialog(this, gradesOutput.toString(), "Student Grades", JOptionPane.INFORMATION_MESSAGE);
  }

  // =================================================================================================================================
  private void seeEnrolledCourses() {

    String name = JOptionPane.showInputDialog(this, "Enter your name:");
    if (name == null || name.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Name is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    List<Student> students = university.searchStudentByName(name);
    if (students.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Student student;

    if (students.size() > 1) {
      String[] studentDetails = new String[students.size()];
      for (int i = 0; i < students.size(); i++) {
        studentDetails[i] = "Name: " + students.get(i).getName() + ", ID: " + students.get(i).getStudentID();
      }

      String selectedStudent = (String) JOptionPane.showInputDialog(this,
          "Multiple students found. Select one:",
          "Select Student",
          JOptionPane.QUESTION_MESSAGE,
          null,
          studentDetails,
          studentDetails[0]);

      if (selectedStudent == null) {
        return;
      }

      student = null;
      for (Student s : students) {
        if (selectedStudent.contains(s.getStudentID())) {
          student = s;
          break;
        }
      }

      if (student == null) {
        JOptionPane.showMessageDialog(this, "Invalid selection. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
    } else {
      student = students.get(0);
    }

    StringBuilder coursesOutput = new StringBuilder("Enrolled Courses for " + student.getName() + ":\n");
    List<Course> courses = student.getEnrolledCourses();

    if (courses.isEmpty()) {
      coursesOutput.append("No courses enrolled.");
    } else {
      for (Course course : courses) {
        coursesOutput.append(course.toString()).append("\n");
      }
    }

    JOptionPane.showMessageDialog(this, coursesOutput.toString(), "Enrolled Courses", JOptionPane.INFORMATION_MESSAGE);
  }

  // =================================================================================================================================
  private void searchCoursesByCredits() {
    String minCreditsStr = JOptionPane.showInputDialog(this, "Enter minimum credit for course:");
    if (minCreditsStr == null || minCreditsStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Minimum credit is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      int minCredits = Integer.parseInt(minCreditsStr);
      List<Course> courses = university.filterCoursesByCredits(minCredits);
      if (!courses.isEmpty()) {
        StringBuilder courseList = new StringBuilder("Courses with at least " + minCredits + " credits:\n\n");
        for (Course course : courses) {
          courseList.append(course.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this, courseList.toString(), "Courses Found", JOptionPane.INFORMATION_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(this, "No courses found with the minimum credit requirement of " + minCredits,
            "No Courses Found", JOptionPane.INFORMATION_MESSAGE);
      }
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid number of credits. Please enter a valid integer.",
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
