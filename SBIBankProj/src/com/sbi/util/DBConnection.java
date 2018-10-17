package com.sbi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection
{
	private static final DBConnection single;
	private static Connection con;
	
	static 
	{
		single= new DBConnection();
	}
	
	private DBConnection()
	{
		try 
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/akashdb?user=root&password=root");
			System.out.println("---------------------STATE BANK OF INDIA------------------");
			} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static DBConnection getSingleton() 
	{
		return single;
	}
	public  Connection getConnection() 
	{
		return con;
	}
	@Override
	protected void finalize() throws Throwable
	{
		con.close();
	}
}
