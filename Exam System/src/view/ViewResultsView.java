package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewResultsView {
    private JFrame frame;
    private JTable resultsTable;
    private DefaultTableModel tableModel;

    public ViewResultsView() {
        frame = new JFrame("View Results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        // Initialize table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Exam Date");
        tableModel.addColumn("Subject");
        tableModel.addColumn("Type");
        tableModel.addColumn("Marks");
        tableModel.addColumn("Review");

        // Create table with initialized model
        resultsTable = new JTable(tableModel);
        resultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(resultsTable);

        // Add table to main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Set frame content
        frame.add(mainPanel);
    }

    // Method to display results in the table
    public void displayResults(String[][] results) {
        tableModel.setRowCount(0); // Clear previous rows

        if (results != null && results.length > 0) {
            for (String[] result : results) {
                tableModel.addRow(result);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No results found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to dispose the frame
    public void dispose() {
        frame.dispose();
    }
}
