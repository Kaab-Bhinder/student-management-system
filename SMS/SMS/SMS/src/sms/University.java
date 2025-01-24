package sms;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

// UNIVERSITY CLASS
class University implements Serializable {
    private Repository<Student> studentRepo;
    private Repository<Teacher> teacherRepo;
    private Repository<Course> courseRepo;

    public University() {
        studentRepo = new Repository<>();
        teacherRepo = new Repository<>();
        courseRepo = new Repository<>();
    }

    public void addStudent(Student student) {
        if (student != null) {
            studentRepo.add(student);
        } else {
            System.out.println("Error: Cannot add null student.");
        }
    }

    public void addTeacher(Teacher teacher) {
        if (teacher != null) {
            teacherRepo.add(teacher);
        } else {
            System.out.println("Error: Cannot add null teacher.");
        }
    }

    public void addCourse(Course course) {
        if (course != null) {
            courseRepo.add(course);
        } else {
            System.out.println("Error: Cannot add null course.");
        }
    }

    public void removeStudent(Student student) {
        if (student != null) {
            studentRepo.remove(student);
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Error: Cannot remove null student.");
        }
    }

    public void removeTeacher(Teacher teacher) {
        if (teacher != null) {
            teacherRepo.remove(teacher);
            System.out.println("Teacher removed successfully.");
        } else {
            System.out.println("Error: Cannot remove null teacher.");
        }
    }

    public void removeCourse(Course course) {
        if (course != null) {
            courseRepo.remove(course);
            System.out.println("Course removed successfully.");
        } else {
            System.out.println("Error: Cannot remove null course.");
        }
    }

    public void displaySystemStats() {
        System.out.println("Total Students: " + studentRepo.getAll().size());
        System.out.println("Total Teachers: " + teacherRepo.getAll().size());
        System.out.println("Total Courses: " + courseRepo.getAll().size());
    }

    public void displayAllEntities() {
        System.out.println("Displaying all entities:");
        System.out.println("***STUDENTS***");
        studentRepo.displayAll();
        System.out.println("***TEACHERS***");
        teacherRepo.displayAll();
        System.out.println("***COURSES***");
        courseRepo.displayAll();
    }

    public List<Student> searchStudentByName(String name) {
        List<Student> results = new ArrayList<>();
        List<Student> students = studentRepo.getAll();
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                results.add(student);
            }
        }
        return results;
    }

    public List<Course> filterCoursesByCredits(int minCredits) {
        List<Course> results = new ArrayList<>();
        List<Course> courses = courseRepo.getAll();
        for (Course course : courses) {
            if (course.getCredits() >= minCredits) {
                results.add(course);
            }
        }
        return results;
    }

    public Course findCourseByID(String courseID) {
        List<Course> courses = courseRepo.getAll();
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            if (course.getCourseID().equals(courseID)) {
                return course;
            }
        }
        System.out.println("No course found with ID: " + courseID);
        return null;
    }

    public Teacher findTeacherByID(String teacherID) {
        List<Teacher> teachers = teacherRepo.getAll();
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getTeacherID().equalsIgnoreCase(teacherID)) {
                return teachers.get(i);
            }
        }
        System.out.println("No teacher found with ID: " + teacherID);
        return null;
    }

    public Student findStudentByID(String studentID) {
        List<Student> students = studentRepo.getAll();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentID().equalsIgnoreCase(studentID)) {
                return students.get(i);
            }
        }
        System.out.println("No student found with ID: " + studentID);
        return null;
    }

    public List<Teacher> searchTeachersByName(String name) {
        List<Teacher> results = new ArrayList<>();
        List<Teacher> teachers = teacherRepo.getAll();
        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            if (teacher.getName().equalsIgnoreCase(name)) {
                results.add(teacher);
            }
        }
        if (results.isEmpty()) {
            System.out.println("No teacher found with the name: " + name);
        }
        return results;
    }

    // Getter for Student Repository
    public Repository<Student> getStudentRepository() {
        return studentRepo;
    }

    // Getter for Teacher Repository
    public Repository<Teacher> getTeacherRepository() {
        return teacherRepo;
    }

    // Getter for Course Repository
    public Repository<Course> getCourseRepository() {
        return courseRepo;
    }

    public void displayAllCourses() {
        System.out.println("All Courses:");
        List<Course> courses = courseRepo.getAll();
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.println(course.toString());
        }
    }

    public void saveData() {
        try (ObjectOutputStream studentOut = new ObjectOutputStream(new FileOutputStream("students.txt", false));
                ObjectOutputStream teacherOut = new ObjectOutputStream(new FileOutputStream("teachers.txt", false));
                ObjectOutputStream courseOut = new ObjectOutputStream(new FileOutputStream("courses.txt", false))) {

            // Save serialized data
            studentOut.writeObject(studentRepo.getAll());
            teacherOut.writeObject(teacherRepo.getAll());
            courseOut.writeObject(courseRepo.getAll());

            System.out.println("Data saved successfully to separate files.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadData() {
        try (ObjectInputStream studentIn = new ObjectInputStream(new FileInputStream("students.txt"));
                ObjectInputStream teacherIn = new ObjectInputStream(new FileInputStream("teachers.txt"));
                ObjectInputStream courseIn = new ObjectInputStream(new FileInputStream("courses.txt"))) {

            studentRepo.setItems((List<Student>) studentIn.readObject());
            teacherRepo.setItems((List<Teacher>) teacherIn.readObject());
            courseRepo.setItems((List<Course>) courseIn.readObject());

            System.out.println("Data loaded successfully from separate files.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
