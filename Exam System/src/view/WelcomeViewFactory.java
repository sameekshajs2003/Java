package view;

import controller.UserController;

public class WelcomeViewFactory {
    public static WelcomeView createWelcomeView(String userType, UserController controller) {
        if (userType != null) {
            if (userType.equals("student")) {
                return new WelcomeStudentView(controller);
            } else if (userType.equals("teacher")) {
                return new WelcomeTeacherView(controller);
            }
        }
        // Default behavior, you can return null or throw an exception
        return null;
    }
}
