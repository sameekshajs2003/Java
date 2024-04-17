package controller;

import model.UserModel;

public class StudentPasswordChangeStrategy implements PasswordChangeStrategy {
    private UserModel userModel;

    public StudentPasswordChangeStrategy(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public boolean changePassword(String newPassword) {
        return userModel.changePassword( newPassword,"student");
    }
}
