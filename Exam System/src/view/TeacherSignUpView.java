package view;

import javax.swing.*;
import java.awt.*;
import controller.UserController;

public class TeacherSignUpView {
    private UserController controller;

    public TeacherSignUpView(UserController controller) {
        this.controller = controller;
    }

    public void displaySignUpPage() {
        JFrame frame = new JFrame("Teacher Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1800);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(149,166,203));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20, 20, 20, 20);

        JLabel headingLabel = new JLabel("Teacher Sign Up");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 40));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(headingLabel, constraints);

        constraints.gridwidth = 1;

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField(30);
        userText.setPreferredSize(new Dimension(userText.getPreferredSize().width, 40));
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField(30);
        passwordText.setPreferredSize(new Dimension(passwordText.getPreferredSize().width, 40));
        JLabel irnLabel = new JLabel("Instructor Number:");
        JTextField irnText = new JTextField(30);
        irnText.setPreferredSize(new Dimension(irnText.getPreferredSize().width, 40));
        JLabel deptiLabel = new JLabel("Department:");
        JTextField deptiText = new JTextField(30);
        deptiText.setPreferredSize(new Dimension(deptiText.getPreferredSize().width, 40));
        JLabel subLabel = new JLabel("Subject:");
        JTextField subText = new JTextField(30);
        subText.setPreferredSize(new Dimension(subText.getPreferredSize().width, 40));
        JButton signUpButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back");

        userLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        irnLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        deptiLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        subLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 30));
        backButton.setFont(new Font("Arial", Font.PLAIN, 30));

        signUpButton.setPreferredSize(new Dimension(250, 60));
        backButton.setPreferredSize(new Dimension(250, 60));

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

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(irnLabel, constraints);

        constraints.gridx = 1;
        panel.add(irnText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(deptiLabel, constraints);

        constraints.gridx = 1;
        panel.add(deptiText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(subLabel, constraints);

        constraints.gridx = 1;
        panel.add(subText, constraints);

        constraints.gridy = 6;
        constraints.gridwidth = 2;
        panel.add(signUpButton, constraints);

        constraints.gridy = 7;
        panel.add(backButton, constraints);

        frame.add(panel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        backButton.addActionListener(e -> {
            frame.dispose();
            SignUpView signUpView = new SignUpView(controller);
            signUpView.displaySignUpPage();
        });

        signUpButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            String irn = irnText.getText();
            String depti = deptiText.getText();
            String sub = subText.getText();
            controller.handleTeacherSignUp(username, password, irn, depti, sub);
        });
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
