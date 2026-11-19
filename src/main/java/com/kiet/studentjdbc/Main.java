
package com.kiet.studentjdbc;

import java.sql.SQLException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        int choice;

        do {
            System.out.println("\n===== STUDENT PROFILE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Email");
            System.out.println("5. Search Student by Course");
            System.out.println("6. Calculate Average and Performance");
            System.out.println("7. Display Stronger Subject");
            System.out.println("8. Display Merit List");
            System.out.println("9. Find Students with Same Skills");
            System.out.println("10. Find Improvement Students");
            System.out.println("11. Exit");

            System.out.print("\nEnter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        System.out.print("Enter Student ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Student Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Course Name: ");
                        String course = sc.nextLine();

                        System.out.print("Enter Programming Score: ");
                        double programming =
                                Double.parseDouble(sc.nextLine());

                        System.out.print("Enter Mathematics Score: ");
                        double mathematics =
                                Double.parseDouble(sc.nextLine());

                        System.out.print(
                                "Enter Technical Skills (comma separated): ");

                        String skillInput = sc.nextLine();

                        List<String> skills = new ArrayList<>();

                        for (String skill : skillInput.split(",")) {
                            if (!skill.trim().isEmpty()) {
                                skills.add(skill.trim());
                            }
                        }

                        Student s = new Student(
                                id, name, email, course,
                                programming, mathematics, skills
                        );

                        dao.insertStudent(s);
                        break;

                    case 2:
                        List<Student> all = dao.getAllStudents();

                        if (all.isEmpty()) {
                            System.out.println("No students found.");
                        } else {
                            for (Student student : all) {
                                System.out.println(student);
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Enter Student ID: ");
                        int searchId =
                                Integer.parseInt(sc.nextLine());

                        Student byId = dao.searchById(searchId);

                        if (byId != null) {
                            System.out.println(byId);
                        } else {
                            System.out.println("Student not found.");
                        }
                        break;

                    case 4:
                        System.out.print("Enter Email: ");
                        String searchEmail = sc.nextLine();

                        Student byEmail =
                                dao.searchByEmail(searchEmail);

                        if (byEmail != null) {
                            System.out.println(byEmail);
                        } else {
                            System.out.println("Student not found.");
                        }
                        break;

                    case 5:
                        System.out.print("Enter Course Name: ");
                        String searchCourse = sc.nextLine();

                        List<Student> courseStudents =
                                dao.searchByCourse(searchCourse);

                        if (courseStudents.isEmpty()) {
                            System.out.println("No students found.");
                        } else {
                            for (Student student : courseStudents) {
                                System.out.println(student);
                            }
                        }
                        break;

                    case 6:
                        List<Student> scoreStudents =
                                dao.getAllStudents();

                        for (Student student : scoreStudents) {
                            System.out.println(
                                    student.getStudentName() +
                                            " | Average: " +
                                            student.getAverageScore() +
                                            " | Category: " +
                                            student.getCategory()
                            );
                        }

                        if (scoreStudents.isEmpty()) {
                            System.out.println("No students found.");
                        }
                        break;

                    case 7:
                        List<Student> subjectStudents =
                                dao.getAllStudents();

                        for (Student student : subjectStudents) {
                            System.out.println(
                                    student.getStudentName() +
                                            " | Stronger Subject: " +
                                            student.getStrongerSubject()
                            );
                        }

                        if (subjectStudents.isEmpty()) {
                            System.out.println("No students found.");
                        }
                        break;

                    case 8:
                        List<Student> meritList =
                                dao.getMeritList();

                        if (meritList.isEmpty()) {
                            System.out.println("No students found.");
                        } else {
                            System.out.println("\n===== MERIT LIST =====");

                            int rank = 1;

                            for (Student student : meritList) {
                                System.out.println(
                                        "Rank " + rank++ +
                                                " | " + student.getStudentName() +
                                                " | Average: " +
                                                student.getAverageScore()
                                );
                            }
                        }
                        break;

                    case 9:
                        dao.findSameSkills();
                        break;

                    case 10:
                        List<Student> improvement =
                                dao.getImprovementStudents();

                        if (improvement.isEmpty()) {
                            System.out.println(
                                    "No students need improvement.");
                        } else {
                            for (Student student : improvement) {
                                System.out.println(student);
                            }
                        }
                        break;

                    case 11:
                        System.out.println("Exiting application...");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                choice = 0;

            } catch (SQLException e) {
                System.out.println("Database Error: " + e.getMessage());
                choice = 0;
            }

        } while (choice != 11);

        sc.close();
    }
}

