package com.srms.util;

public class GradeCalculator {

    public static double computePercentage(int obtained, int max) {
        if (max <= 0) {
            return 0.0;
        }
        return (double) obtained / max * 100;
    }

    public static String computeGrade(double percentage) {
        if (percentage >= 80) {
            return "A";
        } else if (percentage >= 65) {
            return "B";
        } else if (percentage >= 50) {
            return "C";
        } else if (percentage >= 35) {
            return "D";
        } else {
            return "F";
        }
    }

    public static String computePassFail(double percentage) {
        return percentage >= 35 ? "Pass" : "Fail";
    }
}
