package com.college.seminar.dao;

import com.college.seminar.model.Student;
import com.college.seminar.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO{
	
	public boolean addStudent(Student student ) throws SQLException{
		
		String sql = "insert into Student (Student_id , name , email , department ,academic_year ) values (? , ? , ? , ? , ? )";
		
		try(Connection con = DBConnection.getConnection() ; PreparedStatement ps = con.prepareStatement(sql))
		{
		ps.setString(1, student.getStudentId());
		ps.setString(2, student.getName());
		ps.setString(3, student.getEmail());
		ps.setString(4, student.getDepartment());
		ps.setInt(5, student.getAcademicYear());
		
		return ps.executeUpdate() == 1;
			}
		}
		public List<Student> getAllStudent() throws SQLException{
	     List<Student> students = new ArrayList<>();
	     
	     String sql = "select * from Student";
	     
	     try(Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql) ;ResultSet rs = ps.executeQuery())
	     {
		       while(rs.next()){
				   Student student = new Student( rs.getString("student_id"),
				                                  rs.getString("name"),
				                                  rs.getString("email"),
				                                  rs.getString("department"),
				                                  rs.getInt("academic_year"));
				   students.add(student);                                 
				    }
			 }
			return students;
			}
			
			public boolean deleteStudent(String studentId) throws SQLException{
				
				String sql = "delete from Student where student_id = ?";
				
				try(Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
				{
					ps.setString(1, studentId);
					return ps.executeUpdate() == 1;
					}
				}
		}
