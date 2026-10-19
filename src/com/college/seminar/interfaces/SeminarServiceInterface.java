
package com.college.seminar.interfaces;

import com.college.seminar.model.Seminar;
import java.sql.SQLException;
import java.util.List;

public interface SeminarServiceInterface {
    boolean addSeminar(Seminar seminar) throws SQLException;
    List<Seminar> getAllSeminars() throws SQLException;
    boolean updateSeminar(Seminar seminar) throws SQLException;
    boolean deleteSeminar(String seminarId) throws SQLException;
}
