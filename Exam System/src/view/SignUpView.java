package view;

import javax.swing.*;
import java.awt.*;
import controller.UserController;

public class SignUpView {
    private UserController controller;

    public SignUpView(UserController controller) {
        this.controller = controller;
    }

    public void displaySignUpPage() {
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1800); // Set frame size to 1920x1800 pixels
        JPanel panel = new JPanel(new GridBagLayout()); // Using GridBagLayout for better layout control
        panel.setBackground(new Color(255, 255, 255));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20, 20, 20, 20); // Increase insets for spacing

        // Add image above the heading
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\Sameeksha J S\\Downloads\\RecipeCatalogue\\RecipeCatalogue\\src\\view\\images.png"); // Path to your image
        JLabel imageLabel = new JLabel(imageIcon);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2; // Span across 2 columns
        panel.add(imageLabel, constraints);

        // Add a big heading "Sign Up" below the image
        JLabel headingLabel = new JLabel("Sign Up");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 40)); // Increase font size
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2; // Span across 2 columns
        panel.add(headingLabel, constraints);

        // Reset gridwidth
        constraints.gridwidth = 1;

        JButton studentButton = new JButton("Student Sign Up");
        JButton teacherButton = new JButton("Teacher Sign Up");
        JButton backButton = new JButton("Back");

        // Increase font size for buttons
        studentButton.setFont(new Font("Arial", Font.PLAIN, 30));
        teacherButton.setFont(new Font("Arial", Font.PLAIN, 30));
        backButton.setFont(new Font("Arial", Font.PLAIN, 30));

        // Set preferred size for buttons
        studentButton.setPreferredSize(new Dimension(300, 60)); // Increase button size
        teacherButton.setPreferredSize(new Dimension(300, 60)); // Increase button size
        backButton.setPreferredSize(new Dimension(300, 60)); // Increase button size

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(studentButton, constraints);

        constraints.gridx = 1;
        panel.add(teacherButton, constraints);

        // Place the "Back" button at the bottom
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(backButton, constraints);

        frame.add(panel);
        frame.setVisible(true);

        // Action listener for Student Sign Up button
        studentButton.addActionListener(e -> {
            frame.dispose(); // Close current frame
            controller.handleSignUp("student"); // Call controller to handle student sign up
        });

        // Action listener for Teacher Sign Up button
        teacherButton.addActionListener(e -> {
            frame.dispose(); // Close current frame
            controller.handleSignUp("teacher"); // Call controller to handle teacher sign up
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            LoginView loginView = new LoginView(controller);
            loginView.displayLoginPage();
        });
    }
}
