
package com.college.seminar.interfaces;

import com.college.seminar.model.Registration;
import java.sql.SQLException;
import java.util.List;

public interface RegistrationServiceInterface {
    boolean addRegistration(Registration registration) throws SQLException;
    List<Registration> getAllRegistrations() throws SQLException;
    boolean updateRegistration(Registration registration) throws SQLException;
    boolean deleteRegistration(String registrationId) throws SQLException;
}
