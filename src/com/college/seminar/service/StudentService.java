
package com.college.seminar.service;

import com.college.seminar.dao.StudentDAO;
import com.college.seminar.interfaces.StudentServiceInterface;
import com.college.seminar.model.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService implements StudentServiceInterface {

    private final StudentDAO studentDAO = new StudentDAO();

    //Override
    public boolean addStudent(Student student) throws SQLException {
        return studentDAO.addStudent(student);
    }

    //Override
    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    //Override
    public boolean updateStudent(Student student) throws SQLException {
        return studentDAO.updateStudent(student);
    }

    //Override
    public boolean deleteStudent(String studentId) throws SQLException {
        return studentDAO.deleteStudent(studentId);
    }
}
