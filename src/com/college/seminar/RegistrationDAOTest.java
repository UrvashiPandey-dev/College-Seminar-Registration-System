
package com.college.seminar;

import com.college.seminar.dao.RegistrationDAO;
import com.college.seminar.model.Registration;

import java.time.LocalDate;

public class RegistrationDAOTest {

    public static void main(String[] args) {
        RegistrationDAO dao = new RegistrationDAO();

        try {
            Registration registration = new Registration(
                "TESTREG001",
                "S102",
                "SEM102",
                LocalDate.now(),
                "CONFIRMED"
            );

            System.out.println(
                "Add : " + dao.addRegistration(registration)
            );

            registration.setStatus("CANCELLED");

            System.out.println(
                "Update : " + dao.updateRegistration(registration)
            );

            System.out.println("\nAll Registrations:");

            for (Registration r : dao.getAllRegistrations()) {
                System.out.println(
                    r.getRegistrationId() + " | " +
                    r.getStudentId() + " | " +
                    r.getSeminarId() + " | " +
                    r.getRegistrationDate() + " | " +
                    r.getStatus()
                );
            }

            System.out.println(
                "\nDelete : " + dao.deleteRegistration("TESTREG001")
            );

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
