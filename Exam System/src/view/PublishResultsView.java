package view;

import javax.swing.*;
import controller.UserController;
import java.awt.*;

public class PublishResultsView {
    private UserController controller;

    public PublishResultsView(UserController controller) {
        this.controller = controller;
    }

    public void displayGiveResultsPage() {
        JFrame frame = new JFrame("Give Exam Results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400); // Adjusted frame size for better visibility
        

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Set background color

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // Added spacing between components
        inputPanel.setBackground(Color.WHITE); // Set background color

        JLabel groupNameLabel = new JLabel("Group Name:");
        JTextField groupNameField = new JTextField();
        JLabel examDateLabel = new JLabel("Exam Date:");
        JTextField examDateField = new JTextField();
        JLabel examSubjectLabel = new JLabel("Exam Subject:");
        JTextField examSubjectField = new JTextField();
        JLabel examTypeLabel = new JLabel("Exam Type:");
        JTextField examTypeField = new JTextField();

        // Add labels and input fields to inputPanel
        inputPanel.add(groupNameLabel);
        inputPanel.add(groupNameField);
        inputPanel.add(examDateLabel);
        inputPanel.add(examDateField);
        inputPanel.add(examSubjectLabel);
        inputPanel.add(examSubjectField);
        inputPanel.add(examTypeLabel);
        inputPanel.add(examTypeField);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set button font
        submitButton.setBackground(new Color(0, 123, 255)); // Set button background color
        submitButton.setForeground(Color.WHITE); // Set button text color

        // Add action listener to submit button
        submitButton.addActionListener(e -> {
            String groupName = groupNameField.getText();
            String examDate = examDateField.getText();
            String examSubject = examSubjectField.getText();
            String examType = examTypeField.getText();

            // Call controller method to fetch results
            controller.fetchExamResults(groupName, examDate, examSubject, examType);
        });

        // Add inputPanel and submitButton to mainPanel
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(submitButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
