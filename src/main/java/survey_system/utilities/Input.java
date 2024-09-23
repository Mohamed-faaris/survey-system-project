package survey_system.utilities;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    public static int getChoice(Scanner s, int min, int max,String query) {
        int choice = -1;

        while (true) {
            System.out.print(query);

            try {
                choice = s.nextInt();
                s.nextLine();  // Consume the newline

                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Please enter a valid choice between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a number.");
                s.nextLine();  // Clear invalid Input
            }
        }
    }
    public static int getChoice(Scanner s, int min, int max){
        return getChoice(s, min, max,"");
    }

}
