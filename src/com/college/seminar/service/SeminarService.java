
package com.college.seminar.service;

import com.college.seminar.dao.SeminarDAO;
import com.college.seminar.interfaces.SeminarServiceInterface;
import com.college.seminar.model.Seminar;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SeminarService implements SeminarServiceInterface {

    private final SeminarDAO seminarDAO = new SeminarDAO();

    private void validate(Seminar seminar) {
        if (seminar == null) {
            throw new IllegalArgumentException("Seminar cannot be null");
        }
        if (seminar.getSeminarId() == null || seminar.getSeminarId().isBlank()) {
            throw new IllegalArgumentException("Seminar ID is required");
        }
        if (seminar.getTitle() == null || seminar.getTitle().isBlank()) {
            throw new IllegalArgumentException("Seminar title is required");
        }
        if (seminar.getTopic() == null || seminar.getTopic().isBlank()) {
            throw new IllegalArgumentException("Seminar topic is required");
        }
        if (seminar.getCategory() == null || seminar.getCategory().isBlank()) {
            throw new IllegalArgumentException("Seminar category is required");
        }
        if (seminar.getSpeakerId() == null || seminar.getSpeakerId().isBlank()) {
            throw new IllegalArgumentException("Speaker ID is required");
        }
        if (seminar.getSeminarDate() == null
                || seminar.getSeminarDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Seminar date cannot be in the past");
        }
        if (seminar.getSeminarTime() == null) {
            throw new IllegalArgumentException("Seminar time is required");
        }
        if (seminar.getVenue() == null || seminar.getVenue().isBlank()) {
            throw new IllegalArgumentException("Seminar venue is required");
        }
        if (seminar.getCapacity() <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
    }

    @Override
    public boolean addSeminar(Seminar seminar) throws SQLException {
        validate(seminar);
        return seminarDAO.addSeminar(seminar);
    }

    @Override
    public List<Seminar> getAllSeminars() throws SQLException {
        return seminarDAO.getAllSeminars();
    }

    @Override
    public boolean updateSeminar(Seminar seminar) throws SQLException {
        validate(seminar);
        return seminarDAO.updateSeminar(seminar);
    }

    @Override
    public boolean deleteSeminar(String seminarId) throws SQLException {
        if (seminarId == null || seminarId.isBlank()) {
            throw new IllegalArgumentException("Seminar ID is required");
        }
        return seminarDAO.deleteSeminar(seminarId);
    }
}
