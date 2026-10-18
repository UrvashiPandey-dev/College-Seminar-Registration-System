
package com.college.seminar;

import com.college.seminar.dao.SeminarDAO;
import com.college.seminar.model.Seminar;

import java.time.LocalDate;
import java.time.LocalTime;

public class SeminarDAOTest {

    public static void main(String[] args) {
        SeminarDAO dao = new SeminarDAO();

        try {
            Seminar seminar = new Seminar(
                "TESTSEM001",
                "Java Testing Seminar",
                "Java Programming",
                "Technology",
                "SP101",
                LocalDate.of(2027, 1, 15),
                LocalTime.of(10, 0),
                "Lab 101",
                50
            );

            System.out.println("Add : " + dao.addSeminar(seminar));

            seminar.setTitle("Updated Java Testing Seminar");
            System.out.println("Update : " + dao.updateSeminar(seminar));

            System.out.println("\nAll Seminars:");

            for (Seminar s : dao.getAllSeminars()) {
                System.out.println(
                    s.getSeminarId() + " | " +
                    s.getTitle() + " | " +
                    s.getTopic() + " | " +
                    s.getCategory() + " | " +
                    s.getSpeakerId() + " | " +
                    s.getSeminarDate() + " | " +
                    s.getSeminarTime() + " | " +
                    s.getVenue() + " | " +
                    s.getCapacity()
                );
            }

            System.out.println(
                "\nDelete : " + dao.deleteSeminar("TESTSEM001")
            );

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
