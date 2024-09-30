package survey_system.surveySystem;

import survey_system.utilities.Input;

import java.util.Scanner;

public class Survey {
    public static int[] conductSurvey(Scanner s) {
        String[] questions = {
                "1. What is your favorite programming language?",
                "2. What is your preferred IDE?",
                "3. What type of project do you like to work on?",
                "4. What is your experience level with Java?",
                "5. Which operating system do you prefer for development?"
        };

        String[][] options = {
                {"1. Java", "2. Python", "3. C++", "4. JavaScript"},
                {"1. IntelliJ IDEA", "2. Visual Studio Code", "3. Eclipse", "4. NetBeans"},
                {"1. Web Development", "2. Mobile App Development", "3. Data Science", "4. Embedded Systems"},
                {"1. Beginner", "2. Intermediate", "3. Advanced", "4. Expert"},
                {"1. Windows", "2. macOS", "3. Linux", "4. Other"}
        };

        int[] responses = new int[questions.length];

        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }
            responses[i] = Input.getChoice(s, 1, options[i].length);
        }

        return responses;
    }
}
