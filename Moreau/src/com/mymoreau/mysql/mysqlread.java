package com.mymoreau.mysql;

import java.sql.*;

public class mysqlread {
	Connection connect = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public void init(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			connect=DriverManager.getConnection("jdbc:mysql://localhost/test1?user=root&password=1994121715&useUnicode=true&characterEncoding=UTF-8");
			stmt = connect.createStatement();
			//rs=stmt.executeQuery("select * from book");
			//rs.next();
			//System.out.println(rs.getString("name"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void queryAll(String sql) {
		try{
			rs = stmt.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int update(String sql) {
		try{
			return stmt.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	public boolean next(){
		try{
			return rs.next();
		}catch(Exception e){
			
		}
		return false;
	}
	public String getString(String str) {
		try{
			return rs.getString(str);
		}catch(Exception e){
			return "error";
		}
	}
	public int getInt(String str) {
		try{
			return rs.getInt(str);
		}catch(Exception e){
			return 0;
		}
	}
	
	public void close(){
		try{
			rs.close();
			stmt.close();
			connect.close();
		}catch(Exception e){
			
		}
		
	}
}
