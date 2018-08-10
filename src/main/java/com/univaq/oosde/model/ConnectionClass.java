package com.univaq.oosde.model;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
	public Connection connection;
	    public  Connection getConnection(){


	        String dbName="oosde";
	        String userName="oosde";
	        String password="oosde";

	        try {
	            Class.forName("com.mysql.jdbc.Driver").newInstance();

	        connection= DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password);


	        } catch (Exception e) {
	            e.printStackTrace();
	        }


	        return connection;
	    }
	   

	}