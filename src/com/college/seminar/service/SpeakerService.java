
package com.college.seminar.service;

import com.college.seminar.dao.SpeakerDAO;
import com.college.seminar.interfaces.SpeakerServiceInterface;
import com.college.seminar.model.Speaker;

import java.sql.SQLException;
import java.util.List;

public class SpeakerService implements SpeakerServiceInterface {

    private final SpeakerDAO speakerDAO = new SpeakerDAO();

    private void validate(Speaker speaker, boolean requireId) {
        if (speaker == null) {
            throw new IllegalArgumentException("Speaker cannot be null");
        }
        if (requireId && (speaker.getSpeakerId() == null
                || speaker.getSpeakerId().isBlank())) {
            throw new IllegalArgumentException("Speaker ID is required");
        }
        if (speaker.getName() == null || speaker.getName().isBlank()) {
            throw new IllegalArgumentException("Speaker name is required");
        }
        if (speaker.getEmail() == null
                || !speaker.getEmail().matches(
                "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("Valid email is required");
        }
    }

    @Override
    public boolean addSpeaker(Speaker speaker) throws SQLException {
        validate(speaker, true);
        return speakerDAO.addSpeaker(speaker);
    }

    @Override
    public List<Speaker> getAllSpeakers() throws SQLException {
        return speakerDAO.getAllSpeakers();
    }

    @Override
    public boolean updateSpeaker(Speaker speaker) throws SQLException {
        validate(speaker, true);
        return speakerDAO.updateSpeaker(speaker);
    }

    @Override
    public boolean deleteSpeaker(String speakerId) throws SQLException {
        if (speakerId == null || speakerId.isBlank()) {
            throw new IllegalArgumentException("Speaker ID is required");
        }
        return speakerDAO.deleteSpeaker(speakerId);
    }
}
