
package com.college.seminar.dao;

import com.college.seminar.model.Seminar;
import com.college.seminar.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SeminarDAO {

    public boolean addSeminar(Seminar seminar) throws SQLException {
        String sql = "INSERT INTO Seminar " +
                "(seminar_id, title, topic, category, speaker_id, " +
                "seminar_date, seminar_time, venue, capacity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, seminar.getSeminarId());
            ps.setString(2, seminar.getTitle());
            ps.setString(3, seminar.getTopic());
            ps.setString(4, seminar.getCategory());
            ps.setString(5, seminar.getSpeakerId());
            ps.setDate(6, Date.valueOf(seminar.getSeminarDate()));
            ps.setTime(7, Time.valueOf(seminar.getSeminarTime()));
            ps.setString(8, seminar.getVenue());
            ps.setInt(9, seminar.getCapacity());

            return ps.executeUpdate() == 1;
        }
    }

    public List<Seminar> getAllSeminars() throws SQLException {
        List<Seminar> seminars = new ArrayList<>();
        String sql = "SELECT * FROM Seminar";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                seminars.add(new Seminar(
                        rs.getString("seminar_id"),
                        rs.getString("title"),
                        rs.getString("topic"),
                        rs.getString("category"),
                        rs.getString("speaker_id"),
                        rs.getDate("seminar_date").toLocalDate(),
                        rs.getTime("seminar_time").toLocalTime(),
                        rs.getString("venue"),
                        rs.getInt("capacity")
                ));
            }
        }

        return seminars;
    }

    public boolean updateSeminar(Seminar seminar) throws SQLException {
        String sql = "UPDATE Seminar SET title = ?, topic = ?, " +
                "category = ?, speaker_id = ?, seminar_date = ?, " +
                "seminar_time = ?, venue = ?, capacity = ? " +
                "WHERE seminar_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, seminar.getTitle());
            ps.setString(2, seminar.getTopic());
            ps.setString(3, seminar.getCategory());
            ps.setString(4, seminar.getSpeakerId());
            ps.setDate(5, Date.valueOf(seminar.getSeminarDate()));
            ps.setTime(6, Time.valueOf(seminar.getSeminarTime()));
            ps.setString(7, seminar.getVenue());
            ps.setInt(8, seminar.getCapacity());
            ps.setString(9, seminar.getSeminarId());

            return ps.executeUpdate() == 1;
        }
    }

    public boolean deleteSeminar(String seminarId) throws SQLException {
        String sql = "DELETE FROM Seminar WHERE seminar_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, seminarId);
            return ps.executeUpdate() == 1;
        }
    }
}
