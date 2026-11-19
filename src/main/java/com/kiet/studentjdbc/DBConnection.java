
package com.kiet.studentjdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {

    private int studentId;
    private String studentName;
    private String email;
    private String courseName;
    private double programmingScore;
    private double mathematicsScore;
    private List<String> technicalSkills = new ArrayList<>();

    public Student() {
    }

    public Student(int studentId, String studentName, String email,
                   String courseName, double programmingScore,
                   double mathematicsScore, List<String> technicalSkills) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.courseName = courseName;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.technicalSkills = new ArrayList<>(technicalSkills);
    }

    // Getters and Setters

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = new ArrayList<>(technicalSkills);
    }

    // Calculate Average

    public double getAverageScore() {
        return (programmingScore + mathematicsScore) / 2.0;
    }

    // Performance Category

    public String getCategory() {
        double avg = getAverageScore();

        if (avg >= 85)
            return "Excellent";
        else if (avg >= 70)
            return "Good";
        else if (avg >= 50)
            return "Satisfactory";
        else
            return "Needs Improvement";
    }

    // Identify Stronger Subject

    public String getStrongerSubject() {
        if (programmingScore > mathematicsScore)
            return "Programming";
        else if (mathematicsScore > programmingScore)
            return "Mathematics";
        else
            return "Both are equal";
    }

    @Override
    public String toString() {
        return "\nStudent ID: " + studentId +
                "\nName: " + studentName +
                "\nEmail: " + email +
                "\nCourse: " + courseName +
                "\nProgramming Score: " + programmingScore +
                "\nMathematics Score: " + mathematicsScore +
                "\nAverage: " + getAverageScore() +
                "\nCategory: " + getCategory() +
                "\nStronger Subject: " + getStrongerSubject() +
                "\nTechnical Skills: " + technicalSkills;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Student student = (Student) obj;
        return studentId == student.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}

