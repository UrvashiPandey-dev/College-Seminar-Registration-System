
package com.college.seminar.interfaces;

import com.college.seminar.model.Student;
import java.sql.SQLException;
import java.util.List;

public interface StudentServiceInterface {

    boolean addStudent(Student student) throws SQLException;

    List<Student> getAllStudents() throws SQLException;

    boolean updateStudent(Student student) throws SQLException;

    boolean deleteStudent(String studentId) throws SQLException;
}
