import java.util.*;
import java.io.*;

/**
 * @author Mohan Siddarth
 */
@SuppressWarnings("resource")
public class Student extends Quiz {

    /**
     * Takes the name and password of the Student and create a file with that name
     * and store name in first line and password in second line.
     * <p>
     * 
     * @param name     Name of the Student
     * @param password Password of the Student
     */
    private static void addStudent(String name, String password) {
        File studentFile = new File("Students/" + name + ".txt");

        try (FileWriter fw = new FileWriter(studentFile, true)) {
            studentFile.createNewFile();

            fw.write(name);
            fw.write(System.getProperty("line.separator"));
            fw.write(password);
        } catch (Exception e) {
            System.out.println("[addStudent] " + e);
        }
    }

    /**
     * Check whether the Student with given name is present or not by getting all
     * the files in Student directory and checking the given name with files
     * present.
     * <p>
     * 
     * @param name Name of the Student
     * @return boolean
     */
    private static boolean checkStudent(String name) {
        File[] files = new File("Students").listFiles();
        ArrayList<String> students = new ArrayList<String>();

        for (File file : files) {
            String fileName = file.getName();
            students.add(fileName.substring(0, fileName.length() - 4));
        }

        return students.contains(name);
    }

    /**
     * Find the file with given name and check the password against the password
     * present in that name file. If successful return true else false.
     * <p>
     * 
     * @param name     Name of the Student
     * @param password Password of the Student
     * @return boolean
     */
    private static boolean loginStudent(String name, String password) {
        File studentFile = new File("Students/" + name + ".txt");

        // At index 0, name of the student is stored
        // At index 1, password of the student is stored
        ArrayList<String> studentProfile = new ArrayList<String>();

        try (Scanner sc = new Scanner(studentFile)) {
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                studentProfile.add(line);
            }

            if (!password.equals(studentProfile.get(1))) {
                System.out.println("Wrong Password");
                return false;
            }

            System.out.println("You are logged in successfully");
        } catch (Exception e) {
            System.out.println("[loginStudent] " + e);
        }

        return true;
    }

    /**
     * Show the functionalities of the Student and call the functions according to
     * the choice.
     * <p>
     * 
     * @param studentName Name of the Student
     * @return boolean
     */
    private static boolean studentMenu(String studentName) {
        Scanner sc = new Scanner(System.in);
        String continueMessage = "";
        do {
            System.out.println("Functions Available for Students are:\n" + "1 : Display courses\n" + "2 : Take Quiz\n"
                    + "3 : My Attempts\n" + "4 : Exit");
            System.out.println("------\"Enter your Choice\"------");
            int studentChoice = sc.nextInt();
            sc.nextLine();
            if (studentChoice == 1) {
                displayCourse();
                System.out.println();
            } else if (studentChoice == 2) {
                displayCourse();
                System.out.println();

                System.out.println("Enter Course name to attempt");

                String course = sc.nextLine();
                attemptQuiz(course, studentName);
            } else if (studentChoice == 3) {
                myAttempts(studentName);
            } else if (studentChoice == 4) {
                return false;
            }
            System.out.println("Enter \"Yes\" to go to Student home page ");
            continueMessage = sc.nextLine();

        } while (continueMessage.charAt(0) == 'Y' || continueMessage.charAt(0) == 'y');
        return true;
    }

    /**
     * Check the name of student and call login or register function accordingly.
     */
    public static void studentFullMenu() {
        Scanner sc = new Scanner(System.in);
        String continueMessage = "";
        String studentName = "", password = "";
        Stu: do {
            System.out.println(" Welcome to QUIZZIZ Student Portal...!");
            System.out.println("Enter your name:");

            studentName = sc.nextLine();
            if (checkStudent(studentName)) {
                boolean login;
                do {
                    System.out.println("Enter Password");
                    password = sc.nextLine();
                    login = loginStudent(studentName, password);
                } while (!login);

                if (studentMenu(studentName) == false)
                    break Stu;
            } else {
                String register = "";
                System.out.println("You are not registered. To register press y");
                register = sc.nextLine();

                if (register.charAt(0) == 'y' || register.charAt(0) == 'Y') {
                    System.out.println("Enter your name");
                    studentName = sc.nextLine();
                    System.out.println("Enter your password");
                    password = sc.nextLine();
                    addStudent(studentName, password);
                    System.out.println("You are registered successfully");
                    studentMenu(studentName);
                } else {
                    System.out.println("Enter \"Yes\" to try again");
                }
            }

            continueMessage = sc.nextLine();

        } while (continueMessage.charAt(0) == 'y' || continueMessage.charAt(0) == 'Y');
    }
}