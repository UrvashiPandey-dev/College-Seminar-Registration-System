
package com.college.seminar;

import com.college.seminar.model.Student;
import com.college.seminar.model.Speaker;
import com.college.seminar.model.Seminar;
import com.college.seminar.service.StudentService;
import com.college.seminar.service.SpeakerService;
import com.college.seminar.service.SeminarService;

import java.time.LocalDate;
import java.time.LocalTime;

public class ServiceValidationTest {

    @FunctionalInterface
    interface TestAction {
        void run() throws Exception;
    }

    private static void expectInvalid(String name, TestAction action) {
        try {
            action.run();
            System.out.println("FAIL: " + name + " - no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + name);
        } catch (Exception e) {
            System.out.println("FAIL: " + name + " - " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentService students = new StudentService();
        SpeakerService speakers = new SpeakerService();
        SeminarService seminars = new SeminarService();

        expectInvalid("Reject null student",
                () -> students.addStudent(null));

        expectInvalid("Reject null speaker",
                () -> speakers.addSpeaker(null));

        expectInvalid("Reject null seminar",
                () -> seminars.addSeminar(null));

        expectInvalid("Reject blank student ID",
                () -> students.addStudent(
                        new Student("", "Test Student", "test@example.com",
                                "MCA", 1)));

        expectInvalid("Reject invalid speaker email",
                () -> speakers.addSpeaker(
                        new Speaker("TESTSP999", "Test Speaker",
                                "invalid-email", "Test Org", "Java")));

        expectInvalid("Reject zero seminar capacity",
                () -> seminars.addSeminar(
                        new Seminar("TESTSEM999", "Test Seminar",
                                "Testing", "Technology", "SP101",
                                LocalDate.now().plusDays(1),
                                LocalTime.of(10, 0), "Hall X", 0)));
    }
}
