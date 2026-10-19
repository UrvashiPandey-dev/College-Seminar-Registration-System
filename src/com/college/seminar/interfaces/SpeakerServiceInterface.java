
package com.college.seminar.interfaces;

import com.college.seminar.model.Speaker;
import java.sql.SQLException;
import java.util.List;

public interface SpeakerServiceInterface {
    boolean addSpeaker(Speaker speaker) throws SQLException;
    List<Speaker> getAllSpeakers() throws SQLException;
    boolean updateSpeaker(Speaker speaker) throws SQLException;
    boolean deleteSpeaker(String speakerId) throws SQLException;
}
