package com.srms.util;

import com.srms.model.Result;
import java.util.List;

public class ExportUtil {

    public static String generateCSV(List<Result> results) {
        StringBuilder csv = new StringBuilder();
        csv.append("Student Name,Email,Department,Semester,Subject,Max Marks,Marks Obtained,Percentage,Grade,Pass/Fail\n");

        for (Result result : results) {
            csv.append("\"").append(escape(result.getStudent().getName())).append("\",");
            csv.append("\"").append(escape(result.getStudent().getEmail())).append("\",");
            csv.append("\"").append(escape(result.getStudent().getDepartment())).append("\",");
            csv.append(result.getStudent().getSemester()).append(",");
            csv.append("\"").append(escape(result.getSubject().getSubjectName())).append("\",");
            csv.append(result.getSubject().getMaxMarks()).append(",");
            csv.append(result.getMarksObtained()).append(",");
            csv.append(String.format("%.2f", result.getPercentage())).append(",");
            csv.append(result.getGrade()).append(",");
            csv.append(result.getPassFail()).append("\n");
        }

        return csv.toString();
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\"", "\"\"");
    }
}
