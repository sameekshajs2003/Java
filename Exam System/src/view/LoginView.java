package view;

import javax.swing.*;
import java.awt.*;
import controller.UserController;

import model.UserModel;

public class LoginView {

    private UserController controller;

    public LoginView(UserController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserController userController = new UserController(new UserModel());
            LoginView loginView = new LoginView(userController);
            loginView.displayLoginPage();
        });
    }
  

    public void displayLoginPage() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1800); // Set frame size to 1920x1800 pixels
        JPanel panel = new JPanel(new GridBagLayout()); // Using GridBagLayout for better layout control
        panel.setBackground(new Color(212,235,252));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20, 20, 20, 20); // Increase insets for spacing

        // Add a big heading "Exam Management System" above username
        JLabel headingLabel = new JLabel("Exam Management System");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 40)); // Increase font size
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2; // Span across 2 columns
        panel.add(headingLabel, constraints);

        // Reset gridwidth
        constraints.gridwidth = 1;

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField(30); // Increase text field size
        userText.setPreferredSize(new Dimension(userText.getPreferredSize().width, 40));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField(30); // Increase password field size
        passwordText.setPreferredSize(new Dimension(passwordText.getPreferredSize().width, 40));
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        // Increase font size for labels and buttons
        userLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        loginButton.setFont(new Font("Arial", Font.PLAIN, 30));
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 30));

        // Set preferred size for buttons
        loginButton.setPreferredSize(new Dimension(250, 60)); // Increase button size
        signUpButton.setPreferredSize(new Dimension(250, 60)); // Increase button size

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(userLabel, constraints);

        constraints.gridx = 1;
        panel.add(userText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        panel.add(passwordText, constraints);

        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(loginButton, constraints);

        constraints.gridy = 4;
        panel.add(signUpButton, constraints);

        frame.add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            if (username.equals("admin") && password.equals("admin")) {
                frame.dispose(); // Close current frame
                AdminPageView adminPageView = new AdminPageView(controller);
                adminPageView.displayAdminPage();
            } else {
                controller.handleLogin(username, password);
            }
        });

        // Action listener for Sign Up button
        signUpButton.addActionListener(e -> {
            frame.dispose(); // Close current frame
            SignUpView signUpView = new SignUpView(controller);
            signUpView.displaySignUpPage();
        });
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void displaySuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
