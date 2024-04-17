package view;

import javax.swing.*;
import controller.UserController;
import java.awt.*;

public class WelcomeTeacherView implements WelcomeView {
    private UserController controller;

    public WelcomeTeacherView(UserController controller) {
        this.controller = controller;
    }

    public void displayWelcomeMessage(String username) {
        JFrame frame = new JFrame("Welcome, " + username + "!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1980, 1800); // Set frame size
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(242, 242, 242));

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(236, 236, 236)); // Set background color
        sidePanel.setPreferredSize(new Dimension(250, frame.getHeight()));

        JButton teacherDetailsButton = new JButton("Teacher Details");
        JButton scheduleExamsButton = new JButton("Schedule Exams");
        JButton giveResultsButton = new JButton("Give Results");
        JButton viewScheduledExamsButton = new JButton("View Scheduled Exams"); // New button
        JButton logoutButton = new JButton("Logout");

        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        teacherDetailsButton.setFont(buttonFont);
        scheduleExamsButton.setFont(buttonFont);
        giveResultsButton.setFont(buttonFont);
        viewScheduledExamsButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);

        Dimension buttonSize = new Dimension(200, 40);
        teacherDetailsButton.setPreferredSize(buttonSize);
        scheduleExamsButton.setPreferredSize(buttonSize);
        giveResultsButton.setPreferredSize(buttonSize);
        viewScheduledExamsButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);

        // Set foreground color for buttons
        Color buttonForegroundColor = Color.BLUE;
        teacherDetailsButton.setForeground(buttonForegroundColor);
        scheduleExamsButton.setForeground(buttonForegroundColor);
        giveResultsButton.setForeground(buttonForegroundColor);
        viewScheduledExamsButton.setForeground(buttonForegroundColor);
        logoutButton.setForeground(buttonForegroundColor);

        // Add space between buttons
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(teacherDetailsButton);
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(scheduleExamsButton);
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(giveResultsButton);
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(viewScheduledExamsButton); // Add new button
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(logoutButton);
        sidePanel.add(Box.createVerticalGlue());

       

        mainPanel.add(sidePanel, BorderLayout.WEST);
        JPanel imagePanel = new JPanel(new BorderLayout());

        // Load your image
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Sameeksha J S\\Downloads\\RecipeCatalogue\\RecipeCatalogue\\src\\view\\download.jpeg");
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
        mainPanel.add(imagePanel, BorderLayout.EAST);
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel greetingLabel = new JLabel("Welcome, " + username + "!");
        contentPanel.add(greetingLabel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Action listeners for buttons
        teacherDetailsButton.addActionListener(e -> {
            String[][] teacherdetails = controller.displayteacherDetails();
            if (teacherdetails != null && teacherdetails.length > 0) {
                TeacherDetailsView teacherDetailsView = new TeacherDetailsView(controller);
                teacherDetailsView.displayteacherDetails(teacherdetails);
            } else {
                JOptionPane.showMessageDialog(null, "No Details found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        scheduleExamsButton.addActionListener(e -> {
            controller.handleScheduleExam();
        });

        viewScheduledExamsButton.addActionListener(e -> {
            controller.handleViewScheduledExams(); // Handle viewing scheduled exams
        });

        giveResultsButton.addActionListener(e -> {
            controller.handleGiveResults(); // Handle giving exam results
        });

        // Logout action
        logoutButton.addActionListener(e -> {
            frame.dispose();
            LoginView loginView = new LoginView(controller);
            loginView.displayLoginPage();
        });
    }
}
