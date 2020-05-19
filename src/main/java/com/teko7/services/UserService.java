package com.teko7.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.teko7.database.Database;
import com.teko7.entities.UserEntity;

public class UserService {

	Connection myConn = null;
	PreparedStatement myStmt = null;
    ResultSet myRs = null;
	
	private Database getDatabase() {return new Database();}
	private Connection getConnection() throws Exception {return getDatabase().getConnection();}
	
	public UserEntity getUserById(int theId) throws SQLException {
   	 try {
   		 myConn = getConnection();
   		 myStmt = myConn.prepareStatement("select * from users where id = ?");
   		 myStmt.setInt(1, theId);
   		 
   		 ResultSet rs = myStmt.executeQuery();

   		 if(rs.next())
   			 return new UserEntity(rs.getInt("id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("username"),rs.getString("age"),rs.getString("school"),rs.getString("department"));
   		 else
   			 return null;
   		 
   	 }catch(Exception e) {
   		 System.out.println(e.getStackTrace());
   		 System.out.println("execute Query HAtasi !");
   		 System.exit(1);
   	 }finally{
   		 myConn.close();
   	 }
   	 return null;
    }

	public void saveUser(UserEntity user) throws SQLException {
   	 try {
   		 myConn = getConnection();
   		 if(user.getId() > 0) {
   			 myStmt = myConn.prepareStatement("update users set firstname=?, lastname=? , username=? , age=? , school=? , department=? where id=?");
   			 myStmt.setInt(7, user.getId());
   		 }
   		 else{
   			 myStmt = myConn.prepareStatement("Insert into users (firstname, lastname, username, age,school,department) values (?,?,?,?,?,?)");
   		 
   		 }
   		
   		 myStmt.setString(1, user.getFirstname());
   		 myStmt.setString(2, user.getLastname());
   		 myStmt.setString(3, user.getUsername());
   		 myStmt.setString(4, user.getAge());
   		 myStmt.setString(5, user.getSchool());
   		 myStmt.setString(6, user.getDepartment());
   		 
   		 myStmt.executeUpdate();
   		 
   	 }catch(Exception e) {
   		 e.getStackTrace();
   	 }finally{
   		 myConn.close();
   	 }
    }

	public List<UserEntity> getAllUsers() { 
   	 List<UserEntity> users = new ArrayList<UserEntity>();	
   	 UserEntity entity = null;
   	 try {
			 myConn = getConnection();
			 String query = "select * from users";
			 myStmt = myConn.prepareStatement(query);
			 ResultSet rs = myStmt.executeQuery(query);
			 while(rs.next()) {
				 entity = new UserEntity(rs.getInt("id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("username"),rs.getString("age"),rs.getString("school"),rs.getString("department"));
				System.out.println(entity);
				 users.add(entity);
			 }
			 myConn.close();
			 
		 }catch(Exception e) {
			 e.getStackTrace();
		 }
		 return users;
	 }

	public void removeUser(int id) throws SQLException {
   	 try {
   		 myConn = getConnection();
   		 myStmt = myConn.prepareStatement("delete from users where id = ?");
   		 myStmt.setInt(1, id);
   		 myStmt.executeUpdate();
   		 
   	 }catch(Exception e) {
   		 System.out.println(e.getStackTrace());
   	 }finally{
   		 myConn.close();
   	 }
    }

}
