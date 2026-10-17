package com.college.seminar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	public void start(Stage stage){
		Label message = new Label ("College Semiar Registration System");
		
		StackPane root = new StackPane(message);
		Scene scene = new Scene(root , 600 , 400);
		
		stage.setTitle("College Seminar Registration System");
		stage.setScene(scene);
		stage.show();
		
		}
		
		public static void main(String args[]){
			launch(args);
			}
	}
