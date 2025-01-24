package sms;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TeacherMenuGUI extends JFrame {

  private University university;

  public TeacherMenuGUI(University university) {
    this.university = university;
    setTitle("Teacher Menu");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setPreferredSize(new Dimension(400, 300));
    setLayout(new GridLayout(4, 1, 10, 10)); // GridLayout for buttons

    JButton assignGradeButton = new JButton("Assign grade to student");
    JButton displayCoursesButton = new JButton("Display all courses you are teaching");
    JButton exportReportButton = new JButton("Export report to file");
    JButton backButton = new JButton("Return to Main Menu");

    // Add buttons to the frame
    add(assignGradeButton);
    add(displayCoursesButton);
    add(exportReportButton);
    add(backButton);

    // Action Listeners (stubs for now)
    assignGradeButton.addActionListener(e -> assignGradeToStudent());
    displayCoursesButton.addActionListener(e -> displayCoursesTeaching());
    exportReportButton.addActionListener(e -> exportReportToFile());
    backButton.addActionListener(e -> dispose()); // Close the teacher menu

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void assignGradeToStudent() {
    String teacherName = JOptionPane.showInputDialog(this, "Enter your name:");
    if (teacherName == null || teacherName.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Name is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    List<Teacher> teachers = university.searchTeachersByName(teacherName);
    if (teachers.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Teacher not found!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Teacher teacher;

    if (teachers.size() > 1) {
      String[] teacherDetails = new String[teachers.size()];
      for (int i = 0; i < teachers.size(); i++) {
        teacherDetails[i] = "Name: " + teachers.get(i).getName() + ", ID: " + teachers.get(i).getTeacherID();
      }

      String selectedTeacher = (String) JOptionPane.showInputDialog(this,
          "Multiple teachers found. Select one:", "Select Teacher",
          JOptionPane.QUESTION_MESSAGE, null, teacherDetails, teacherDetails[0]);

      if (selectedTeacher == null) {
        return;
      }

      teacher = null;
      for (Teacher t : teachers) {
        if (selectedTeacher.contains(t.getTeacherID())) {
          teacher = t;
          break;
        }
      }

      if (teacher == null) {
        JOptionPane.showMessageDialog(this, "Invalid selection. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
    } else {
      teacher = teachers.get(0);
    }

    String courseID = JOptionPane.showInputDialog(this, "Enter course ID:");
    if (courseID == null || courseID.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Course ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Course course = university.findCourseByID(courseID);
    if (course == null) {
      JOptionPane.showMessageDialog(this, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (!teacher.getCoursesTaught().contains(course)) {
      JOptionPane.showMessageDialog(this, "This teacher does not teach the selected course.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    String studentName = JOptionPane.showInputDialog(this, "Enter student's name:");
    if (studentName == null || studentName.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Student name is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    List<Student> students = university.searchStudentByName(studentName);
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

    if (!course.getEnrolledStudents().contains(student)) {
      JOptionPane.showMessageDialog(this, "This student is not enrolled in the selected course.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    String gradeStr = JOptionPane.showInputDialog(this, "Enter grade to assign:");
    if (gradeStr == null || gradeStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Grade is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      double grade = Double.parseDouble(gradeStr);
      teacher.assignGrade(student, course, grade);
      JOptionPane.showMessageDialog(this, "Grade assigned successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid grade. Please enter a valid number.", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  // ==========================================================================================================================

  private void displayCoursesTeaching() {
    // Prompt for teacher name
    String name = JOptionPane.showInputDialog(this, "Enter your name:");
    if (name == null || name.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Name is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    List<Teacher> teachers = university.searchTeachersByName(name);
    if (teachers.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Teacher not found!", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Teacher teacher;

    if (teachers.size() > 1) {
      String[] teacherDetails = new String[teachers.size()];
      for (int i = 0; i < teachers.size(); i++) {
        teacherDetails[i] = "Name: " + teachers.get(i).getName() + ", ID: " + teachers.get(i).getTeacherID();
      }

      String selectedTeacher = (String) JOptionPane.showInputDialog(this,
          "Multiple teachers found. Select one:",
          "Select Teacher",
          JOptionPane.QUESTION_MESSAGE, null, teacherDetails, teacherDetails[0]);

      if (selectedTeacher == null) {
        return;
      }

      teacher = null;
      for (Teacher t : teachers) {
        if (selectedTeacher.contains(t.getTeacherID())) {
          teacher = t;
          break;
        }
      }

      if (teacher == null) {
        JOptionPane.showMessageDialog(this, "Invalid selection. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
    } else {
      teacher = teachers.get(0);
    }

    List<Course> courses = teacher.getCoursesTaught();
    StringBuilder coursesOutput = new StringBuilder("Courses taught by " + teacher.getName() + ":\n");

    if (courses.isEmpty()) {
      coursesOutput.append("No courses assigned.");
    } else {
      for (Course course : courses) {
        coursesOutput.append(course.toString()).append("\n");
      }
    }

    JOptionPane.showMessageDialog(this, coursesOutput.toString(), "Courses Teaching", JOptionPane.INFORMATION_MESSAGE);
  }

  // ==========================================================================================================================
  private void exportReportToFile() {

    String name = JOptionPane.showInputDialog(this, "Enter your name:");
    if (name == null || name.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Name is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    List<Teacher> teachers = university.searchTeachersByName(name);
    if (teachers.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No teacher found with the name " + name, "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Teacher teacher;

    if (teachers.size() > 1) {
      String[] teacherDetails = new String[teachers.size()];
      for (int i = 0; i < teachers.size(); i++) {
        teacherDetails[i] = "Name: " + teachers.get(i).getName() + ", ID: " + teachers.get(i).getTeacherID();
      }

      String selectedTeacher = (String) JOptionPane.showInputDialog(this,
          "Multiple teachers found. Select one:",
          "Select Teacher",
          JOptionPane.QUESTION_MESSAGE, null, teacherDetails, teacherDetails[0]);

      if (selectedTeacher == null) {
        return;
      }

      teacher = null;
      for (Teacher t : teachers) {
        if (selectedTeacher.contains(t.getTeacherID())) {
          teacher = t;
          break;
        }
      }

      if (teacher == null) {
        JOptionPane.showMessageDialog(this, "Invalid selection. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
    } else {
      teacher = teachers.get(0);
    }

    teacher.exportToFile();
    JOptionPane.showMessageDialog(this, "Report exported successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
  }

}
