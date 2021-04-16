package com.adjudication.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {
	private Connection connection;
	private static Statement statement;
	private static ResultSet result;
	public Properties properties;
	
	/**
	 * Gets the deatails from DB.
	 *
	 * @param query the query
	 * @param connectionstring the connectionstring
	 * @return the deatails from DB
	 * @throws Exception the exception
	 */
	public ResultSet getDeatailsFromDB(String query,String connectionstring ) throws Exception {
		
		connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection=DriverManager.getConnection(connectionstring);
			if(connection!=null)
			{
				System.out.println("connecting to database");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
//		if (connection != null) {
//			try {
//				System.out.println("Closing Database Connection...");
//				connection.close();
//			} catch (SQLException ex) {
//				ex.printStackTrace();
//			}
//		}
		return result;
	}
}
