
package com.college.seminar.service;

import com.college.seminar.dao.RegistrationDAO;
import com.college.seminar.dao.SeminarDAO;
import com.college.seminar.dao.StudentDAO;
import com.college.seminar.interfaces.RegistrationServiceInterface;
import com.college.seminar.model.Registration;
import com.college.seminar.model.Seminar;
import com.college.seminar.model.Student;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RegistrationService implements RegistrationServiceInterface {

    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final SeminarDAO seminarDAO = new SeminarDAO();

    private void validate(Registration registration) throws SQLException {
        if (registration == null) {
            throw new IllegalArgumentException("Registration cannot be null");
        }

        if (registration.getRegistrationId() == null
                || registration.getRegistrationId().isBlank()) {
            throw new IllegalArgumentException("Registration ID is required");
        }

        if (registration.getStudentId() == null
                || registration.getStudentId().isBlank()) {
            throw new IllegalArgumentException("Student ID is required");
        }

        if (registration.getSeminarId() == null
                || registration.getSeminarId().isBlank()) {
            throw new IllegalArgumentException("Seminar ID is required");
        }

        if (registration.getRegistrationDate() == null) {
            throw new IllegalArgumentException("Registration date is required");
        }

        if (registration.getRegistrationDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Registration date cannot be in the future");
        }

        String status = registration.getStatus();
        if (status == null
                || !(status.equalsIgnoreCase("CONFIRMED")
                || status.equalsIgnoreCase("CANCELLED"))) {
            throw new IllegalArgumentException(
                    "Status must be CONFIRMED or CANCELLED");
        }

        boolean studentExists = studentDAO.getAllStudents().stream()
                .anyMatch(s -> s.getStudentId()
                        .equals(registration.getStudentId()));

        if (!studentExists) {
            throw new IllegalArgumentException("Student does not exist");
        }

        Seminar seminar = seminarDAO.getAllSeminars().stream()
                .filter(s -> s.getSeminarId()
                        .equals(registration.getSeminarId()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Seminar does not exist"));

        if (status.equalsIgnoreCase("CONFIRMED")) {
            long confirmedCount = registrationDAO.getAllRegistrations().stream()
                    .filter(r -> r.getSeminarId().equals(seminar.getSeminarId()))
                    .filter(r -> "CONFIRMED".equalsIgnoreCase(r.getStatus()))
                    .filter(r -> !r.getRegistrationId().equals(
                            registration.getRegistrationId()))
                    .count();

            if (confirmedCount >= seminar.getCapacity()) {
                throw new IllegalArgumentException(
                        "Seminar has reached its capacity");
            }
        }

        boolean duplicate = registrationDAO.getAllRegistrations().stream()
                .anyMatch(r ->
                        r.getStudentId().equals(registration.getStudentId())
                        && r.getSeminarId().equals(registration.getSeminarId())
                        && "CONFIRMED".equalsIgnoreCase(r.getStatus())
                        && !r.getRegistrationId().equals(
                                registration.getRegistrationId()));

        if (status.equalsIgnoreCase("CONFIRMED") && duplicate) {
            throw new IllegalArgumentException(
                    "Student is already registered for this seminar");
        }
    }

    @Override
    public boolean addRegistration(Registration registration)
            throws SQLException {
        validate(registration);
        return registrationDAO.addRegistration(registration);
    }

    @Override
    public List<Registration> getAllRegistrations() throws SQLException {
        return registrationDAO.getAllRegistrations();
    }

    @Override
    public boolean updateRegistration(Registration registration)
            throws SQLException {
        validate(registration);
        return registrationDAO.updateRegistration(registration);
    }

    @Override
    public boolean deleteRegistration(String registrationId)
            throws SQLException {
        if (registrationId == null || registrationId.isBlank()) {
            throw new IllegalArgumentException("Registration ID is required");
        }
        return registrationDAO.deleteRegistration(registrationId);
    }
}
