package view;

import javax.swing.*;
import controller.UserController;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminPageView {
    private UserController controller;

    public AdminPageView(UserController controller) {
        this.controller = controller;
    }

    public void displayAdminPage() {
        JFrame frame = new JFrame("Admin Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1980, 1800); // Set frame size
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(242, 242, 242));

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color	(236,236,236)); // Set background color
        sidePanel.setPreferredSize(new Dimension(250, frame.getHeight()));

        JButton viewGroupsButton = new JButton("View Groups");
        JButton createGroupsButton = new JButton("Create Groups");
        JButton deleteGroupsButton = new JButton("Delete Groups");
        JButton giveResultsButton = new JButton("Publish Results");
        JButton logoutButton = new JButton("Logout");

        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        viewGroupsButton.setFont(buttonFont);
        createGroupsButton.setFont(buttonFont);
        deleteGroupsButton.setFont(buttonFont);
        giveResultsButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);

        Dimension buttonSize = new Dimension(200, 40);
        viewGroupsButton.setPreferredSize(buttonSize);
        createGroupsButton.setPreferredSize(buttonSize);
        deleteGroupsButton.setPreferredSize(buttonSize);
        giveResultsButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);

        // Set foreground color for buttons
        Color buttonForegroundColor = Color.BLUE;
        viewGroupsButton.setForeground(buttonForegroundColor);
        createGroupsButton.setForeground(buttonForegroundColor);
        deleteGroupsButton.setForeground(buttonForegroundColor);
        giveResultsButton.setForeground(buttonForegroundColor);
        logoutButton.setForeground(buttonForegroundColor);

        
        // Add space between buttons
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(viewGroupsButton);
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(createGroupsButton);
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(deleteGroupsButton);
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(giveResultsButton);
        sidePanel.add(Box.createVerticalStrut(40)); // Increased space
        sidePanel.add(logoutButton);
        sidePanel.add(Box.createVerticalGlue());

        mainPanel.add(sidePanel, BorderLayout.WEST);
        JPanel imagePanel = new JPanel(new BorderLayout());

        // Load your image
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Sameeksha J S\\Downloads\\RecipeCatalogue\\RecipeCatalogue\\src\\view\\cl.jpg");
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

        // Logout action
        logoutButton.addActionListener(e -> {
            frame.dispose();
            LoginView loginView = new LoginView(controller);
            loginView.displayLoginPage();
        });

        // Action listener for create groups button
        createGroupsButton.addActionListener(e -> {
            // Display group creation dialog
            String groupName = JOptionPane.showInputDialog(frame, "Enter group name:");
            if (groupName != null && !groupName.isEmpty()) {
                // Fetch list of students and instructors from the controller
                String[] students = controller.getStudentsList();
                String[] instructors = controller.getInstructorsList();

                // Show selection dialog for students and instructor
                JCheckBox[] studentCheckBoxes = new JCheckBox[students.length];
                for (int i = 0; i < students.length; i++) {
                    studentCheckBoxes[i] = new JCheckBox(students[i]);
                }
                JPanel studentPanel = new JPanel(new GridLayout(0, 1));
                for (JCheckBox checkBox : studentCheckBoxes) {
                    studentPanel.add(checkBox);
                }
                JScrollPane studentScrollPane = new JScrollPane(studentPanel);
                studentScrollPane.setPreferredSize(new Dimension(300, 200));

                JOptionPane.showMessageDialog(frame, studentScrollPane, "Select Students", JOptionPane.PLAIN_MESSAGE);

                // Collect selected students
                List<String> selectedStudents = new ArrayList<>();
                for (JCheckBox checkBox : studentCheckBoxes) {
                    if (checkBox.isSelected()) {
                        selectedStudents.add(checkBox.getText());
                    }
                }

                // Show selection dialog for instructor
                String selectedInstructor = (String) JOptionPane.showInputDialog(frame, "Select an instructor:",
                        "Select Instructor", JOptionPane.QUESTION_MESSAGE, null, instructors, instructors[0]);

                // Call controller method to create the group
                controller.createGroup(groupName, selectedStudents.toArray(new String[0]), selectedInstructor);
            } else {
                JOptionPane.showMessageDialog(frame, "Group name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        deleteGroupsButton.addActionListener(e -> {
            // Display group deletion dialog
            String groupName = JOptionPane.showInputDialog(frame, "Enter group name to delete:");
            if (groupName != null && !groupName.isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the group?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Call controller method to delete the group
                    boolean groupDeleted = controller.deleteGroup(groupName);
                    if (groupDeleted) {
                        JOptionPane.showMessageDialog(frame, "Group deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to delete group. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Group name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        

        // Add action listeners for other buttons as needed
        // For example:
        viewGroupsButton.addActionListener(e -> {
            // Fetch existing groups and associated students from the controller
            String[][] groupsAndStudents = controller.getGroupsAndStudents();

            // Display the groups and associated students
            if (groupsAndStudents != null && groupsAndStudents.length > 0) {
                StringBuilder message = new StringBuilder();
                for (String[] group : groupsAndStudents) {
                    String groupName = group[0];
                    String[] students = group[1].split(",");
                    message.append("Group: ").append(groupName).append("\n");
                    message.append("Students: ");
                    for (String student : students) {
                        message.append(student).append(", ");
                    }
                    // Remove the trailing comma and space
                    message.delete(message.length() - 2, message.length());
                    message.append("\n\n");
                }
                JOptionPane.showMessageDialog(frame, message.toString(), "Existing Groups", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No groups found.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        giveResultsButton.addActionListener(e -> {
            PublishResultsView publishResultsView = new PublishResultsView(controller);
            publishResultsView.displayGiveResultsPage();
        });

    }
}
