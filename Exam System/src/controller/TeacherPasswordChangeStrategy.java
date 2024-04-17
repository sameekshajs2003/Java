package controller;

import model.UserModel;

public class TeacherPasswordChangeStrategy implements PasswordChangeStrategy {
    private UserModel userModel;

    public TeacherPasswordChangeStrategy(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public boolean changePassword(String newPassword) {
        return userModel.changePassword( newPassword,"teacher");
    }
}