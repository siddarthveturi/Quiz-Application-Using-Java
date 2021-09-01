import java.util.*;

/**
 * @author Mohan Siddarth
 */
public class Menu extends Quiz {

    /**
     * Get the current hour using Calendar class and greet accordingly
     * <p>
     * 
     * @return String
     */
    public static String greeting() {
        Calendar now = Calendar.getInstance();
        int time = now.get(Calendar.HOUR_OF_DAY);
        if (time > 3 && time < 12) {
            return "Good Morning!";
        } else if (time >= 12 && time < 17) {
            return "Good Afternoon!";
        } else if (time >= 17 && time < 22) {
            return "Good Evening!";
        } else {
            return "Good Night!";
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String continueMessage;

        try {
            do {
                System.out.printf("%40s", "QUIZZIZ\n");
                System.out.println("Who you are: Teacher or Student..?");
                System.out.println("1 : Teacher \n2 : Student");
                System.out.println("Enter \"1\"  or \"2\"");

                int role = sc.nextInt();
                sc.nextLine();
                if (role == 1) {
                    Teacher.teacherFullMenu();
                } else if (role == 2) {
                    Student.studentFullMenu();
                }
                System.out.println("Enter \"Main\" to go to main page or \"Exit\" to exit application");
                continueMessage = sc.nextLine();
            } while (continueMessage.charAt(0) == 'm' || continueMessage.charAt(0) == 'M');

            System.out.println("\tQuiz application Created by \"Mohan Siddarth\"\n\t\t\tThank You.");
            sc.close();
        } catch (InputMismatchException e) {
            System.out.println("Enter a valid input..!");
        } catch (Exception e) {
            System.out.println("Error..!");
            System.out.println(e);
        }
    }
}