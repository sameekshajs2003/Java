package view;


import javax.swing.*;
import java.awt.*;

import controller.PasswordChangeStrategy;
import controller.UserController;

public class TeacherDetailsView {
	private UserController controller;

    public TeacherDetailsView(UserController controller) {
        this.controller = controller;
        
    }
    
    public void setPasswordChangeStrategy(PasswordChangeStrategy strategy) {
        controller.setPasswordChangeStrategy(strategy);
    }


    public void displayteacherDetails(String[][] teacherDetails) {
        JFrame frame = new JFrame("Teacher Details");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600); // Set a reasonable size for the frame
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(new Color(255, 209, 209));
        
        // Adding heading for the page
        JLabel headingLabel = new JLabel("Teacher Details");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(headingLabel);

        for (String[] details : teacherDetails) {
            JPanel detailPanel = new JPanel(new BorderLayout());
            detailPanel.setBorder(BorderFactory.createEtchedBorder());
            
            JLabel teacherLabel = new JLabel("<html><b>IRN:</b> " + details[0] + "<br><br><br><b>Name:</b> " + details[1] + "<br><br><br><b>Department:</b> " + details[2] + "<br><br><br><b>Subject:</b> " + details[3] + "</html>");
            teacherLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            detailPanel.add(teacherLabel, BorderLayout.CENTER);

            // Add spacing between labels
            detailPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
            detailPanel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
            detailPanel.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
            detailPanel.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
            
            JButton changePassword = new JButton("Change Password");
            detailPanel.add(changePassword, BorderLayout.SOUTH);
            
            panel.add(detailPanel);
            
            // Action listener for register button
         // Inside displayStudentDetails method

         // Action listener for change password button
         changePassword.addActionListener(e -> {
             String newPassword = JOptionPane.showInputDialog(null, "Enter new password:");
             if (newPassword != null && !newPassword.isEmpty()) {
                 boolean passwordChanged = controller.changePassword(newPassword); // Assuming details[1] is username and details[2] is current password
                 if (passwordChanged) {
                     JOptionPane.showMessageDialog(null, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                 } else {
                     JOptionPane.showMessageDialog(null, "Failed to change password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                 }
             }
         });


        }
        
        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
