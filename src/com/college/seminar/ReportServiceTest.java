
package com.college.seminar;

import com.college.seminar.service.ReportService;

public class ReportServiceTest {

    public static void main(String[] args) throws Exception {
        ReportService reports = new ReportService();

        System.out.println("=== Department Statistics ===");
        reports.getDepartmentStatistics()
                .forEach((key, value) ->
                        System.out.println(key + " : " + value));

        System.out.println("\n=== Category Statistics ===");
        reports.getCategoryStatistics()
                .forEach((key, value) ->
                        System.out.println(key + " : " + value));

        System.out.println("\n=== Combined Statistics ===");
        reports.getCombinedStatistics()
                .forEach((key, value) ->
                        System.out.println(key + " : " + value));
    }
}
