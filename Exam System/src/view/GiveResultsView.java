package view;


import javax.swing.*;
import java.awt.*;
import controller.UserController;

public class GiveResultsView {
    private UserController controller;

    public GiveResultsView(UserController controller) {
        this.controller = controller;
    }

    public void displayGiveResultsPage() {
        JFrame frame = new JFrame("Give Exam Results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2, 10, 10)); // Grid layout for input fields with gaps

        // Create input fields
        JLabel groupNameLabel = new JLabel("Group Name:");
        JTextField groupNameField = new JTextField(20);
        JButton fetchStudentsButton = new JButton("Fetch Students");
        JLabel selectStudentLabel = new JLabel("Select Student:");
        JComboBox<String> studentComboBox = new JComboBox<>();
        JLabel examSubjectLabel = new JLabel("Exam Subject:");
        JTextField examSubjectField = new JTextField(20);
        JLabel examTypeLabel = new JLabel("Exam Type:");
        JTextField examTypeField = new JTextField(20);
        JLabel examDateLabel = new JLabel("Exam Date (YYYY-MM-DD):");
        JTextField examDateField = new JTextField(20);
        JLabel marksLabel = new JLabel("Marks:");
        JTextField marksField = new JTextField(20);
        JLabel reviewLabel = new JLabel("Review:");
        JTextArea reviewArea = new JTextArea(5, 20);
        JScrollPane reviewScrollPane = new JScrollPane(reviewArea);

        // Add components to input panel
        inputPanel.add(groupNameLabel);
        inputPanel.add(groupNameField);
        inputPanel.add(fetchStudentsButton);
        inputPanel.add(new JPanel()); // Empty panel for alignment
        inputPanel.add(selectStudentLabel);
        inputPanel.add(studentComboBox);
        inputPanel.add(examSubjectLabel);
        inputPanel.add(examSubjectField);
        inputPanel.add(examTypeLabel);
        inputPanel.add(examTypeField);
        inputPanel.add(examDateLabel);
        inputPanel.add(examDateField);
        inputPanel.add(marksLabel);
        inputPanel.add(marksField);
        inputPanel.add(reviewLabel);
        inputPanel.add(reviewScrollPane);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");
        buttonsPanel.add(submitButton);
        buttonsPanel.add(cancelButton);

        // Add components to main panel
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Action listener for fetch students button
        fetchStudentsButton.addActionListener(e -> {
            String groupName = groupNameField.getText();
            if (!groupName.isEmpty()) {
                String[] students = controller.getStudentsInGroup(groupName);
                studentComboBox.removeAllItems();
                for (String student : students) {
                    studentComboBox.addItem(student);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a group name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action listener for submit button
        submitButton.addActionListener(e -> {
            String groupName = groupNameField.getText();
            String studentSrn = (String) studentComboBox.getSelectedItem();
            String examSubject = examSubjectField.getText();
            String examType = examTypeField.getText();
            String examDate = examDateField.getText();
            String marksStr = marksField.getText();
            String review = reviewArea.getText();

            if (!examSubject.isEmpty() && !examType.isEmpty() && !examDate.isEmpty() && !marksStr.isEmpty() && !review.isEmpty()) {
                int marks = Integer.parseInt(marksStr);
                controller.submitExamResults(groupName,studentSrn, examSubject, examType, examDate, marks, review);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action listener for cancel button
        cancelButton.addActionListener(e -> {
            frame.dispose();
        });
    }
}
