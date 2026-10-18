package com.college.seminar.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{
	
	private static final String URL = "jdbc:mariadb://localhost:3306/CollegeSeminarDB";
	
	private static final String USER = "seminar_app";
	
	 public static Connection getConnection() throws SQLException{
		 
		 String password = System.getenv("SEMINAR_DB_PASSWORD");
		 
		 if(password == null || password.isBlank()) { throw new SQLException("Seminar_DB_Password environment variable is not set.");
		 
			 }
		 return DriverManager.getConnection(URL , USER , password);
		 
		 }
	
	}
