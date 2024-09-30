import survey_system.surveySystem.Survey;
import survey_system.surveySystem.SurveyLogger;
import survey_system.userLogin.UserLogin;
import survey_system.utilities.Input;

import java.util.Scanner;

class demo {
    public static void main(String[] args) {
        UserLogin user = new UserLogin();
        Scanner s = new Scanner(System.in);
        while (!user.isLogged()) {
            System.out.print("1 for login\n2 for sign in\n");
            int choice = Input.getChoice(s,1,2,"Enter your choice: ");
            switch (choice) {
                case 1:
                    while (!user.isLogged()) {
                        System.out.print("Enter userId: ");
                        String userId = s.nextLine();
                        System.out.print("Enter password: ");
                        String password = s.nextLine();

                        user.userLogin(userId, password);
                        if (!user.isLogged()) {
                            System.out.println("Login failed. Try again.");
                        }
                    }
                    break;
                case 2:
                    while (!user.isLogged()) {
                        user.userRegister();
                        if (!user.isLogged()) {
                            System.out.println("Registration failed. Try again.");
                        }
                    }
                    break;
            }
        }
        int[] result = Survey.conductSurvey(s);
        SurveyLogger.storeInSql(user.user,"test",result);
    }
}
