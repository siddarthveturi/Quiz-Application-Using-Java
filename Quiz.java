import java.util.*;
import java.io.*;

/**
 * @author Mohan Siddarth
 */
@SuppressWarnings("resource")
public class Quiz {

	/**
	 * Takes the directory name as input and returs all files present in that
	 * directory
	 * <p>
	 * 
	 * @param dir Name of the directory
	 * @return <code>ArrayList<String></code>
	 */
	private static ArrayList<String> availableFiles(File dir) {
		File[] files = dir.listFiles();
		ArrayList<String> filesPresent = new ArrayList<String>();

		for (File file : files) {
			String fileName = file.getName();
			filesPresent.add(fileName.substring(0, fileName.length() - 4));
		}
		return filesPresent;
	}

	/**
	 * Create a file with the given name
	 * <p>
	 * 
	 * @param cName Name of the course
	 */
	protected static void addCourse(String cName) {
		try {
			File courseDir = new File("Courses");
			ArrayList<String> filesPresent = availableFiles(courseDir);

			if (filesPresent.contains(cName)) {
				System.out.println("Course already exists");
				return;
			}
			File course = new File("Courses/" + cName + ".txt");
			course.createNewFile();
			displayCourse();
		} catch (Exception e) {
			System.out.println("[addCourse] " + e);
		}
	}

	/**
	 * Delete the file with the given name.
	 * <p>
	 * 
	 * @param cName Name of the course
	 */
	protected static void removeCourse(String cName) {
		try {
			File course = new File("Courses/" + cName + ".txt");
			if (course.exists()) {
				course.delete();
				System.out.println("Updated course list is ");
				displayCourse();
			} else {
				System.out.println("Their is no such course to remove...!");
			}
		} catch (Exception e) {
			System.out.println("[removeCourse] " + e);
		}
	}

	/**
	 * Display all the courses present in the courses directory.
	 */
	protected static void displayCourse() {
		System.out.println("List of courses available are:");
		File coursesDir = new File("Courses");
		ArrayList<String> files = availableFiles(coursesDir);

		for (String file : files) {
			System.out.println(file);
		}
	}

	/**
	 * Store all the attempts of the student in a file with the name of the student.
	 * <p>
	 * 
	 * @param studentName Name of the student
	 */
	protected static void myAttempts(String studentName) {
		File studentAttemptFile = new File("Attempts/" + studentName + ".txt");
		ArrayList<String> studentAttempts = new ArrayList<String>();

		try (Scanner sc = new Scanner(studentAttemptFile)) {
			if (!studentAttemptFile.exists())
				studentAttemptFile.createNewFile();

			String line;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				studentAttempts.add(line);
			}

			if (studentAttempts.size() == 0) {
				System.out.println("Oops...! You have not attempted any Quiz yet..Come on Start it TODAY.");
				return;
			} else {
				System.out.printf("%40s", "Score Board");
				System.out.println();
				System.out.print("Course" + "\t" + "Score");
				System.out.println();

				for (int i = 0; i < studentAttempts.size(); i++) {
					System.out.println(studentAttempts.get(i));
				}
			}
		} catch (Exception e) {
			System.out.println("[myAttempts] " + e);
		}
	}

	/**
	 * Read all the questions present in the course file and return the question in
	 * the array.
	 * <p>
	 * 
	 * @param course Name of the course
	 * @return <code>ArrayList<Questions></code>
	 */
	protected static ArrayList<Questions> readQuestions(String course) {
		File courseFile = new File("Courses/" + course + ".txt");
		ArrayList<Questions> questions = new ArrayList<Questions>();

		try (Scanner sc = new Scanner(new FileReader(courseFile))) {
			String line, ques = "", answer = "";
			int i = 0;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (i % 3 == 0) {
					ques = line;
				} else if (i % 3 == 1) {
					answer = line;
					questions.add(new Questions(ques, answer));
				}
				i++;
			}
		} catch (Exception e) {
			System.out.println("[readQuestion] " + e);
		}
		return questions;
	}

	/**
	 * Take the question and answer for the course and store them in the course
	 * file.
	 */
	protected static void addQuiz() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the name of course");
		String course = s.nextLine();
		File courseFile = new File("Courses/" + course + ".txt");

		try (FileWriter fw = new FileWriter(courseFile, true)) {
			if (courseFile.exists()) {
				System.out.println("How many questions you want to add to: " + course);
				int numberOfQuestions = s.nextInt();
				s.nextLine();

				for (int i = 1; i <= numberOfQuestions; i++) {
					System.out.println("Enter Question " + i);
					String question = s.nextLine();
					System.out.println("Enter answer for question " + i);
					String answer = s.nextLine();

					fw.write(question);
					fw.write(System.getProperty("line.separator"));
					fw.write(answer);
					fw.write(System.getProperty("line.separator"));
					fw.write(System.getProperty("line.separator"));
				}
			} else {
				System.out.println("Please add a Course before adding Quiz");
			}

		} catch (Exception e) {
			System.out.println("[addQuiz] ");
			e.printStackTrace();
		}
	}

	/**
	 * Store the attempts in the file with student name.
	 * <p>
	 * 
	 * @param result      Result of the form Course \t Score
	 * @param studentName Name of the student
	 */
	private static void storeAttempts(String result, String studentName) {
		File studentAttemptFile = new File("Attempts/" + studentName + ".txt");
		try (FileWriter fw = new FileWriter(studentAttemptFile, true)) {
			fw.write(result);
			fw.write(System.getProperty("line.separator"));
		} catch (Exception e) {
			System.out.println("[storeAttempts] " + e);
		}
	}

	/**
	 * Get the questions from readQuestions function, call takeTest function and
	 * finally call storeAttempts function.
	 * <p>
	 * 
	 * @param course      Name of the course
	 * @param studentName Name of the student
	 */
	protected static void attemptQuiz(String course, String studentName) {
		try {

			File courseFile = new File("Courses/" + course + ".txt");

			if (courseFile.exists()) {
				ArrayList<Questions> questions = new ArrayList<Questions>();
				questions = readQuestions(course);

				if (questions.size() == 0) {
					System.out.println("There is no quiz at this moment");
					return;
				}

				String result = course + '\t' + takeTest(questions);
				storeAttempts(result, studentName);
			} else {
				System.out.println("No such course with that name");
			}
		} catch (Exception e) {
			System.out.println("[attemptQuiz] " + e);
		}
	}

	/**
	 * Displaying the questions of the course to the Student and calculating the
	 * score.
	 * <p>
	 * 
	 * @param questions Array of Questions
	 * @return int Return the final score
	 */
	private static int takeTest(ArrayList<Questions> questions) {
		try {
			int score = 0;
			int numberOfQuestions = questions.size();
			Scanner sc = new Scanner(System.in);
			for (int i = 0; i < numberOfQuestions; i++) {
				System.out.println(questions.get(i).question);
				System.out.println("Please enter your answer:" + "\t\t"
						+ "Hint:-if a then enter a,if two or more options enter abc");
				String a = sc.nextLine();
				if (a.equals(questions.get(i).answer)) {
					score = score + 10;
				}
			}
			System.out.println("Your Score is:" + score + "/" + numberOfQuestions * 10);
			if (score < (numberOfQuestions / 2) * 10) {
				System.out.println("Don\'t get discouraged TRY AGAIN");
			} else {
				System.out.println("Congratulations...! You are PASSED...");
			}
			return score;

		} catch (Exception e) {
			System.out.println("[takeTest] " + e);
		}
		return 0;
	}
}
