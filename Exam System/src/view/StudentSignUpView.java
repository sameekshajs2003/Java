package view;

import javax.swing.*;
import java.awt.*;
import controller.UserController;

public class StudentSignUpView {
    private UserController controller;

    public StudentSignUpView(UserController controller) {
        this.controller = controller;
    }

    public void displaySignUpPage() {
        JFrame frame = new JFrame("Student Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1800);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(149,166,203));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20, 20, 20, 20);

        JLabel headingLabel = new JLabel("Student Sign Up");
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
        JLabel srnLabel = new JLabel("SRN Number:");
        JTextField srnText = new JTextField(30);
        srnText.setPreferredSize(new Dimension(srnText.getPreferredSize().width, 40));
        JLabel deptLabel = new JLabel("Department Name:");
        JTextField deptText = new JTextField(30);
        deptText.setPreferredSize(new Dimension(deptText.getPreferredSize().width, 40));
        JLabel semLabel = new JLabel("SEM:");
        JTextField semText = new JTextField(30);
        semText.setPreferredSize(new Dimension(semText.getPreferredSize().width, 40));
        JButton signUpButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back");

        userLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        srnLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        deptLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        semLabel.setFont(new Font("Arial", Font.PLAIN, 30));
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
        panel.add(srnLabel, constraints);

        constraints.gridx = 1;
        panel.add(srnText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(deptLabel, constraints);

        constraints.gridx = 1;
        panel.add(deptText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(semLabel, constraints);

        constraints.gridx = 1;
        panel.add(semText, constraints);

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
            String srn = srnText.getText();
            String dept = deptText.getText();
            String sem = semText.getText();
            controller.handleStudentSignUp(username, password, srn, dept, sem);
        });
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
