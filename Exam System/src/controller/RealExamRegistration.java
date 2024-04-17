package controller;
import model.UserModel;


public class RealExamRegistration implements ExamRegistration {
    private UserModel userModel;

    public RealExamRegistration(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public boolean registerExam(String examName, String examDate) {
        // Actual implementation of registering for an exam
        return userModel.registerExam(examName, examDate);
    }
}