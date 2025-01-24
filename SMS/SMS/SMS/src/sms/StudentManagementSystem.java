package sms;

import java.util.*;

public class StudentManagementSystem {
  public static void main(String[] args) {
    University university = new University();
    Scanner scanner = new Scanner(System.in);
    AdministrativeStaff admin = new AdministrativeStaff("Admin", "admin@example.com", "01-01-1980", 1, "Administrator",
        "Administration");

    university.loadData();
    while (true) {
      System.out.println("\nWelcome to the University Management System");
      System.out.println("1. Teacher");
      System.out.println("2. Student");
      System.out.println("3. Administrative Staff");
      System.out.println("4. Exit");
      System.out.print("Please select your role: ");
      int role = scanner.nextInt();
      switch (role) {
        case 1:
          teacherMenu(scanner, university);
          break;
        case 2:
          studentMenu(scanner, university);
          break;
        case 3:
          administrativeStaffMenu(scanner, university, admin);
          break;
        case 4:
          // Before exiting, save data to a file or database
          university.saveData();
          System.out.println("Exiting the system...");
          scanner.close();
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  // ******************************************************************************************************************
  private static void studentMenu(Scanner scanner, University university) {
    while (true) {
      System.out.println("\n--- Student Menu ---");
      System.out.println("1. Enroll In A Course");
      System.out.println("2. See Grades");
      System.out.println("3. See Courses You Are Enrolled In");
      System.out.println("4. Search for courses by minimum credits");
      System.out.println("5. Return to Main Menu");
      System.out.print("Select an option: ");
      int choice = scanner.nextInt();
      switch (choice) {
        case 1:
          enrollInCourse(scanner, university);
          break;
        case 2:
          seeGrades(scanner, university);
          break;
        case 3:
          seeEnrolledCourses(scanner, university);
          break;
        case 4:
          searchCoursesByCredits(scanner, university);
          break;
        case 5:
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
          break;
      }
    }
  }

  // ------------------------------------------------------------------------------
  private static void enrollInCourse(Scanner scanner, University university) {
    System.out.print("Enter your name: ");
    scanner.nextLine();
    String name = scanner.nextLine();
    // Search for students by name
    List<Student> students = university.searchStudentByName(name);
    if (students.isEmpty()) {
      System.out.println("No student found with the name " + name);
      return;
    }

    Student student;

    // If multiple students found, display them and ask for ID
    if (students.size() > 1) {
      System.out.println("Multiple students found with the name " + name + ":");
      for (Student s : students) {
        System.out.println(s.toString());
      }
      System.out.print("Enter the student ID to proceed: ");
      String studentID = scanner.next();

      // Search for student by ID
      student = university.findStudentByID(studentID);
      if (student == null) {
        System.out.println("No student found with the ID " + studentID);
        return;
      }
    } else {
      // If only one student is found, select them
      student = students.get(0);
    }

    // Display all available courses
    System.out.println("Available Courses:");
    university.displayAllCourses();

    // Enroll in a course
    System.out.print("Enter course ID to enroll: ");
    String courseID = scanner.next();
    Course course = university.findCourseByID(courseID);
    if (course == null) {
      System.out.println("Course not found!");
      return;
    }

    // Enroll the student in the selected course
    student.enrollInCourse(course);
    System.out.println("Enrolled in course successfully.");
  }

  // ------------------------------------------------------------------------------
  private static void seeGrades(Scanner scanner, University university) {
    System.out.print("Enter your name: ");
    scanner.nextLine(); // Consume newline left-over
    String name = scanner.nextLine();

    // Search for students by name
    List<Student> students = university.searchStudentByName(name);
    if (students.isEmpty()) {
      System.out.println("Student not found!");
      return;
    }

    Student student;

    // If multiple students are found, ask for ID
    if (students.size() > 1) {
      System.out.println("Multiple students found with the name " + name + ":");
      for (Student s : students) {
        System.out.println(s.toString()); // Display student details (use toString)
      }
      System.out.print("Enter the student ID to proceed: ");
      String studentID = scanner.next();

      // Search for student by ID
      student = university.findStudentByID(studentID);
      if (student == null) {
        System.out.println("No student found with the ID " + studentID);
        return;
      }
    } else {
      // If only one student is found, select that student
      student = students.get(0);
    }

    // Display grades for the selected student
    student.displayGrades();
  }

  // ------------------------------------------------------------------------------
  private static void seeEnrolledCourses(Scanner scanner, University university) {
    System.out.print("Enter your name for student: ");
    scanner.nextLine(); // Consume newline left-over
    String name = scanner.nextLine();

    // Search for students by name
    List<Student> students = university.searchStudentByName(name);
    if (students.isEmpty()) {
      System.out.println("Student not found!");
      return;
    }

    Student student;
    // If multiple students are found, ask for ID
    if (students.size() > 1) {
      System.out.println("Multiple students found with the name " + name + ":");
      for (Student s : students) {
        System.out.println(s.toString()); // Display student details (use toString)
      }
      System.out.print("Enter the student ID to proceed: ");
      String studentID = scanner.next();

      // Search for student by ID
      student = university.findStudentByID(studentID);
      if (student == null) {
        System.out.println("No student found with the ID " + studentID);
        return;
      }
    } else {
      // If only one student is found, select that student
      student = students.get(0);
    }
    // Display courses for the selected student
    student.displayCourses();
  }

  // -------------------------------------------------------------------------------------------
  private static void searchCoursesByCredits(Scanner scanner, University university) {
    System.out.print("Enter minimum credit for course: ");
    int minCredits = scanner.nextInt(); // Reading the minimum credit requirement
    List<Course> courses = university.filterCoursesByCredits(minCredits); // Filtering courses by credits

    if (!courses.isEmpty()) {
      System.out.println("Courses with at least " + minCredits + " credits:");
      for (int i = 0; i < courses.size(); i++) {
        Course course = courses.get(i);
        System.out.println(course.toString());
        System.out.println();
      }
    } else {
      System.out.println("No courses found with the minimum credit requirement of " + minCredits);
    }
  }

  // ********************************************************************************************
  private static void teacherMenu(Scanner scanner, University university) {
    while (true) {
      System.out.println("\n--- Teacher Menu ---");
      System.out.println("1. Assign grade to student");
      System.out.println("2. Display all courses which you are teaching");
      System.out.println("3. Export report to file");
      System.out.println("4. Return to Main Menu");
      System.out.print("Select an option: ");
      int choice = scanner.nextInt();

      switch (choice) {
        case 1:
          assignGradeToStudent(scanner, university);
          break;
        case 2:
          displayCoursesTeaching(scanner, university);
          break;
        case 3:
          exportReportToFile(scanner, university);
          break;
        case 4:
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
          break;
      }
    }
  }

  private static void exportReportToFile(Scanner scanner, University university) {
    System.out.print("Enter your name for the teacher: ");
    scanner.nextLine();
    String name = scanner.nextLine();
    List<Teacher> teachers = university.searchTeachersByName(name);
    if (teachers.isEmpty()) {
      System.out.println("No teacher found with the name " + name);
      return;
    }
    Teacher teacher;
    if (teachers.size() > 1) {
      System.out.println("Multiple teachers found with the name " + name + ":");
      for (int i = 0; i < teachers.size(); i++) {
        System.out.println((i + 1) + ". " + teachers.get(i).toString());
      }
      System.out.print("Enter the teacher ID to proceed: ");
      String teacherID = scanner.next();
      teacher = university.findTeacherByID(teacherID);
      if (teacher == null) {
        System.out.println("No teacher found with the ID " + teacherID);
        return;
      }
    } else {
      teacher = teachers.get(0);
    }
    teacher.exportToFile();
    System.out.println("Report exported successfully.");
  }

  // ------------------------------------------------------------------------------------
  private static void assignGradeToStudent(Scanner scanner, University university) {
    System.out.print("Enter your name for teacher: ");
    scanner.nextLine();
    String teacherName = scanner.nextLine();
    List<Teacher> teachers = university.searchTeachersByName(teacherName);
    if (teachers.isEmpty()) {
      System.out.println("Teacher not found!");
      return;
    }
    Teacher teacher;
    if (teachers.size() > 1) {
      System.out.println("Multiple teachers found with the name " + teacherName + ":");
      for (Teacher t : teachers) {
        System.out.println(t.toString());
      }
      System.out.print("Enter the teacher ID to proceed: ");
      String teacherID = scanner.next();
      teacher = university.findTeacherByID(teacherID);
      if (teacher == null) {
        System.out.println("No teacher found with the ID " + teacherID);
        return;
      }
    } else {
      teacher = teachers.get(0);
    }
    System.out.print("Enter student's name to assign grade: ");
    scanner.nextLine();
    String studentName = scanner.nextLine();
    List<Student> students = university.searchStudentByName(studentName);
    if (students.isEmpty()) {
      System.out.println("Student not found!");
      return;
    }
    Student student;
    if (students.size() > 1) {
      System.out.println("Multiple students found with the name " + studentName + ":");
      for (Student s : students) {
        System.out.println(s.toString());
      }
      System.out.print("Enter the student ID to proceed: ");
      String studentID = scanner.next();

      student = university.findStudentByID(studentID);
      if (student == null) {
        System.out.println("No student found with the ID " + studentID);
        return;
      }
    } else {
      student = students.get(0);
    }

    System.out.print("Enter course ID for which grade is to be assigned: ");
    String courseID = scanner.next();
    Course course = university.findCourseByID(courseID);
    if (course == null) {
      System.out.println("Course not found!");
      return;
    }

    System.out.print("Enter grade to assign: ");
    double grade = scanner.nextDouble();

    teacher.assignGrade(student, course, grade);
    System.out.println("Grade assigned successfully.");
  }

  // ---------------------------------------------------------------------------------------
  private static void displayCoursesTeaching(Scanner scanner, University university) {
    System.out.print("Enter your name for teacher: ");
    scanner.nextLine();
    String name = scanner.nextLine();

    List<Teacher> teachers = university.searchTeachersByName(name);
    if (teachers.isEmpty()) {
      System.out.println("Teacher not found!");
      return;
    }

    Teacher teacher;
    if (teachers.size() > 1) {
      System.out.println("Multiple teachers found with the name " + name + ":");
      for (Teacher t : teachers) {
        System.out.println(t.toString());
      }
      System.out.print("Enter the teacher ID to proceed: ");
      String teacherID = scanner.next();
      teacher = university.findTeacherByID(teacherID);
      if (teacher == null) {
        System.out.println("No teacher found with the ID " + teacherID);
        return;
      }
    } else {
      teacher = teachers.get(0);
    }
    teacher.displayCourses();
  }

  // **********************************************************************************************
  private static void administrativeStaffMenu(Scanner scanner, University university, AdministrativeStaff admin) {
    int choice;
    do {
      System.out.println("\n--- Administrative Staff Menu ---");
      System.out.println("1. Add Teacher");
      System.out.println("2. Remove Teacher");
      System.out.println("3. Add Student");
      System.out.println("4. Remove Student");
      System.out.println("5. Add Course");
      System.out.println("6. Remove Course");
      System.out.println("7. Assign Course to Teacher");
      System.out.println("8. Calculate Average Grade for a Course");
      System.out.println("9. Calculate Median Grade for a Course");
      System.out.println("10. Generate Report");
      System.out.println("11. Export Report to File");
      System.out.println("12. Return to Main Menu");
      System.out.print("Select an option: ");
      choice = scanner.nextInt();
      scanner.nextLine();

      switch (choice) {
        case 1:
          addTeacher(scanner, university);
          break;
        case 2:
          removeTeacher(scanner, university);
          break;
        case 3:
          addStudent(scanner, university);
          break;
        case 4:
          removeStudent(scanner, university);
          break;
        case 5:
          addCourse(scanner, university);
          break;
        case 6:
          removeCourse(scanner, university);
          break;
        case 7:
          assignCourseToTeacher(scanner, university);
          break;
        case 8:
          calculateAverageGradeForCourse(scanner, university);
          break;
        case 9:
          calculateMedianGradeForCourse(scanner, university);
          break;
        case 10:
          generateReport(university, admin);
          break;
        case 11:
          exportAdministrativeReportToFile(admin);
          break;
        case 12:
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    } while (choice != 12);
  }

  // ---------------------------------------------------------------------------------------
  private static void addTeacher(Scanner scanner, University university) {
    System.out.print("Enter Teacher ID: ");
    String teacherID = scanner.nextLine();
    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter Email: ");
    String email = scanner.nextLine();
    System.out.print("Enter Date of Birth (DD-MM-YYYY): ");
    String dob = scanner.nextLine();
    System.out.print("Enter House Number(int): ");
    int house = scanner.nextInt();
    System.out.print("Enter City: ");
    String city = scanner.nextLine();
    Address address = new Address(house, city);
    System.out.print("Enter Specialization: ");
    String specialization = scanner.nextLine();
    Teacher teacher = new Teacher(name, teacherID, email, dob, address, specialization);
    university.addTeacher(teacher);
    System.out.println("Teacher added successfully.");
  }

  // ---------------------------------------------------------------------------------------
  private static void removeTeacher(Scanner scanner, University university) {
    System.out.println("Available Teachers:");
    List<Teacher> teachers = university.getTeacherRepository().getAll();
    if (teachers.isEmpty()) {
      System.out.println("No teachers available to remove.");
      return;
    }
    for (int i = 0; i < teachers.size(); i++) {
      System.out.println((i + 1) + ". " + teachers.get(i).getName() + " (ID: " + teachers.get(i).getTeacherID() + ")");
    }
    System.out.print("Enter the number of the teacher you want to remove: ");
    int teacherNumber = scanner.nextInt();
    if (teacherNumber < 1 || teacherNumber > teachers.size()) {
      System.out.println("Invalid teacher number. No teacher removed.");
      return;
    }
    Teacher teacher = teachers.get(teacherNumber - 1);
    university.removeTeacher(teacher);
    System.out.println("Teacher " + teacher.getName() + " removed successfully.");
  }

  // ---------------------------------------------------------------------------------------
  private static void addStudent(Scanner scanner, University university) {
    System.out.print("Enter Student ID: ");
    String studentID = scanner.nextLine();
    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter Email: ");
    String email = scanner.nextLine();
    System.out.print("Enter Date of Birth (DD-MM-YYYY): ");
    String dob = scanner.nextLine();
    System.out.print("Enter House Number(int): ");
    int house = scanner.nextInt();
    System.out.print("Enter City: ");
    String city = scanner.nextLine();

    Address address = new Address(house, city);
    Student student = new Student(studentID, name, email, dob, address);
    university.addStudent(student);
    System.out.println("Student added successfully.");
  }

  // ---------------------------------------------------------------------------------------
  private static void removeStudent(Scanner scanner, University university) {
    System.out.println("Available Students:");
    List<Student> students = university.getStudentRepository().getAll();
    if (students.isEmpty()) {
      System.out.println("No students available to remove.");
      return;
    }
    for (int i = 0; i < students.size(); i++) {
      System.out.println((i + 1) + ". " + students.get(i).getName() + " (ID: " + students.get(i).getStudentID() + ")");
    }
    System.out.print("Enter the number of the student you want to remove: ");
    int studentNumber = scanner.nextInt();
    if (studentNumber < 1 || studentNumber > students.size()) {
      System.out.println("Invalid student number. No student removed.");
      return;
    }
    Student student = students.get(studentNumber - 1);
    university.removeStudent(student);
    System.out.println("Student " + student.getName() + " removed successfully.");
  }

  // ---------------------------------------------------------------------------------------
  private static void addCourse(Scanner scanner, University university) {
    System.out.print("Enter Course ID: ");
    String courseID = scanner.nextLine();
    System.out.print("Enter Course Title: ");
    String title = scanner.nextLine();
    System.out.print("Enter Credits: ");
    int credits = scanner.nextInt();
    scanner.nextLine();
    System.out.println("Select a Teacher for this Course:");
    List<Teacher> teachers = university.getTeacherRepository().getAll();
    if (teachers.isEmpty()) {
      System.out.println("No teachers available. Please add teachers first.");
      return;
    }
    for (int i = 0; i < teachers.size(); i++) {
      System.out.println((i + 1) + ". " + teachers.get(i).toString());
    }
    System.out.print("Enter Teacher Number: ");
    int teacherChoice = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    if (teacherChoice < 1 || teacherChoice > teachers.size()) {
      System.out.println("Invalid teacher selection.");
      return;
    }
    Teacher selectedTeacher = teachers.get(teacherChoice - 1);
    Course course = new Course(courseID, title, credits, selectedTeacher);
    university.addCourse(course);
    System.out.println("Course added successfully.");
  }

  // ---------------------------------------------------------------------------------------
  private static void removeCourse(Scanner scanner, University university) {
    System.out.println("Available Courses:");
    List<Course> courses = university.getCourseRepository().getAll();
    if (courses.isEmpty()) {
      System.out.println("No courses available to remove.");
      return;
    }
    for (int i = 0; i < courses.size(); i++) {
      System.out
          .println((i + 1) + ". " + courses.get(i).getCourseTitle() + " (ID: " + courses.get(i).getCourseID() + ")");
    }
    System.out.print("Enter the number of the course you want to remove: ");
    int courseNumber = scanner.nextInt();
    if (courseNumber < 1 || courseNumber > courses.size()) {
      System.out.println("Invalid course number. No course removed.");
      return;
    }
    Course course = courses.get(courseNumber - 1);
    university.removeCourse(course);
    System.out.println("Course " + course.getCourseTitle() + " removed successfully.");
  }

  // ---------------------------------------------------------------------------------------
  private static void assignCourseToTeacher(Scanner scanner, University university) {
    System.out.println("Select a Course to Assign:");
    List<Course> courses = university.getCourseRepository().getAll();
    for (int i = 0; i < courses.size(); i++) {
      System.out
          .println((i + 1) + ". " + courses.get(i).getCourseTitle() + " (ID: " + courses.get(i).getCourseID() + ")");
    }

    System.out.print("Enter Course Number: ");
    int courseChoice = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    if (courseChoice < 1 || courseChoice > courses.size()) {
      System.out.println("Invalid course selection.");
      return;
    }

    Course selectedCourse = courses.get(courseChoice - 1);

    System.out.println("Select a Teacher to Assign the Course:");
    List<Teacher> teachers = university.getTeacherRepository().getAll();
    for (int i = 0; i < teachers.size(); i++) {
      System.out.println((i + 1) + ". " + teachers.get(i).getName() + " (ID: " + teachers.get(i).getTeacherID() + ")");
    }

    System.out.print("Enter Teacher Number: ");
    int teacherChoice = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    if (teacherChoice < 1 || teacherChoice > teachers.size()) {
      System.out.println("Invalid teacher selection.");
      return;
    }

    Teacher selectedTeacher = teachers.get(teacherChoice - 1);
    selectedTeacher.assignCourse(selectedCourse);
    System.out.println("Course assigned successfully to Teacher: " + selectedTeacher.getName());
  }

  // ---------------------------------------------------------------------------------------
  private static void generateReport(University university, AdministrativeStaff admin) {
    List<Object> entities = new ArrayList<>();
    entities.addAll(university.getStudentRepository().getAll());
    entities.addAll(university.getTeacherRepository().getAll());
    entities.addAll(university.getCourseRepository().getAll());

    String report = admin.generateReport(entities);
    System.out.println("\n=== Administrative Report ===");
    System.out.println(report);
  }

  // ---------------------------------------------------------------------------------------
  private static void calculateAverageGradeForCourse(Scanner scanner, University university) {
    System.out.print("Enter the Course ID to calculate the average grade: ");
    String courseID = scanner.nextLine();
    Course course = university.findCourseByID(courseID);

    if (course != null) {
      double averageGrade = course.calculateAverageGrade();
      if (averageGrade == -1) {
        System.out.println("No valid grades to calculate the average for this course.");
      } else {
        System.out.println("The average grade for course " + course.getCourseTitle() + " is: " + averageGrade);
      }
    } else {
      System.out.println("Course not found.");
    }
  }

  // ---------------------------------------------------------------------------------------
  private static void calculateMedianGradeForCourse(Scanner scanner, University university) {
    System.out.print("Enter the Course ID to calculate the median grade: ");
    String courseID = scanner.nextLine();
    Course course = university.findCourseByID(courseID);

    if (course != null) {
      double medianGrade = course.calculateMedianGrade();
      if (medianGrade == -1) {
        System.out.println("No valid grades to calculate the median for this course.");
      } else {
        System.out.println("The median grade for course " + course.getCourseTitle() + " is: " + medianGrade);
      }
    } else {
      System.out.println("Course not found.");
    }
  }

  // ---------------------------------------------------------------------------------------
  private static void exportAdministrativeReportToFile(AdministrativeStaff admin) {
    admin.exportToFile();
    System.out.println("Administrative report exported successfully.");
  }

}
