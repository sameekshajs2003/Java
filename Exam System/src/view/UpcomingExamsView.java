package view;

import javax.swing.*;
import java.awt.*;
import controller.UserController;

public class UpcomingExamsView {
    private UserController controller;

    public UpcomingExamsView(UserController controller) {
        this.controller = controller;
    }

    public void displayUpcomingExams(String[][] upcomingExams) {
        JFrame frame = new JFrame("Upcoming Exams");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel examPanel = new JPanel();
        examPanel.setLayout(new GridLayout(0, 1, 10, 10)); // Grid layout for exam panels with gaps

        for (String[] exam : upcomingExams) {
            JPanel individualExamPanel = new JPanel(new BorderLayout());
            individualExamPanel.setBorder(BorderFactory.createEtchedBorder());

            JLabel examLabel = new JLabel("<html><b>Subject:</b> " + exam[0] + "<br><br><br><b>Type:</b> " + exam[1] + "<br><br><br><b>Date:</b> " + exam[2] + "<br><br><br><b>Duration:</b> " + exam[3] + " minutes<br><br><br><b>Total Marks:</b> " + exam[4] + "<br><br><br><b>Group:</b> " + exam[5] + "</html>");
            individualExamPanel.add(examLabel, BorderLayout.CENTER);

            JButton registerButton = new JButton("Register");
            individualExamPanel.add(registerButton, BorderLayout.SOUTH);

            examPanel.add(individualExamPanel);

            // Action listener for register button
            registerButton.addActionListener(e -> {
                String examName = exam[0]; // Get the exam name
                String examDate = exam[2]; // Get the exam date
                controller.registerExam(examName, examDate); // Call the registerExam method
            });
        }

        JScrollPane scrollPane = new JScrollPane(examPanel);
        mainPanel.add(scrollPane);

        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
    }
}
