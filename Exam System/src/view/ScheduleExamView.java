package view;
import java.awt.*;

import javax.swing.*;
import controller.UserController;

public class ScheduleExamView {
    private UserController controller;
    private JFrame frame;

    public ScheduleExamView(UserController controller) {
        this.controller = controller;
    }

    public void displayScheduleExamPage() {
        frame = new JFrame("Schedule Exam");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2, 10, 10)); // Grid layout for input fields with gaps

        // Add input fields for exam details with labels
        JLabel subjectLabel = new JLabel("Subject:");
        JTextField subjectField = new JTextField(20);
        inputPanel.add(subjectLabel);
        inputPanel.add(subjectField);

        JLabel typeLabel = new JLabel("Type:");
        JTextField typeField = new JTextField(20);
        inputPanel.add(typeLabel);
        inputPanel.add(typeField);

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField(20);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);

        JLabel durationLabel = new JLabel("Duration (in minutes):");
        JTextField durationField = new JTextField(20);
        inputPanel.add(durationLabel);
        inputPanel.add(durationField);

        JLabel totalMarksLabel = new JLabel("Total Marks:");
        JTextField totalMarksField = new JTextField(20);
        inputPanel.add(totalMarksLabel);
        inputPanel.add(totalMarksField);

        // Add dropdown menu for group selection
        JLabel groupLabel = new JLabel("Group Name:");
        String[] groupNames = controller.getGroupNames(); // Fetch group names from UserModel
        JComboBox<String> groupDropdown = new JComboBox<>(groupNames);
        inputPanel.add(groupLabel);
        inputPanel.add(groupDropdown);

        // Create button to submit exam details
        JButton submitButton = new JButton("Schedule Exam");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(0, 123, 255)); // Set button background color
        submitButton.setForeground(Color.WHITE);

        submitButton.addActionListener(e -> {
            String subject = subjectField.getText();
            String type = typeField.getText();
            String date = dateField.getText();
            int duration = Integer.parseInt(durationField.getText());
            int totalMarks = Integer.parseInt(totalMarksField.getText());
            String groupName = (String) groupDropdown.getSelectedItem();

            // Call UserController method to schedule exam
            controller.scheduleExam(subject, type, date, duration, totalMarks, groupName);
        });

        // Add components to main panel
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(submitButton, BorderLayout.SOUTH);

        // Add main panel to frame
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
