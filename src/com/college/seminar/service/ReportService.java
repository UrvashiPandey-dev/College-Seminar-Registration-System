
package com.college.seminar.service;

import com.college.seminar.dao.RegistrationDAO;
import com.college.seminar.dao.SeminarDAO;
import com.college.seminar.dao.StudentDAO;
import com.college.seminar.model.Registration;
import com.college.seminar.model.Seminar;
import com.college.seminar.model.Student;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final SeminarDAO seminarDAO = new SeminarDAO();

    // Confirmed registrations grouped by student department
    public Map<String, Long> getDepartmentStatistics()
            throws SQLException {

        List<Registration> registrations =
                registrationDAO.getAllRegistrations();
        List<Student> students = studentDAO.getAllStudents();

        Map<String, String> departmentByStudent = students.stream()
                .collect(Collectors.toMap(
                        Student::getStudentId,
                        Student::getDepartment,
                        (first, second) -> first
                ));

        return registrations.stream()
                .filter(r -> "CONFIRMED".equalsIgnoreCase(r.getStatus()))
                .filter(r -> departmentByStudent.containsKey(r.getStudentId()))
                .collect(Collectors.groupingBy(
                        r -> departmentByStudent.get(r.getStudentId()),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
    }

    // Confirmed registrations grouped by seminar category
    public Map<String, Long> getCategoryStatistics()
            throws SQLException {

        List<Registration> registrations =
                registrationDAO.getAllRegistrations();
        List<Seminar> seminars = seminarDAO.getAllSeminars();

        Map<String, String> categoryBySeminar = seminars.stream()
                .collect(Collectors.toMap(
                        Seminar::getSeminarId,
                        Seminar::getCategory,
                        (first, second) -> first
                ));

        return registrations.stream()
                .filter(r -> "CONFIRMED".equalsIgnoreCase(r.getStatus()))
                .filter(r -> categoryBySeminar.containsKey(r.getSeminarId()))
                .collect(Collectors.groupingBy(
                        r -> categoryBySeminar.get(r.getSeminarId()),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
    }

    // Combined department + seminar category statistics
    public Map<String, Long> getCombinedStatistics()
            throws SQLException {

        List<Registration> registrations =
                registrationDAO.getAllRegistrations();
        List<Student> students = studentDAO.getAllStudents();
        List<Seminar> seminars = seminarDAO.getAllSeminars();

        Map<String, String> departmentByStudent = students.stream()
                .collect(Collectors.toMap(
                        Student::getStudentId,
                        Student::getDepartment,
                        (first, second) -> first
                ));

        Map<String, String> categoryBySeminar = seminars.stream()
                .collect(Collectors.toMap(
                        Seminar::getSeminarId,
                        Seminar::getCategory,
                        (first, second) -> first
                ));

        return registrations.stream()
                .filter(r -> "CONFIRMED".equalsIgnoreCase(r.getStatus()))
                .filter(r -> departmentByStudent.containsKey(r.getStudentId()))
                .filter(r -> categoryBySeminar.containsKey(r.getSeminarId()))
                .collect(Collectors.groupingBy(
                        r -> departmentByStudent.get(r.getStudentId())
                                + " | "
                                + categoryBySeminar.get(r.getSeminarId()),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
    }
}
