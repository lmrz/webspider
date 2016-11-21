package com.cao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// 连结数据库公共类，用于连接数据库，操作SQL语句，关闭连接等
public class connectDB {

	private static String uri;
	private static String user;
    private	static String password;
    private static Connection conn = null;
    private static Statement statement;
    private static PreparedStatement ps;
	
	public static Connection getConnectDB()
	{
		uri = "jdbc:mysql://localhost:3306/cy";
		user = "root";
		password = "941001";
		String uriAll = "";
		uriAll = uri + "?user=" + user + "&password=" + password +"&useUnicode=true&characterEncoding=UTF-8";
	//	queryResult = new StringBuffer();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			//Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			conn = DriverManager.getConnection(uriAll);
		//	System.out.println(uriAll);
		}catch(Exception e)
		{
			System.out.println("数据库连结失败！");
		}
		return conn;
	}
	
	public static void close()
	{
		try{
			conn.close();
		}catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}
	}
	
	
	  // SQLException this method
	
	  public void closeSelect() throws SQLException {
		
		  if (statement != null)
			  statement.close();
	  }
	  
	  
	  public static ResultSet openSelect(String sql) throws SQLException {
		
	    statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    ResultSet rs = statement.executeQuery(sql);
	   
	    return rs;
	  }
	  
	  
	  public static int runSql(String sql) throws SQLException {
		
	    statement = conn.createStatement();
	    int result=statement.executeUpdate(sql);
	    statement.close();
	    return result;
	  }
	  public static int runStoredPreceedure(String sql, String s) throws SQLException {
		  ps = conn.prepareStatement(sql);
		  ps.setString(1, s);
		  int result = ps.executeUpdate(sql);
		  return result;
	  }
	  
	  public static ResultSet openStoredPreceedure(String sql, String s)
	  {
		  ResultSet rs = null; 
		  try {
			ps = conn.prepareStatement(sql);
		
			ps.setString(1,s);
		
			rs = ps.executeQuery();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return rs;
		  
	  }
}

