/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Arces
 */
public class CircaDatabase { //singleton

    private Connection con;
    private String sql;
    private Statement stmt;
    private ResultSet rs;
    private static CircaDatabase databaseInstance = new CircaDatabase();

    private CircaDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String host = "jdbc:mysql://127.0.0.1:3306/Circa?user=root";
            String uUser = "root";
            String uPass = "admin";

            con = DriverManager.getConnection(host, uUser, uPass);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CircaDatabase getInstance() {
        return databaseInstance;
    }

    public String getPassword(String userInput) {
        String password = "";
        boolean isFound = false;

        try {
         //get via email
            sql = "SELECT password FROM user"
                    + " WHERE emailAddress = '" + userInput + "'"; 
            
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                password = rs.getString("password");
                isFound = true;
            }
            
         //get via username
            if(!isFound)
            {
                sql = "SELECT password FROM user"
                        + " WHERE username = '" + userInput + "'";
                
                rs = stmt.executeQuery(sql);
                if(rs.next()){
                    password = rs.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return password;
    }

    public void addUser(String firstName, String lastName, String emailAddress, String username, String password) {
        sql = "INSERT INTO user(firstName, lastName, emailAddress, username, password)"
                + " VALUES('" + firstName + "', '" + lastName
                + "', '" + emailAddress + "', '" + username
                + "', '" + password + "')";

        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //-- GET INFO ------------------------------------------------------------
    public int getUserID(String userInput){
        int userID = 0;
        boolean isFound = false;

        try {
         //get via email
            sql = "SELECT userID FROM user"
                    + " WHERE emailAddress = '" + userInput + "'"; 
            
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                userID = rs.getInt("userID");
                isFound = true;
            }
            
         //get via username
            if(!isFound)
            {
                sql = "SELECT userID FROM user"
                        + " WHERE username = '" + userInput + "'";
                
                rs = stmt.executeQuery(sql);
                if(rs.next()){
                    userID = rs.getInt("userID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userID;
    }
    
    public String getFirstName(int userID){
        String firstName = "";

        try {
            sql = "SELECT firstName FROM user"
                    + " WHERE userID = " + userID;
            
            rs = stmt.executeQuery(sql);
            
            if(rs.next())
            {
                firstName = rs.getString("firstName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return firstName;
    }
    
    public String getLastName(int userID){
        String lastName = "";

        try {
            sql = "SELECT lastName FROM user"
                    + " WHERE userID = " + userID;
            
            rs = stmt.executeQuery(sql);
            
            if(rs.next())
            {
                lastName = rs.getString("lastName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lastName;
    }
    
    public String getEmailAddress(int userID){
        String emailAddress = "";

        try {
            sql = "SELECT emailAddress FROM user"
                    + " WHERE userID = " + userID;
            
            rs = stmt.executeQuery(sql);
            
            if(rs.next())
            {
                emailAddress = rs.getString("emailAddress");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return emailAddress;
    }
    
    public Date getBirthDay(int userID){
        Date birthDay = null;

        try {
            sql = "SELECT birthDate FROM user"
                    + " WHERE userID = " + userID;
            
            rs = stmt.executeQuery(sql);
            
            if(rs.next())
            {
                birthDay = rs.getDate("birthDate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return birthDay;
    }
}
