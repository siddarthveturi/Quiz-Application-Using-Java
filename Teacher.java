import java.util.*;
import java.io.*;

/**
 * @author Mohan Siddarth
 */
@SuppressWarnings("resource")
public class Teacher extends Quiz {

    /**
     * Takes the name and password of the Teacher and create a file with that name
     * and store name in first line and password in second line.
     * <p>
     * 
     * @param name     Name of the Teacher
     * @param password Password of the Teacher
     */
    private static void addTeacher(String name, String password) {
        File teacherFile = new File("Teachers/" + name + ".txt");
        try (FileWriter fw = new FileWriter(teacherFile, true)) {
            teacherFile.createNewFile();

            fw.write(name);
            fw.write(System.getProperty("line.separator"));
            fw.write(password);
        } catch (Exception e) {
            System.out.println("[addTeacher] " + e);
        }
    }

    /**
     * Check whether the Teacher with given name is present or not by getting all
     * the files in Teacher directory and checking the given name with files
     * present.
     * <p>
     * 
     * @param name Name of the Teacher
     * @return boolean
     */
    private static boolean checkTeacher(String name) {
        File[] files = new File("Teachers").listFiles();
        ArrayList<String> teachers = new ArrayList<String>();

        for (File file : files) {
            String fileName = file.getName();
            teachers.add(fileName.substring(0, fileName.length() - 4));
        }

        return teachers.contains(name);
    }

    /**
     * Find the file with given name and check the password against the password
     * present in that name file. If successful return true else false.
     * <p>
     * 
     * @param name     Name of the Teacher
     * @param password Password of the Teacher
     * @return boolean
     */
    private static boolean loginTeacher(String name, String password) {
        File teacherFile = new File("Teachers/" + name + ".txt");

        // At index 0, name of the teacher is stored
        // At index 1, password of the teacher is stored
        ArrayList<String> teacherProfile = new ArrayList<String>();

        try (Scanner sc = new Scanner(teacherFile)) {
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                teacherProfile.add(line);
            }

            if (!password.equals(teacherProfile.get(1))) {
                System.out.println("Wrong Password");
                return false;
            }

            System.out.println("You are logged in successfully");
        } catch (Exception e) {
            System.out.println("[loginTeacher] " + e);
        }

        return true;
    }

    /**
     * Show the functionalities of the Teacher and call the functions according to
     * the choice.
     * <p>
     * 
     * @param teacherName Name of the teacher
     * @return boolean
     */
    private static boolean teacherMenu(String teacherName) {
        Scanner sc = new Scanner(System.in);
        String continueMessage = "";
        do {

            System.out.println(Menu.greeting() + " " + teacherName);
            System.out.println("Functions Available for teacher are:\n" + "1 : Add a Course\n" + "2 : Remove a course\n"
                    + "3 : Add Quiz to Added course\n" + "4 : Display courses\n" + "5 : Exit");
            System.out.println("------\"Enter your Choice\"------");
            int teacherChoice = sc.nextInt();
            sc.nextLine();

            if (teacherChoice == 1) {
                System.out.println("Enter the name of the course you want to add:");
                String courseName = sc.nextLine();
                addCourse(courseName);
            } else if (teacherChoice == 2) {
                System.out.println("Enter the name of the course you want to \"Remove\":");
                String courseName = sc.nextLine();
                removeCourse(courseName);
            } else if (teacherChoice == 3) {
                addQuiz();
            } else if (teacherChoice == 4) {
                displayCourse();
                System.out.println();
            } else if (teacherChoice == 5) {
                return false;
            }
            System.out.println("Enter \"Yes\" to go to Teacher home page ");
            continueMessage = sc.nextLine();

        } while ((continueMessage.charAt(0) == 'Y' || continueMessage.charAt(0) == 'y'));
        return true;
    }

    /**
     * Check the name of teacher and call login or register function accordingly.
     */
    public static void teacherFullMenu() {
        Scanner sc = new Scanner(System.in);
        String continueMessage = "";
        String teacherName = "", password = "";
        do {
            System.out.println(" Welcome to QUIZZIZ teacher Portal...!");
            System.out.println("Enter your name:");

            teacherName = sc.nextLine();
            if (checkTeacher(teacherName)) {
                boolean login;
                do {
                    System.out.println("Enter Password");
                    password = sc.nextLine();
                    login = loginTeacher(teacherName, password);
                } while (!login);

                if (teacherMenu(teacherName) == false)
                    break;
            } else {
                String register = "";
                System.out.println("You are not registered. To register press y");
                register = sc.nextLine();

                if (register.charAt(0) == 'y' || register.charAt(0) == 'Y') {
                    System.out.println("Enter your name");
                    teacherName = sc.nextLine();
                    System.out.println("Enter your password");
                    password = sc.nextLine();
                    addTeacher(teacherName, password);
                    System.out.println("You are registered successfully");
                    teacherMenu(teacherName);
                } else {
                    System.out.println("Enter \"Yes\" to try again");
                }
            }

            continueMessage = sc.nextLine();

        } while (continueMessage.charAt(0) == 'y' || continueMessage.charAt(0) == 'Y');
    }

}
