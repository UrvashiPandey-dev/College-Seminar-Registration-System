package com.college.seminar.model;

public class Student{
	
     private String studentID;
     private String name;
     private String email;
     private String department;
     private int academicYear;
     
     public Student(String studentID , String name , String email , String department , int academicYear){
		 
			this.studentID = studentID;
			this.name = name ;
			this.email = email;
			this.department = department;
			this.academicYear = academicYear;
			
		 }
		 
		 public String getStudentId(){
			 return studentID;			 
			 }	
			 
		 public String getName(){
			 return name;
			 }
			
		 public String getEmail(){
			 return email;
			 }
			 	
		 public String getDepartment(){
			 return department;
			 }
			 
		 public int getAcademicYear(){
			 return academicYear;
			 }
			 
		 public void setName(String name){
			 this.name = name;
			 }
			 
		 public void setEmail(String name){
			 this.email = email;
			 }
			 
		 public void setDepartment(String department){
			 this.department = department;
			 }
			 
	     public void setAcademicYear(int academicYear){
			 this.academicYear = academicYear;
			 
			 }
	}
	
