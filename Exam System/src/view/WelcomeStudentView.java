package view;

import javax.swing.*;
import java.awt.*;
import controller.UserController;

public class WelcomeStudentView implements WelcomeView{
    private UserController controller;

    public WelcomeStudentView(UserController controller) {
        this.controller = controller;
    }

    public void displayWelcomeMessage(String username) {
        JFrame frame = new JFrame("Welcome, " + username + "!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1800); // Set frame size
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(242, 242, 242));

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(236, 236, 236)); // Set background color
        sidePanel.setPreferredSize(new Dimension(250, frame.getHeight()));

        JButton studentDetailsButton = new JButton("Student Details");
        JButton upcomingExamsButton = new JButton("View Upcoming Exams");
        JButton resultsButton = new JButton("View Results");
        JButton backButton = new JButton("Back");

        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        studentDetailsButton.setFont(buttonFont);
        upcomingExamsButton.setFont(buttonFont);
        resultsButton.setFont(buttonFont);
        backButton.setFont(buttonFont);

        Dimension buttonSize = new Dimension(200, 40);
        studentDetailsButton.setPreferredSize(buttonSize);
        upcomingExamsButton.setPreferredSize(buttonSize);
        resultsButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        Color buttonForegroundColor = Color.BLUE;
        studentDetailsButton.setForeground(buttonForegroundColor);
        upcomingExamsButton.setForeground(buttonForegroundColor);
        resultsButton.setForeground(buttonForegroundColor);
        backButton.setForeground(buttonForegroundColor);

        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(studentDetailsButton);
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(upcomingExamsButton);
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(resultsButton);
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(backButton);
        sidePanel.add(Box.createVerticalGlue());

        mainPanel.add(sidePanel, BorderLayout.WEST);

        JPanel imagePanel = new JPanel(new BorderLayout());
        // Load your image
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Sameeksha J S\\Downloads\\RecipeCatalogue\\RecipeCatalogue\\src\\view\\st1.jpg");
        Image originalImage = originalIcon.getImage();

        // Get the screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Calculate the scaling factors
        double scaleWidth = (double) screenWidth / originalIcon.getIconWidth();
        double scaleHeight = (double) screenHeight / originalIcon.getIconHeight();
        double scale = Math.min(scaleWidth, scaleHeight);

        // Resize the image
        Image scaledImage = originalImage.getScaledInstance((int) (originalIcon.getIconWidth() * scale), (int) (originalIcon.getIconHeight() * scale), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create a JLabel with the scaled image
        JLabel imageLabel = new JLabel(scaledIcon);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        mainPanel.add(imagePanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose();
            LoginView loginView = new LoginView(controller);
            loginView.displayLoginPage();
        });

        studentDetailsButton.addActionListener(e -> {
            String[][] studentDetails = controller.showstudentdetails();
            if (studentDetails != null && studentDetails.length > 0) {
                StudentDetailsView studentDetailsView = new StudentDetailsView(controller);
                studentDetailsView.displayStudentDetails(studentDetails);
            } else {
                JOptionPane.showMessageDialog(null, "No Details found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        upcomingExamsButton.addActionListener(e -> {
            String[][] upcomingExams = controller.getUpcomingExams();
            if (upcomingExams != null && upcomingExams.length > 0) {
                UpcomingExamsView upcomingExamsView = new UpcomingExamsView(controller);
                upcomingExamsView.displayUpcomingExams(upcomingExams);
            } else {
                JOptionPane.showMessageDialog(null, "No upcoming exams found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        resultsButton.addActionListener(e -> {
            controller.handleViewResults();
        });
    }
}
