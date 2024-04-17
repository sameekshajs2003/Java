package controller;

import javax.swing.*;
import model.UserModel;
import view.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class UserController {
    private UserModel userModel;
    private PasswordChangeStrategy passwordChangeStrategy; // Added
    private ExamRegistration examRegistrationProxy;

    public UserController(UserModel userModel) {
        this.userModel = userModel;RealExamRegistration realExamRegistration = new RealExamRegistration(userModel);
        this.examRegistrationProxy = new ExamRegistrationProxy(realExamRegistration);
        //this.examRegistrationProxy = new ExamRegistrationProxy(this);
    }
    public UserController(PasswordChangeStrategy passwordChangeStrategy) {
        this.passwordChangeStrategy = passwordChangeStrategy;
    }
    
        // Method to register an exam
    public void registerExam(String examName, String examDate) {
        boolean examRegistered = examRegistrationProxy.registerExam(examName, examDate);
        if (examRegistered) {
            JOptionPane.showMessageDialog(null, "Exam registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to register for the exam.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public String userType;

    public void handleLogin(String username, String password) {
        userType = userModel.authenticateUser(username, password);
        if (userType != null) {
            WelcomeView welcomeView = WelcomeViewFactory.createWelcomeView(userType, this);
            if (welcomeView != null) {
                welcomeView.displayWelcomeMessage(username);
            } else {
                // Handle error
            }
        } else {
            LoginView loginView = new LoginView(this);
            loginView.displayErrorMessage("Invalid username or password.");
        }
    }
    
    public boolean changePassword(String newPassword) {
    	 // Determine which password change strategy to set based on the userType
        if (userType.equalsIgnoreCase("student")) {
            setPasswordChangeStrategy(new StudentPasswordChangeStrategy(userModel));
        } else if (userType.equalsIgnoreCase("teacher")) {
            setPasswordChangeStrategy(new TeacherPasswordChangeStrategy(userModel));
        } else {
            throw new IllegalArgumentException("Invalid user type: " + userType);
        }
        return passwordChangeStrategy.changePassword(newPassword);
    }

    // Method to set the password change strategy
    public void setPasswordChangeStrategy(PasswordChangeStrategy strategy) {
        this.passwordChangeStrategy = strategy;
    }
    
    public boolean deleteGroup(String groupName) {
        // Call UserModel method to delete the group
        return userModel.deleteGroup(groupName);
    }

    public void handleSignUp(String userType) {
        if (userType.equalsIgnoreCase("student")) {
            StudentSignUpView studentSignUpView = new StudentSignUpView(this);
            studentSignUpView.displaySignUpPage();
        } else {
            TeacherSignUpView teacherSignUpView = new TeacherSignUpView(this);
            teacherSignUpView.displaySignUpPage();
        }
    }

    public void handleStudentSignUp(String username, String password,String srn,String dept, String sem) {
        int registered = userModel.registerUser(username, password,srn,dept,sem, "student");
        if (registered != -1) {
            LoginView loginView = new LoginView(this);
            loginView.displaySuccessMessage("Student registration successful. Please log in.");
        } else {
            StudentSignUpView studentSignUpView = new StudentSignUpView(this);
            studentSignUpView.displayErrorMessage("Failed to register. Please try again with a different username.");
        }
    }

    public void handleTeacherSignUp(String username, String password,String irn,String depti,String sub) {
        int registered = userModel.registerUser(username, password,irn,depti,sub, "teacher");
        if (registered != -1) {
            LoginView loginView = new LoginView(this);
            loginView.displaySuccessMessage("Teacher registration successful. Please log in.");
        } else {
            TeacherSignUpView teacherSignUpView = new TeacherSignUpView(this);
            teacherSignUpView.displayErrorMessage("Failed to register. Please try again with a different username.");
        }
    }

    // Method to fetch list of students
    public String[] getStudentsList() {
        // Call UserModel method to fetch students list from database
        return userModel.getStudentsList();
    }

    // Method to fetch list of instructors
    public String[] getInstructorsList() {
        // Call UserModel method to fetch instructors list from database
        return userModel.getInstructorsList();
    }
    
 // Add this method to handle scheduling of exams
    public void handleScheduleExam() {
        ScheduleExamView scheduleExamView = new ScheduleExamView(this);
        scheduleExamView.displayScheduleExamPage();
    }

    // Add this method to schedule an exam
    public void scheduleExam(String subject, String type, String dateScheduled, int duration, int totalMarks,String groupName) {
        // Call UserModel method to schedule exam in the database
        boolean examScheduled = userModel.scheduleExam(subject, type, dateScheduled, duration, totalMarks,groupName);
        if (examScheduled) {
            JOptionPane.showMessageDialog(null, "Exam scheduled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to schedule exam. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] getGroupNames() {
        // Call UserModel method to fetch group names from the database
        return userModel.getGroupNames();
    }
    
    public String[][] getUpcomingExams() {
        String[] userGroups = userModel.getUserGroups(); // Implement this method to get groups of the logged-in student
        if (userGroups != null && userGroups.length > 0) {
            List<String[]> upcomingExamsList = new ArrayList<>();
            for (String group : userGroups) {
                String[][] exams = userModel.getUpcomingExams(group);
                if (exams != null) {
                    upcomingExamsList.addAll(Arrays.asList(exams));
                }
            }
            return upcomingExamsList.toArray(new String[0][]);
        }
        return null;
    }
    
    public String[][] showstudentdetails() {
        String[] studentDetails = userModel.getstudentDetails();
        if (studentDetails != null) {
            String[][] result = {studentDetails};
            return result;
        } else {
            // Handle case where no student details found
            return null;
        }
    }
    
    public String[][] displayteacherDetails() {
        String[] teacherDetails = userModel.getteacherDetails();
        if (teacherDetails != null) {
            String[][] result = {teacherDetails};
            return result;
        } else {
            // Handle case where no student details found
            return null;
        }
    }
    
 // Inside UserController class

   

 // Add this method to handle giving exam results
    public void handleGiveResults() {
        GiveResultsView giveResultsView = new GiveResultsView(this);
        giveResultsView.displayGiveResultsPage();
    }

    // Add this method to get students in a group
    public String[] getStudentsInGroup(String groupName) {
        // Call UserModel method to fetch students in the specified group
        return userModel.getStudentsInGroup(groupName);
    }

    // Add this method to submit exam results
    public void submitExamResults(String groupName,String studentSrn, String examSubject, String examType, String examDate, int marks, String review) {
        // Call UserModel method to submit exam results
        boolean resultsSubmitted = userModel.submitExamResults(groupName,studentSrn, examSubject, examType, examDate, marks, review);
        if (resultsSubmitted) {
            JOptionPane.showMessageDialog(null, "Exam results submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to submit exam results. You are not in this group.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void fetchExamResults(String groupName, String examDate, String examSubject, String examType) {
        // Call the model layer method to retrieve and insert exam results
        userModel.getAndInsertExamResults(groupName, examDate, examSubject, examType);

        // Display success message
        JOptionPane.showMessageDialog(null, "Results published successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }



    // Method to create a group
    public void createGroup(String groupName, String[] selectedStudents, String selectedInstructor) {
        // Call UserModel method to create group in the database
        boolean groupCreated = userModel.createGroup(groupName, selectedStudents, selectedInstructor);
        if (groupCreated) {
            // Notify success
            JOptionPane.showMessageDialog(null, "Group created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Notify failure
            JOptionPane.showMessageDialog(null, "Failed to create group. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void handleViewScheduledExams() {
        String[][] scheduledExams = userModel.getScheduledExams(); // Call UserModel method to get scheduled exams
        // Display scheduled exams in a dialog
        if (scheduledExams != null && scheduledExams.length > 0) {
            StringBuilder dialogMessage = new StringBuilder();
            dialogMessage.append("<html><body><p>Scheduled Exams:</p><ul>");
            for (String[] exam : scheduledExams) {
                dialogMessage.append("<li>Subject: ").append(exam[0]).append(", Type: ").append(exam[1]).append(", Date: ").append(exam[2]).append(", Duration: ").append(exam[3]).append(" minutes, Total Marks: ").append(exam[4]).append(", Group: ").append(exam[5]).append("</li>");
            }
            dialogMessage.append("</ul></body></html>");
            JOptionPane.showMessageDialog(null, dialogMessage.toString(), "Scheduled Exams", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No scheduled exams found.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void handleViewResults() {
        String[][] examResults = userModel.getExamResults();
        ViewResultsView viewResultsView = new ViewResultsView();
        viewResultsView.displayResults(examResults);
    }
    
   

    public String[][] getGroupsAndStudents() {
        // Call UserModel method to fetch existing groups and associated students
        return userModel.getGroupsAndStudents();
    }
}
