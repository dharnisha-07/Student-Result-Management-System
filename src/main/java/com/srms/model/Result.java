package com.srms.model;

public class Result {
    private Student student;
    private Subject subject;
    private int marksObtained;
    private double percentage;
    private String grade;
    private String passFail;

    public Result(Student student, Subject subject, int marksObtained, double percentage, String grade, String passFail) {
        this.student = student;
        this.subject = subject;
        this.marksObtained = marksObtained;
        this.percentage = percentage;
        this.grade = grade;
        this.passFail = passFail;
    }

    public Student getStudent() {
        return student;
    }

    public Subject getSubject() {
        return subject;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getGrade() {
        return grade;
    }

    public String getPassFail() {
        return passFail;
    }
}
