package com.college.seminar.model;

public class Speaker{
	private String speakerId;
	private String name;
	private String email;
	private String organization;
	private String expertise;
	
	public Speaker(String speakerId , String name , String email , String organization , String expertise){
		
		this.speakerId = speakerId;
		this.name = name;
		this.email = email;
		this.organization = organization;
		this.expertise = expertise;
	
    	}
	
	public String getSpeakerId(){
		return speakerId;
		}
		
	public String getName(){
		return name;
		}

	public String getEmail(){
		return email;
		}

	public String getOrganization(){
		return organization;
		}
		
	public String getExpertise(){
		return expertise;
		}
	
	public void setName(String name) {
         this.name = name;
         }
	
	public void setEmail(String email){
		this.email = email;
		}
		
	public void setOrganization(String organization){
		this.organization = organization;
		}	
		
	public void setExpertise(String expertise){
		this.expertise = expertise;
		}	
}

