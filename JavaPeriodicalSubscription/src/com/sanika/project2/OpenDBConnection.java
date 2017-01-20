package com.sanika.project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OpenDBConnection {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_LINK = "jdbc:mysql://localhost/subscriptions";
	Connection c = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public OpenDBConnection(){
		try {
			Class.forName(JDBC_DRIVER);
			c = DriverManager.getConnection(DB_LINK, "root", "sanika");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	protected void executeFinally() {
		
		 try{
	         if(ps!=null)
	            c.close();
	      }catch(SQLException se){
	      }
	      try{
	         if(c!=null)
	            c.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	}
	
}
