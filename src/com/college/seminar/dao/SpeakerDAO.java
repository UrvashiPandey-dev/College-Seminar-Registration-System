package com.college.seminar.dao;

import com.college.seminar.model.Speaker;
import com.college.seminar.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpeakerDAO {

    public boolean addSpeaker(Speaker speaker) throws SQLException {
        String sql = "insert into Speaker (speaker_id, name, email, organization, expertise) values (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, speaker.getSpeakerId());
            ps.setString(2, speaker.getName());
            ps.setString(3, speaker.getEmail());
            ps.setString(4, speaker.getOrganization());
            ps.setString(5, speaker.getExpertise());

            return ps.executeUpdate() == 1;
        }
    }

    public List<Speaker> getAllSpeakers() throws SQLException {
        List<Speaker> speakers = new ArrayList<>();
        String sql = "select * from Speaker";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                speakers.add(new Speaker(
                        rs.getString("speaker_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("organization"),
                        rs.getString("expertise")
                ));
            }
        }

        return speakers;
    }

    public boolean updateSpeaker(Speaker speaker) throws SQLException {
        String sql = "update Speaker set name = ?, email = ?, organization = ?, expertise = ? where speaker_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, speaker.getName());
            ps.setString(2, speaker.getEmail());
            ps.setString(3, speaker.getOrganization());
            ps.setString(4, speaker.getExpertise());
            ps.setString(5, speaker.getSpeakerId());

            return ps.executeUpdate() == 1;
        }
    }

    public boolean deleteSpeaker(String speakerId) throws SQLException {
        String sql = "delete from Speaker where speaker_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, speakerId);
            return ps.executeUpdate() == 1;
        }
    }
}
