
package com.college.seminar.service;

import com.college.seminar.dao.StudentDAO;
import com.college.seminar.interfaces.StudentServiceInterface;
import com.college.seminar.model.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService implements StudentServiceInterface {

    private final StudentDAO studentDAO = new StudentDAO();

    private void validateStudent(Student student, boolean requireId) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (requireId && (student.getStudentId() == null
                || student.getStudentId().isBlank())) {
            throw new IllegalArgumentException("Student ID is required");
        }

        if (student.getName() == null || student.getName().isBlank()) {
            throw new IllegalArgumentException("Student name is required");
        }

        if (student.getEmail() == null
                || !student.getEmail().matches(
                "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("Valid email is required");
        }

        if (student.getDepartment() == null
                || student.getDepartment().isBlank()) {
            throw new IllegalArgumentException("Department is required");
        }

        if (student.getAcademicYear() <= 0) {
            throw new IllegalArgumentException(
                    "Academic year must be positive");
        }
    }

    //Override
    public boolean addStudent(Student student) throws SQLException {
        validateStudent(student, true);
        return studentDAO.addStudent(student);
    }

    //Override
    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    //Override
    public boolean updateStudent(Student student) throws SQLException {
        validateStudent(student, true);
        return studentDAO.updateStudent(student);
    }

    //Override
    public boolean deleteStudent(String studentId) throws SQLException {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException(
                    "Student ID is required for deletion");
        }

        return studentDAO.deleteStudent(studentId);
    }
}
