package sms;

import javax.swing.*;
import java.awt.*;

public class StudentManagementSystemGUI extends JFrame {

  private University university;
  private AdministrativeStaff admin;

  public StudentManagementSystemGUI() {
    this.university = new University();
    this.admin = new AdministrativeStaff("Admin", "admin@example.com", "01-01-1980", 1, "Administrator",
        "Administration");

    setTitle("Student Management System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(500, 400));
    setResizable(false);

    JPanel mainMenuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
    JLabel titleLabel = new JLabel("Student Management System", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    mainMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JButton studentButton = new JButton("STUDENT");
    JButton teacherButton = new JButton("TEACHER");
    JButton adminButton = new JButton("ADMIN STAFF");
    JButton exitButton = new JButton("EXIT");

    mainMenuPanel.add(titleLabel);
    mainMenuPanel.add(studentButton);
    mainMenuPanel.add(teacherButton);
    mainMenuPanel.add(adminButton);
    mainMenuPanel.add(exitButton);

    studentButton.addActionListener(e -> new StudentMenuGUI(university).setVisible(true));
    teacherButton.addActionListener(e -> new TeacherMenuGUI(university).setVisible(true));
    adminButton.addActionListener(e -> new AdminMenuGUI(university, admin).setVisible(true));

    exitButton.addActionListener(e -> {
      university.saveData();
      System.exit(0);
    });

    getContentPane().add(mainMenuPanel, BorderLayout.CENTER);

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public static void main(String[] args) {
    StudentManagementSystemGUI sms = new StudentManagementSystemGUI();
    sms.university.loadData();
  }
}
