package com.college.seminar;

import com.college.seminar.util.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class DBTest{
	
	public static void main(String[] args){
		
	try(Connection connection = DBConnection.getConnection()){
		  System.out.println("Database connected successfully!");
		  System.out.println("Database : " + connection.getCatalog());
		  
		 }catch(SQLException e){
			 
			System.out.println("Database connection failed!");
			System.out.println(e.getMessage());
			 
			 }	
		}
		
	}
