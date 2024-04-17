package controller;

import javax.swing.*;


public class ExamRegistrationProxy implements ExamRegistration {
    private RealExamRegistration realExamRegistration;

    public ExamRegistrationProxy(RealExamRegistration realExamRegistration) {
        this.realExamRegistration = realExamRegistration;
    }

    @Override
    public boolean registerExam(String examName, String examDate) {
        // Ask for human confirmation
        boolean isHuman = confirmHuman();

        // If confirmed human, proceed with registration
        if (isHuman) {
            return realExamRegistration.registerExam(examName, examDate);
        } else {
            JOptionPane.showMessageDialog(null, "You must confirm that you are human to register for the exam.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Method to confirm human
    private boolean confirmHuman() {
        // Create a checkbox to confirm human
        JCheckBox confirmCheckbox = new JCheckBox("I confirm that I am human");

        // Show a dialog with the checkbox
        int choice = JOptionPane.showConfirmDialog(null, confirmCheckbox, "Confirm Human", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        // Check if the checkbox is selected and OK button is clicked
        return choice == JOptionPane.OK_OPTION && confirmCheckbox.isSelected();
    }
}

