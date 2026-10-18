
package com.college.seminar.dao;

import com.college.seminar.model.Registration;
import com.college.seminar.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    public boolean addRegistration(Registration registration)
            throws SQLException {

        String sql = "INSERT INTO Registration " +
                "(registration_id, student_id, seminar_id, " +
                "registration_date, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, registration.getRegistrationId());
            ps.setString(2, registration.getStudentId());
            ps.setString(3, registration.getSeminarId());
            ps.setDate(4, Date.valueOf(registration.getRegistrationDate()));
            ps.setString(5, registration.getStatus());

            return ps.executeUpdate() == 1;
        }
    }

    public List<Registration> getAllRegistrations()
            throws SQLException {

        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM Registration";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                registrations.add(new Registration(
                        rs.getString("registration_id"),
                        rs.getString("student_id"),
                        rs.getString("seminar_id"),
                        rs.getDate("registration_date").toLocalDate(),
                        rs.getString("status")
                ));
            }
        }

        return registrations;
    }

    public boolean updateRegistration(Registration registration)
            throws SQLException {

        String sql = "UPDATE Registration SET student_id = ?, " +
                "seminar_id = ?, registration_date = ?, status = ? " +
                "WHERE registration_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, registration.getStudentId());
            ps.setString(2, registration.getSeminarId());
            ps.setDate(3, Date.valueOf(registration.getRegistrationDate()));
            ps.setString(4, registration.getStatus());
            ps.setString(5, registration.getRegistrationId());

            return ps.executeUpdate() == 1;
        }
    }

    public boolean deleteRegistration(String registrationId)
            throws SQLException {

        String sql = "DELETE FROM Registration WHERE registration_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, registrationId);
            return ps.executeUpdate() == 1;
        }
    }
}
