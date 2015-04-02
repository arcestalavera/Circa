/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Classes.Event;
import Classes.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

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
            if (rs.next()) {
                password = rs.getString("password");
                isFound = true;
            }

            //get via username
            if (!isFound) {
                sql = "SELECT password FROM user"
                        + " WHERE username = '" + userInput + "'";

                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    password = rs.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password;
    }

    public void addUser(String firstName, String lastName, String emailAddress, Date birthDate, String username, String password) {
        sql = "INSERT INTO user(firstName, lastName, emailAddress, birthDate, username, password)"
                + " VALUES(?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            preparedStmt.setString(1, firstName);
            preparedStmt.setString(2, lastName);
            preparedStmt.setString(3, emailAddress);
            preparedStmt.setDate(4, birthDate);
            preparedStmt.setString(5, username);
            preparedStmt.setString(6, password);

            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-- GET INFO ------------------------------------------------------------
    public int getUserID(String userInput) {
        int userID = 0;
        boolean isFound = false;

        try {
            //get via email
            sql = "SELECT userID FROM user"
                    + " WHERE emailAddress = '" + userInput + "'";

            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                userID = rs.getInt("userID");
                isFound = true;
            }

            //get via username
            if (!isFound) {
                sql = "SELECT userID FROM user"
                        + " WHERE username = '" + userInput + "'";

                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    userID = rs.getInt("userID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userID;
    }

    public ArrayList<Event> getEvents(int userID) {
        ArrayList<Event> eventList = new ArrayList<>();
        String eventName, venue, type, description, eventPicture;
        int eventID;
        Date startDate, endDate;
        User host;

        try {

            host = getUserDetails(userID);

            sql = "SELECT * FROM event"
                    + " WHERE hostID = " + userID;

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                eventID = rs.getInt("eventID");
                eventName = rs.getString("name");
                venue = rs.getString("venue");
                eventPicture = rs.getString("eventPicture");
                type = rs.getString("type");
                description = rs.getString("description");
                startDate = new Date(rs.getTimestamp("startDate").getTime());
                endDate = new Date(rs.getTimestamp("endDate").getTime());

                eventList.add(new Event(eventID, eventName, venue, type, description, startDate, endDate, host, eventPicture));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (eventList.isEmpty()) {
            return null;
        } else {
            return eventList;
        }
    }

    public void addEvent(int hostID, String name, Timestamp startDate, Timestamp endDate, String venue, String type, String description) {
        int maxEvent = 1;

        try {
            sql = "SELECT MAX(eventID) FROM event";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                maxEvent = rs.getInt("MAX(eventID)") + 1;
            }

            sql = "INSERT INTO EVENT(eventID, name, startDate, endDate, venue, type, hostID, description)"
                    + " VALUES(" + maxEvent + ", '" + name + "', '" + startDate + "', '" + endDate + "', '" + venue + "', '" + type + "', " + hostID + ", '" + description + "')";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserDetails(int userID) {
        User user = null;
        String firstName, lastName, emailAddress, profilePicture;
        Date birthDate;

        sql = "SELECT * FROM user"
                + " WHERE userID = " + userID;

        try {
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                emailAddress = rs.getString("emailAddress");
                birthDate = rs.getDate("birthDate");
                profilePicture = rs.getString("profilePicture");

                user = new User(userID, firstName, lastName, emailAddress, birthDate, profilePicture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public Event getEventDetails(int eventID) {
        Event event = null;
        String eventName, venue, type, description, eventPicture;
        Date startDate, endDate;
        int hostID = 0;
        User host;

        try {
            //get event
            sql = "SELECT * FROM event"
                    + " WHERE eventID = " + eventID;

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                eventName = rs.getString("name");
                venue = rs.getString("venue");
                eventPicture = rs.getString("eventPicture");
                type = rs.getString("type");
                description = rs.getString("description");
                startDate = new Date(rs.getTimestamp("startDate").getTime());
                endDate = new Date(rs.getTimestamp("endDate").getTime());
                hostID = rs.getInt("hostID");
                
                //get host details
                host = getUserDetails(hostID);
                event = new Event(eventID, eventName, venue, type, description, startDate, endDate, host, eventPicture);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return event;
    }
}
