
package com.college.seminar;

import com.college.seminar.dao.StudentDAO;
import com.college.seminar.model.Student;

public class StudentDAOTest {

    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();

        try {
            Student student = new Student(
                    "TEST001",
                    "Test Student",
                    "test.student@example.com",
                    "MCA",
                    1
            );

            System.out.println("ADD: " + dao.addStudent(student));

            student.setName("Updated Test Student");
            System.out.println("UPDATE: " + dao.updateStudent(student));

            System.out.println("\nAll Students:");

            for (Student s : dao.getAllStudents()) {
                System.out.println(
                        s.getStudentId() + " | " +
                        s.getName() + " | " +
                        s.getDepartment()
                );
            }

            System.out.println("\nDELETE: " + dao.deleteStudent("TEST001")
            );

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
