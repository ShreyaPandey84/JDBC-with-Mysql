
package com.kiet.studentjdbc;

import java.sql.*;
import java.util.*;

public class StudentDAO {

    // Get technical skills of a student
    private List<String> getSkills(Connection con, int id)
            throws SQLException {

        List<String> skills = new ArrayList<>();

        String sql =
                "SELECT skill FROM StudentSkills WHERE student_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    skills.add(rs.getString("skill"));
                }
            }
        }

        return skills;
    }

    // Convert database record into Student object
    private Student mapStudent(Connection con, ResultSet rs)
            throws SQLException {

        int id = rs.getInt("student_id");

        return new Student(
                id,
                rs.getString("student_name"),
                rs.getString("email"),
                rs.getString("course_name"),
                rs.getDouble("programming_score"),
                rs.getDouble("mathematics_score"),
                getSkills(con, id)
        );
    }

    // 1. Insert Student
    public void insertStudent(Student s) throws SQLException {

        if (s.getProgrammingScore() < 0 ||
                s.getProgrammingScore() > 100 ||
                s.getMathematicsScore() < 0 ||
                s.getMathematicsScore() > 100) {

            System.out.println("Scores must be between 0 and 100.");
            return;
        }

        if (searchById(s.getStudentId()) != null) {
            System.out.println("Student ID already exists!");
            return;
        }

        String sql = "INSERT INTO Student VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, s.getStudentId());
                ps.setString(2, s.getStudentName());
                ps.setString(3, s.getEmail());
                ps.setString(4, s.getCourseName());
                ps.setDouble(5, s.getProgrammingScore());
                ps.setDouble(6, s.getMathematicsScore());

                ps.executeUpdate();

                String skillSql =
                        "INSERT INTO StudentSkills VALUES (?, ?)";

                try (PreparedStatement skillPs =
                             con.prepareStatement(skillSql)) {

                    for (String skill : s.getTechnicalSkills()) {
                        skillPs.setInt(1, s.getStudentId());
                        skillPs.setString(2, skill);
                        skillPs.addBatch();
                    }

                    skillPs.executeBatch();
                }

                con.commit();
                System.out.println("Student added successfully!");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    // 2. Display All Students
    public List<Student> getAllStudents() throws SQLException {

        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM Student";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapStudent(con, rs));
            }
        }

        return list;
    }

    // 3. Search Student by ID
    public Student searchById(int id) throws SQLException {

        String sql = "SELECT * FROM Student WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapStudent(con, rs);
                }
            }
        }

        return null;
    }

    // 4. Search Student by Email
    public Student searchByEmail(String email) throws SQLException {

        String sql = "SELECT * FROM Student WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapStudent(con, rs);
                }
            }
        }

        return null;
    }

    // 5. Search Students by Course
    public List<Student> searchByCourse(String course)
            throws SQLException {

        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM Student WHERE course_name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, course);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapStudent(con, rs));
                }
            }
        }

        return list;
    }

    // 6. Display Merit List (Highest Average First)
    public List<Student> getMeritList() throws SQLException {

        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM Student " +
                "ORDER BY (programming_score + mathematics_score) / 2 DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapStudent(con, rs));
            }
        }

        return list;
    }

    // 7. Find Students Having Same Technical Skills
    public void findSameSkills() throws SQLException {

        List<Student> students = getAllStudents();

        boolean found = false;

        for (int i = 0; i < students.size(); i++) {

            for (int j = i + 1; j < students.size(); j++) {

                Set<String> common = new HashSet<>(
                        students.get(i).getTechnicalSkills());

                common.retainAll(
                        students.get(j).getTechnicalSkills());

                if (!common.isEmpty()) {

                    System.out.println(
                            students.get(i).getStudentName() +
                                    " and " +
                                    students.get(j).getStudentName() +
                                    " have common skills: " + common);

                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No students have common skills.");
        }
    }

    // 8. Programming below 50 and Mathematics >= 75
    public List<Student> getImprovementStudents()
            throws SQLException {

        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM Student " +
                "WHERE programming_score < 50 " +
                "AND mathematics_score >= 75";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapStudent(con, rs));
            }
        }

        return list;
    }
}
