/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Classes.Cluster;
import Classes.Comment;
import Classes.Event;
import Classes.Post;
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
    private static CircaDatabase databaseInstance = new CircaDatabase();

    private CircaDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String host = "jdbc:mysql://127.0.0.1:3306/Circa?user=root";
            String uUser = "root";
            String uPass = "admin";

            con = DriverManager.getConnection(host, uUser, uPass);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CircaDatabase getInstance() {
        return databaseInstance;
    }

    public String getPassword(String userInput) {
        Statement stmt;
        ResultSet rs;
        String password = "";
        boolean isFound = false;

        try {
            stmt = con.createStatement();
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
        Statement stmt;
        ResultSet rs;
        int userID = 0;
        boolean isFound = false;

        try {
            stmt = con.createStatement();
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
        Statement stmt;
        ResultSet rs;
        ArrayList<Event> eventList = new ArrayList<>();
        int eventID;

        try {
            stmt = con.createStatement();

            sql = "SELECT * FROM event"
                    + " WHERE hostID = " + userID;

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                eventID = rs.getInt("eventID");
                Event event = getEventDetails(eventID);
                event.setPostList(getPosts(eventID));

                eventList.add(event);
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
        Statement stmt;
        ResultSet rs;
        int maxEvent = 1;

        try {
            stmt = con.createStatement();
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
        Statement stmt;
        ResultSet rs;
        User user = null;
        String firstName, lastName, emailAddress, profilePicture;
        Date birthDate;

        sql = "SELECT * FROM user"
                + " WHERE userID = " + userID;

        try {
            stmt = con.createStatement();
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
        Statement stmt;
        ResultSet rs;
        Event event = null;
        String eventName, venue, type, description, eventPicture;
        Date startDate, endDate;
        int hostID = 0;
        User host;

        try {
            stmt = con.createStatement();

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

    public ArrayList<Cluster> getUserClusters(int userID) {
        Statement stmt;
        ResultSet rs;
        ArrayList<Cluster> userClusters = new ArrayList<>();

        sql = "SELECT * FROM cluster"
                + " WHERE creatorID = " + userID + " AND isDeleted = 0";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int clusterID = rs.getInt("clusterID");
                String clusterName = rs.getString("name");

                Cluster cluster = new Cluster(clusterID, clusterName);
                cluster.setMemberList(getClusterMembers(cluster.getClusterID()));

                userClusters.add(cluster);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userClusters;
    }

    public ArrayList<User> getClusterMembers(int clusterID) {
        Statement stmt;
        ResultSet rs;
        ArrayList<User> clusterMembers = new ArrayList<>();

        sql = "SELECT * FROM add_user_to_cluster"
                + " WHERE clusterID = " + clusterID;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int clusterAddedID = rs.getInt("addedID");
                User user = getUserDetails(clusterAddedID);
                clusterMembers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clusterMembers;
    }

    public Post getPostDetails(int postID) {
        Statement stmt;
        ResultSet rs;
        Post post = null;
        String postText;
        int userID = 0, eventID = 0;
        User poster;
        Event event;

        try {
            stmt = con.createStatement();
            sql = "SELECT * FROM post"
                    + " WHERE postID = " + postID;

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                userID = rs.getInt("userID");
                eventID = rs.getInt("eventID");
                postText = rs.getString("postText");

                poster = getUserDetails(userID);
                event = getEventDetails(eventID);
                post = new Post(postID, postText, poster, event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public ArrayList<Post> getPosts(int eventID) {
        Statement stmt;
        ResultSet rs;
        ArrayList<Post> postList = new ArrayList<>();
        int postID = 0;

        try {
            stmt = con.createStatement();

            sql = "SELECT * FROM post"
                    + " WHERE eventID = " + eventID;

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                postID = rs.getInt("postID");
                Post post = getPostDetails(postID);
                post.setCommentList(getComments(postID));
                postList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postList;
    }

    public void addPost(int eventID, int userID, String postText) {
        Statement stmt;
        ResultSet rs;
        int maxPost = 1;

        try {
            stmt = con.createStatement();
            sql = "SELECT MAX(postID) FROM post";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                maxPost = rs.getInt("MAX(postID)") + 1;
            }

            sql = "INSERT INTO post(postID, eventID, userID, postText)"
                    + " VALUES(" + maxPost + ", " + eventID + ", " + userID + ", '" + postText + "')";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Comment> getComments(int postID) {
        Statement stmt;
        ResultSet rs;
        ArrayList<Comment> commentList = new ArrayList<>();
        User commenter;
        String commentText;
        int userID = 0, commentID = 0;

        sql = "SELECT * FROM comment"
                + " WHERE postID = " + postID;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                userID = rs.getInt("userID");
                commentText = rs.getString("commentText");
                commentID = rs.getInt("commentID");
                commenter = getUserDetails(userID);

                Comment comment = new Comment(commenter, commentText, commentID);
                commentList.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commentList;
    }

    public void addComment(int postID, String comment, int userID) {
        Statement stmt;
        ResultSet rs;
        int maxComment = 0;
        
        try {
            stmt = con.createStatement();
            sql = "SELECT MAX(commentID) from comment";
            
            rs = stmt.executeQuery(sql);
            if(rs.next())
            {
                maxComment = rs.getInt("MAX(commentID)");
            }
            
            sql = "INSERT INTO comment"
                    + " VALUES(" + postID + ", '" + comment + "', " + userID + ", " + maxComment + ")";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCluster(int clusterID) {

        sql = "UPDATE cluster SET isDeleted = ?"
                + " WHERE clusterID = ?;";

        try {
            PreparedStatement preparedStmt = con.prepareStatement(sql);

            preparedStmt.setBoolean(1, true);
            preparedStmt.setInt(2, clusterID);
            emptyClusterMembers(clusterID);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void emptyClusterMembers(int clusterID) {

        sql = "DELETE FROM add_user_to_cluster "
                + "WHERE clusterID = ?;";

        try {
            PreparedStatement preparedStmt = con.prepareStatement(sql);

            preparedStmt.setInt(1, clusterID);

            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewCluster(int creatorID, String clusterName) {

        sql = "INSERT INTO cluster(creatorID, Name) "
                + "VALUES(?, ?)";

        try {
            PreparedStatement preparedStmt = con.prepareStatement(sql);

            preparedStmt.setInt(1, creatorID);
            preparedStmt.setString(2, clusterName);

            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<User> getUserBuddies(int userID){
        ArrayList<User> userBuddies = new ArrayList<>();
        Statement stmt;
        ResultSet rs;

        try {
            stmt = con.createStatement();

            sql = "SELECT * FROM buddy"
                    + " WHERE friend_1 = " + userID + " or "
                    + "friend_2 = " + userID;

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int friend_1 = rs.getInt("friend_1");
                int friend_2 = rs.getInt("friend_2");
                
                User user = new User();
                
                if(friend_1 == userID){
                    user = getUserDetails(friend_2);
                }else if(friend_2 == userID){
                    user = getUserDetails(friend_1);
                }
                userBuddies.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userBuddies;
    }
}
