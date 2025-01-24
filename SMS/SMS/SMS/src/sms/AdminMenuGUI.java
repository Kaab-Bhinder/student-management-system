package sms;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminMenuGUI extends JFrame {

  private University university;
  private AdministrativeStaff admin;

  public AdminMenuGUI(University university, AdministrativeStaff admin) {
    this.university = university;
    this.admin = admin;

    setTitle("Administrative Staff Menu");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setPreferredSize(new Dimension(500, 400));
    setLayout(new GridLayout(12, 1, 10, 10));

    JButton addTeacherButton = new JButton("Add Teacher");
    JButton removeTeacherButton = new JButton("Remove Teacher");
    JButton addStudentButton = new JButton("Add Student");
    JButton removeStudentButton = new JButton("Remove Student");
    JButton addCourseButton = new JButton("Add Course");
    JButton removeCourseButton = new JButton("Remove Course");
    JButton assignCourseToTeacherButton = new JButton("Assign Course to Teacher");
    JButton calculateAvgGradeButton = new JButton("Calculate Average Grade for a Course");
    JButton calculateMedianGradeButton = new JButton("Calculate Median Grade for a Course");
    JButton generateReportButton = new JButton("Generate Report");
    JButton exportReportButton = new JButton("Export Report to File");
    JButton backButton = new JButton("Return to Main Menu");

    add(addTeacherButton);
    add(removeTeacherButton);
    add(addStudentButton);
    add(removeStudentButton);
    add(addCourseButton);
    add(removeCourseButton);
    add(assignCourseToTeacherButton);
    add(calculateAvgGradeButton);
    add(calculateMedianGradeButton);
    add(generateReportButton);
    add(exportReportButton);
    add(backButton);

    addTeacherButton.addActionListener(e -> addTeacher());
    removeTeacherButton.addActionListener(e -> removeTeacher());
    addStudentButton.addActionListener(e -> addStudent());
    removeStudentButton.addActionListener(e -> removeStudent());
    addCourseButton.addActionListener(e -> addCourse());
    removeCourseButton.addActionListener(e -> removeCourse());
    assignCourseToTeacherButton.addActionListener(e -> assignCourseToTeacher());
    calculateAvgGradeButton.addActionListener(e -> calculateAverageGradeForCourse());
    calculateMedianGradeButton.addActionListener(e -> calculateMedianGradeForCourse());
    generateReportButton.addActionListener(e -> generateReport());
    exportReportButton.addActionListener(e -> exportAdministrativeReportToFile());
    backButton.addActionListener(e -> dispose());

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  // ============================================================================================================================
  private void addTeacher() {
    String teacherID = JOptionPane.showInputDialog(this, "Enter Teacher ID:");
    if (teacherID == null || teacherID.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Teacher ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String name = JOptionPane.showInputDialog(this, "Enter Name:");
    if (name == null || name.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Name is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String email = JOptionPane.showInputDialog(this, "Enter Email:");
    if (email == null || email.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Email is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String dob = JOptionPane.showInputDialog(this, "Enter Date of Birth (DD-MM-YYYY):");
    if (dob == null || dob.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Date of Birth is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
    String[] dateParts = dob.split("-");
    if (dateParts.length != 3) {
      JOptionPane.showMessageDialog(this, "Invalid date format. Please use DD-MM-YYYY.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }
    int day, month, year;
    try {
      day = Integer.parseInt(dateParts[0]);
      month = Integer.parseInt(dateParts[1]);
      year = Integer.parseInt(dateParts[2]);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid date. Please ensure all parts are numbers.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (month < 1 || month > 12) {
      JOptionPane.showMessageDialog(this, "Invalid month! Please enter a value between 01 and 12.", "Error",
          JOptionPane.ERROR_MESSAGE);
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
      JOptionPane.showMessageDialog(this,
          "Invalid day for the selected month! Please enter a day between 01 and " + maxDays + ".", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    String houseNumberStr = JOptionPane.showInputDialog(this, "Enter House Number (int):");
    if (houseNumberStr == null || houseNumberStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "House number is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    int houseNumber;
    try {
      houseNumber = Integer.parseInt(houseNumberStr);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid house number.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String city = JOptionPane.showInputDialog(this, "Enter City:");
    if (city == null || city.isEmpty()) {
      JOptionPane.showMessageDialog(this, "City is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Address address = new Address(houseNumber, city);

    String specialization = JOptionPane.showInputDialog(this, "Enter Specialization:");
    if (specialization == null || specialization.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Specialization is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Teacher teacher = new Teacher(name, teacherID, email, dob, address, specialization);
    university.addTeacher(teacher);
    JOptionPane.showMessageDialog(this, "Teacher added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  // ============================================================================================================================
  private void removeTeacher() {
    List<Teacher> teachers = university.getTeacherRepository().getAll();
    if (teachers.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No teachers available to remove.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String[] teacherList = new String[teachers.size()];
    for (int i = 0; i < teachers.size(); i++) {
      teacherList[i] = teachers.get(i).getName() + " (ID: " + teachers.get(i).getTeacherID() + ")";
    }

    String selectedTeacher = (String) JOptionPane.showInputDialog(this, "Select a Teacher to remove:", "Remove Teacher",
        JOptionPane.QUESTION_MESSAGE, null, teacherList, teacherList[0]);
    if (selectedTeacher == null) {
      return;
    }

    Teacher teacherToRemove = null;
    for (Teacher teacher : teachers) {
      if (selectedTeacher.contains(teacher.getTeacherID())) {
        teacherToRemove = teacher;
        break;
      }
    }

    if (teacherToRemove != null) {
      university.removeTeacher(teacherToRemove);
      JOptionPane.showMessageDialog(this, "Teacher " + teacherToRemove.getName() + " removed successfully.",
          "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(this, "Teacher not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // ============================================================================================================================
  private void addStudent() {
    String studentID = JOptionPane.showInputDialog(this, "Enter Student ID:");
    if (studentID == null || studentID.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Student ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String name = JOptionPane.showInputDialog(this, "Enter Name:");
    if (name == null || name.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Name is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String email = JOptionPane.showInputDialog(this, "Enter Email:");
    if (email == null || email.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Email is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String dob = JOptionPane.showInputDialog(this, "Enter Date of Birth (DD-MM-YYYY):");
    if (dob == null || dob.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Date of Birth is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String[] dateParts = dob.split("-");
    if (dateParts.length != 3) {
      JOptionPane.showMessageDialog(this, "Invalid date format. Please use DD-MM-YYYY.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    int day, month, year;
    try {
      day = Integer.parseInt(dateParts[0]);
      month = Integer.parseInt(dateParts[1]);
      year = Integer.parseInt(dateParts[2]);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid date. Please ensure all parts are numbers.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (month < 1 || month > 12) {
      JOptionPane.showMessageDialog(this, "Invalid month! Please enter a value between 01 and 12.", "Error",
          JOptionPane.ERROR_MESSAGE);
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
      JOptionPane.showMessageDialog(this,
          "Invalid day for the selected month! Please enter a day between 01 and " + maxDays + ".", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    String houseNumberStr = JOptionPane.showInputDialog(this, "Enter House Number (int):");
    if (houseNumberStr == null || houseNumberStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "House number is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    int houseNumber;
    try {
      houseNumber = Integer.parseInt(houseNumberStr);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid house number.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String city = JOptionPane.showInputDialog(this, "Enter City:");
    if (city == null || city.isEmpty()) {
      JOptionPane.showMessageDialog(this, "City is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Address address = new Address(houseNumber, city);
    Student student = new Student(studentID, name, email, dob, address);
    university.addStudent(student);
    JOptionPane.showMessageDialog(this, "Student added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  // ================================================================================================================================

  private void removeStudent() {
    List<Student> students = university.getStudentRepository().getAll();
    if (students.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No students available to remove.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String[] studentList = new String[students.size()];
    for (int i = 0; i < students.size(); i++) {
      studentList[i] = students.get(i).getName() + " (ID: " + students.get(i).getStudentID() + ")";
    }

    String selectedStudent = (String) JOptionPane.showInputDialog(this, "Select a Student to remove:", "Remove Student",
        JOptionPane.QUESTION_MESSAGE, null, studentList, studentList[0]);
    if (selectedStudent == null) {
      return;
    }

    Student studentToRemove = null;
    for (Student student : students) {
      if (selectedStudent.contains(student.getStudentID())) {
        studentToRemove = student;
        break;
      }
    }

    if (studentToRemove != null) {
      university.removeStudent(studentToRemove);
      JOptionPane.showMessageDialog(this, "Student " + studentToRemove.getName() + " removed successfully.",
          "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(this, "Student not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // ================================================================================================================================
  private void addCourse() {
    String courseID = JOptionPane.showInputDialog(this, "Enter Course ID:");
    if (courseID == null || courseID.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Course ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String title = JOptionPane.showInputDialog(this, "Enter Course Title:");
    if (title == null || title.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Course Title is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String creditsStr = JOptionPane.showInputDialog(this, "Enter Credits:");
    if (creditsStr == null || creditsStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Credits are required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
    int credits;
    try {
      credits = Integer.parseInt(creditsStr);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid credits. Please enter a valid number.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }
    List<Teacher> teachers = university.getTeacherRepository().getAll();
    if (teachers.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No teachers available. Please add teachers first.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }
    String[] teacherNames = new String[teachers.size()];
    for (int i = 0; i < teachers.size(); i++) {
      teacherNames[i] = teachers.get(i).getName() + " (ID: " + teachers.get(i).getTeacherID() + ")";
    }
    String selectedTeacherName = (String) JOptionPane.showInputDialog(this, "Select a Teacher for this Course:",
        "Select Teacher",
        JOptionPane.QUESTION_MESSAGE, null, teacherNames, teacherNames[0]);
    if (selectedTeacherName == null) {
      return;
    }
    Teacher selectedTeacher = null;
    for (Teacher teacher : teachers) {
      if (selectedTeacherName.contains(teacher.getTeacherID())) {
        selectedTeacher = teacher;
        break;
      }
    }
    if (selectedTeacher == null) {
      JOptionPane.showMessageDialog(this, "Selected teacher not found. Please try again.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }
    Course course = new Course(courseID, title, credits, selectedTeacher);
    selectedTeacher.assignCourse(course);
    university.addCourse(course);
    JOptionPane.showMessageDialog(this, "Course added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  // ================================================================================================================================
  private void removeCourse() {
    List<Course> courses = university.getCourseRepository().getAll();
    if (courses.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No courses available to remove.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String[] courseList = new String[courses.size()];
    for (int i = 0; i < courses.size(); i++) {
      courseList[i] = courses.get(i).getCourseTitle() + " (ID: " + courses.get(i).getCourseID() + ")";
    }

    String selectedCourse = (String) JOptionPane.showInputDialog(this, "Select a course to remove:", "Remove Course",
        JOptionPane.QUESTION_MESSAGE, null, courseList, courseList[0]);
    if (selectedCourse == null) {
      return;
    }
    Course courseToRemove = null;
    for (Course course : courses) {
      if (selectedCourse.contains(course.getCourseID())) {
        courseToRemove = course;
        break;
      }
    }

    if (courseToRemove != null) {
      university.removeCourse(courseToRemove);
      JOptionPane.showMessageDialog(this, "Course " + courseToRemove.getCourseTitle() + " removed successfully.",
          "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(this, "Course not found. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  // ============================================================================================================================

  private void assignCourseToTeacher() {
    List<Course> courses = university.getCourseRepository().getAll();
    if (courses.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No courses available to assign.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String[] courseList = new String[courses.size()];
    for (int i = 0; i < courses.size(); i++) {
      courseList[i] = courses.get(i).getCourseTitle() + " (ID: " + courses.get(i).getCourseID() + ")";
    }

    String selectedCourseStr = (String) JOptionPane.showInputDialog(this, "Select a Course to Assign:", "Assign Course",
        JOptionPane.QUESTION_MESSAGE, null, courseList, courseList[0]);
    if (selectedCourseStr == null) {
      return;
    }

    Course selectedCourse = null;
    for (Course course : courses) {
      if (selectedCourseStr.contains(course.getCourseID())) {
        selectedCourse = course;
        break;
      }
    }

    List<Teacher> teachers = university.getTeacherRepository().getAll();
    if (teachers.isEmpty()) {
      JOptionPane.showMessageDialog(this, "No teachers available. Please add teachers first.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    String[] teacherList = new String[teachers.size()];
    for (int i = 0; i < teachers.size(); i++) {
      teacherList[i] = teachers.get(i).getName() + " (ID: " + teachers.get(i).getTeacherID() + ")";
    }

    String selectedTeacherStr = (String) JOptionPane.showInputDialog(this, "Select a Teacher to Assign the Course:",
        "Assign Teacher", JOptionPane.QUESTION_MESSAGE, null, teacherList, teacherList[0]);
    if (selectedTeacherStr == null) {
      return;
    }

    Teacher selectedTeacher = null;
    for (Teacher teacher : teachers) {
      if (selectedTeacherStr.contains(teacher.getTeacherID())) {
        selectedTeacher = teacher;
        break;
      }
    }

    if (selectedCourse != null && selectedTeacher != null) {
      selectedTeacher.assignCourse(selectedCourse);
      JOptionPane.showMessageDialog(this,
          "Course " + selectedCourse.getCourseTitle() + " assigned successfully to Teacher: "
              + selectedTeacher.getName(),
          "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(this, "Assignment failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // ============================================================================================================================
  private void calculateAverageGradeForCourse() {
    String courseID = JOptionPane.showInputDialog(this, "Enter the Course ID to calculate the average grade:");
    if (courseID == null || courseID.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Course ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Course course = university.findCourseByID(courseID);
    if (course != null) {
      double averageGrade = course.calculateAverageGrade();
      if (averageGrade == -1) {
        JOptionPane.showMessageDialog(this, "No valid grades to calculate the average for this course.",
            "Average Grade", JOptionPane.INFORMATION_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(this, "The average grade for course " + course.getCourseTitle() + " is: "
            + averageGrade, "Average Grade", JOptionPane.INFORMATION_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(this, "Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // ============================================================================================================================
  private void calculateMedianGradeForCourse() {
    String courseID = JOptionPane.showInputDialog(this, "Enter the Course ID to calculate the median grade:");
    if (courseID == null || courseID.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Course ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Course course = university.findCourseByID(courseID);
    if (course != null) {
      double medianGrade = course.calculateMedianGrade();
      if (medianGrade == -1) {
        JOptionPane.showMessageDialog(this, "No valid grades to calculate the median for this course.",
            "Median Grade", JOptionPane.INFORMATION_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(this, "The median grade for course " + course.getCourseTitle() + " is: "
            + medianGrade, "Median Grade", JOptionPane.INFORMATION_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(this, "Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // ============================================================================================================================
  private void generateReport() {
    List<Object> entities = new ArrayList<>();
    entities.addAll(university.getStudentRepository().getAll());
    entities.addAll(university.getTeacherRepository().getAll());
    entities.addAll(university.getCourseRepository().getAll());

    String report = admin.generateReport(entities);
    if (report != null && !report.isEmpty()) {
      JTextArea reportArea = new JTextArea(report);
      reportArea.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(reportArea);

      JOptionPane.showMessageDialog(this, scrollPane, "Administrative Report", JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(this, "No data available to generate a report.", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  // ============================================================================================================================
  private void exportAdministrativeReportToFile() {
    try {
      admin.exportToFile();
      JOptionPane.showMessageDialog(this, "Administrative report exported successfully.",
          "Export Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this,
          "An error occurred while exporting the administrative report:\n" + e.getMessage(),
          "Export Error", JOptionPane.ERROR_MESSAGE);
    }
  }

}
