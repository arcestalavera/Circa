/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Classes.Cluster;
import Classes.Comment;
import Classes.Event;
import Classes.InviteToEvent;
import Classes.Like;
import Classes.Post;
import Classes.RequestToJoin;
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
                    + " WHERE hostID = " + userID + " AND isDeleted = 0";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                eventID = rs.getInt("eventID");
                Event event = getEventDetails(eventID);

                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventList;
    }

    public int addEvent(int hostID, String name, Timestamp startDate, Timestamp endDate, String venue, String type, String description) {
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

        return maxEvent;
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
        boolean isDeleted;

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
                isDeleted = rs.getBoolean("isDeleted");

                //get host details
                host = getUserDetails(hostID);
                event = new Event(eventID, eventName, venue, type, description, startDate, endDate, host, eventPicture, isDeleted);
                event.setAttendingList(getJoining(event.getEventID()));
                event.setRequestList(getRequests(event.getEventID()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return event;
    }

    
    
    public String getClusterName(int clusterID) {
        Statement stmt;
        ResultSet rs;
        String clusterName = "";

        sql = "SELECT * FROM cluster"
                + " WHERE clusterID = " + clusterID;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                clusterName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clusterName;
    }
    
    public ArrayList<InviteToEvent> getPendingInviteToEvent(int userID){
        Statement stmt;
        ResultSet rs;
        ArrayList<InviteToEvent> inviteList = new ArrayList<>();

        sql = "SELECT * FROM invite_to_event"
                + " WHERE invitedID = " + userID + " AND status = 'Pending'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User host = getUserDetails(rs.getInt("hostID"));
                Event event = getEventDetails(rs.getInt("eventID"));
                User invited = getUserDetails(rs.getInt("invitedID"));
                
                InviteToEvent invite = new InviteToEvent(host, event, invited, "Pending");

                inviteList.add(invite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inviteList;
    }
    
    public ArrayList<RequestToJoin> getPendingRequestToJoin(int userID){
        Statement stmt;
        ResultSet rs;
        ArrayList<RequestToJoin> requestList = new ArrayList<>();

        sql = "SELECT * FROM request_to_join"
                + " WHERE hostID = " + userID + " AND status = 'Pending'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User host = getUserDetails(rs.getInt("hostID"));
                Event event = getEventDetails(rs.getInt("eventID"));
                User requestor = getUserDetails(rs.getInt("requestorID"));
                
                RequestToJoin invite = new RequestToJoin(host, event, requestor, "Pending");

                requestList.add(invite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestList;
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
        boolean isDeleted;
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
                isDeleted = rs.getBoolean("isDeleted");

                poster = getUserDetails(userID);
                event = getEventDetails(eventID);
                post = new Post(postID, postText, poster, event, isDeleted);
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
                    + " WHERE eventID = " + eventID
                    + " ORDER BY postID DESC";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                postID = rs.getInt("postID");
                Post post = getPostDetails(postID);
                post.setCommentList(getComments(postID));
                post.setLikeList(getLikes(postID));
                postList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postList;
    }

    public int addPost(int eventID, int userID, String postText) {
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
        
        return maxPost;
    }

    public ArrayList<Comment> getComments(int postID) {
        Statement stmt;
        ResultSet rs;
        ArrayList<Comment> commentList = new ArrayList<>();
        User commenter;
        String commentText;
        int userID, commentID;

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

    public int addComment(int postID, String comment, int userID) {
        Statement stmt;
        ResultSet rs;
        int maxComment = 0;

        try {
            stmt = con.createStatement();
            sql = "SELECT MAX(commentID) from comment";

            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                maxComment = rs.getInt("MAX(commentID)") + 1;
            }

            sql = "INSERT INTO comment(postID, commentText, userID, commentID)"
                    + " VALUES(" + postID + ", '" + comment + "', " + userID + ", " + maxComment + ")";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return maxComment;
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

    public ArrayList<User> getUserBuddies(int userID) {
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

                if (friend_1 == userID) {
                    user = getUserDetails(friend_2);
                } else if (friend_2 == userID) {
                    user = getUserDetails(friend_1);
                }
                userBuddies.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userBuddies;
    }

    public void deletePost(int postID) {
        Statement stmt;
        try {
            stmt = con.createStatement();

            sql = "UPDATE post"
                    + " SET isDeleted = true"
                    + " WHERE postID = " + postID;

            stmt.executeUpdate(sql);

            sql = "DELETE FROM comment"
                    + " WHERE postID = " + postID;

            stmt.executeUpdate(sql);
            
            sql = "DELETE FROM likes"
                    + " WHERE postID = " + postID;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteComment(int commentID) {
        Statement stmt;

        try {
            stmt = con.createStatement();

            sql = "DELETE FROM comment"
                    + " where commentID = " + commentID;

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void likePost(int postID, int userID) {
        Statement stmt;

        try {
            stmt = con.createStatement();

            sql = "INSERT INTO likes"
                    + " VALUES(" + postID + ", " + userID + ")";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlikePost(int postID, int userID) {
        Statement stmt;

        try {
            stmt = con.createStatement();

            sql = "DELETE FROM likes"
                    + " WHERE postID = " + postID + " AND userID = " + userID;

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isLiked(int postID, int userID) {
        Statement stmt;
        ResultSet rs;
        boolean isLiked = false;

        try {
            stmt = con.createStatement();
            sql = "SELECT * FROM likes"
                    + " WHERE postID = " + postID + " AND userID = " + userID;

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                isLiked = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isLiked;
    }

    public void deleteEvent(int eventID) {
        Event event;
        ArrayList<Post> postList;
        Statement stmt;
        try {
            stmt = con.createStatement();
            event = getEventDetails(eventID);
            sql = "UPDATE event"
                    + " SET isDeleted = true"
                    + " WHERE eventID = " + eventID;

            stmt.executeUpdate(sql);

            postList = event.getPostList();
            if (postList != null) {
                for (Post post : postList) {
                    deletePost(post.getPostID());
                }
            }

            sql = "DELETE FROM request_to_join"
                    + " WHERE eventID = " + eventID;

            stmt.executeUpdate(sql);

            sql = "DELETE FROM attending_an_event"
                    + " WHERE eventID = " + eventID;

            stmt.executeUpdate(sql);

            sql = "DELETE FROM invite_to_event"
                    + " WHERE eventID = " + eventID;

            stmt.executeUpdate(sql);

            sql = "DELETE FROM event_view_restriction"
                    + " WHERE eventID = " + eventID;

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editClusterName(int clusterID, String name) {
        sql = "UPDATE cluster SET name = ?"
                + " WHERE clusterID = ?;";

        try {
            PreparedStatement preparedStmt = con.prepareStatement(sql);

            preparedStmt.setString(1, name);
            preparedStmt.setInt(2, clusterID);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isViewableToCluster(int eventID, int clusterID) {
        Statement stmt;
        ResultSet rs;
        boolean isViewable = false;

        try {
            stmt = con.createStatement();
            sql = "SELECT * FROM event_view_restriction"
                    + " WHERE eventID = " + eventID + " AND clusterID = " + clusterID + ";";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                isViewable = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isViewable;
    }

    public boolean isClusterMember(int userID, int clusterID) {
        Statement stmt;
        ResultSet rs;
        boolean isClusterMember = false;

        try {
            stmt = con.createStatement();
            sql = "SELECT * FROM add_user_to_cluster"
                    + " WHERE addedID = " + userID + " AND clusterID = " + clusterID + ";";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                isClusterMember = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isClusterMember;
    }

    public void addUserToCluster(int adderID, int addedID, int clusterID) {

        sql = "INSERT INTO add_user_to_cluster(adderID, addedID, clusterID) "
                + "VALUES(?, ?, ?)";

        try {
            PreparedStatement preparedStmt = con.prepareStatement(sql);

            preparedStmt.setInt(1, adderID);
            preparedStmt.setInt(2, addedID);
            preparedStmt.setInt(3, clusterID);

            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUsertoCluster(int addedID, int clusterID) {

        sql = "DELETE FROM add_user_to_cluster "
                + "WHERE addedID = ? AND clusterID = ?;";

        try {
            PreparedStatement preparedStmt = con.prepareStatement(sql);

            preparedStmt.setInt(1, addedID);
            preparedStmt.setInt(2, clusterID);

            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void editEvent(int eventID, String name, Timestamp startDate, Timestamp endDate, String venue, String type, String description) {
        Statement stmt;

        try {
            stmt = con.createStatement();
            sql = "UPDATE event"
                    + " SET name = '" + name + "', startDate = '" + startDate + "', endDate = '" + endDate + "',"
                    + " venue = '" + venue + "', type = '" + type + "', description = '" + description + "'"
                    + " WHERE eventID = " + eventID;
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editPost(int postID, String postText) {
        Statement stmt;

        try {
            stmt = con.createStatement();
            sql = "UPDATE post"
                    + " SET postText = '" + postText + "'"
                    + " WHERE postID = " + postID;

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Like> getLikes(int postID) {
        ArrayList<Like> likeList = new ArrayList<>();
        Statement stmt;
        ResultSet rs;

        try {
            stmt = con.createStatement();

            Post post = getPostDetails(postID);

            sql = "SELECT * FROM likes"
                    + " WHERE postID = " + postID;

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int userID = rs.getInt("userID");
                User liker = getUserDetails(userID);

                likeList.add(new Like(post, liker));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("LIKE SIZE = " + likeList.size());
        return likeList;
    }

    public void addJoin(int eventID, int attendingID) {
        Statement stmt;

        try {
            stmt = con.createStatement();

            sql = "INSERT INTO attending_an_event"
                    + " VALUES(" + eventID + ", " + attendingID + ")";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addJoinRequest(int hostID, int eventID, int requestorID) {
        Statement stmt;

        try {
            stmt = con.createStatement();

            sql = "INSERT INTO request_to_join"
                    + " VALUES(" + hostID + ", " + eventID + ", " + requestorID + ", 'Pending')";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void answerRequest(int hostID, int eventID, int requestorID, String action) {
        Statement stmt;

        try {
            stmt = con.createStatement();

            sql = "UPDATE request_to_join"
                    + " SET status = '" + action + "'"
                    + " WHERE hostID = " + hostID + " AND eventID = " + eventID + " AND requestorID = " + requestorID;

            stmt.executeUpdate(sql);
            if (action.equals("Approved")) {
                addJoin(eventID, requestorID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getJoining(int eventID) {
        Statement stmt;
        ResultSet rs;
        ArrayList<User> joiningList = new ArrayList<>();
        int attendingID;
        try {
            stmt = con.createStatement();

            sql = "SELECT * FROM attending_an_event"
                    + " WHERE eventID = " + eventID;

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                attendingID = rs.getInt("attendingID");
                User attendee = getUserDetails(attendingID);

                joiningList.add(attendee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return joiningList;
    }

    public boolean isJoining(int eventID, int attendingID) {
        Statement stmt;
        ResultSet rs;
        boolean isJoining = false;

        try {
            stmt = con.createStatement();
            sql = "SELECT * FROM attending_an_event"
                    + " WHERE eventID = " + eventID + " AND attendingID = " + attendingID + ";";

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                isJoining = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isJoining;
    }

    public void deleteJoin(int eventID, int attendingID) {
        Statement stmt;

        try {
            stmt = con.createStatement();

            sql = "DELETE FROM attending_an_event"
                    + " WHERE eventID = " + eventID + " AND attendingID = " + attendingID;

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getRequests(int eventID) {
        Statement stmt;
        ResultSet rs;
        ArrayList<User> requestList = new ArrayList<>();
        int requestorID;
        try {
            stmt = con.createStatement();

            sql = "SELECT * FROM request_to_join"
                    + " WHERE eventID = " + eventID + " AND status = 'Pending'";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                requestorID = rs.getInt("requestorID");
                User attendee = getUserDetails(requestorID);

                requestList.add(attendee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requestList;
    }

    public boolean isInvited(int eventID, int invitedID) {
        boolean isInvited = false;
        Statement stmt;
        ResultSet rs;

        try {
            stmt = con.createStatement();
            sql = "SELECT * FROM invite_to_event"
                    + " WHERE eventID = " + eventID + " AND invitedID = " + invitedID + " AND status = 'Pending'";

            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                isInvited = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isInvited;
    }
    
    public boolean isMemberOfCluster(int addedID, int clusterID){
        boolean isMember = false;
        Statement stmt;
        ResultSet rs;

        try {
            stmt = con.createStatement();
            sql = "SELECT * FROM add_user_to_cluster"
                    + " WHERE clusterID = " + clusterID + " AND addedID = " + addedID + ";";

            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                isMember = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isMember;
    }
    
    public boolean isRequested(int eventID, int requestorID) {
        Statement stmt;
        ResultSet rs;
        boolean isRequested = false;

        try {
            stmt = con.createStatement();
            sql = "SELECT * FROM request_to_join"
                    + " WHERE eventID = " + eventID + " AND requestorID = " + requestorID + " AND status = 'Pending'";

            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                isRequested = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isRequested;
    }

    public boolean isBuddy(int userID, int buddyID) {
        Statement stmt;
        ResultSet rs;
        boolean isBuddy = false;

        try {
            stmt = con.createStatement();

            sql = "select * from buddy"
                    + " WHERE friend_1 = " + userID + " AND friend_2 = " + buddyID
                    + " UNION"
                    + " select * from buddy"
                    + " WHERE friend_2 = " + userID + " AND friend_1 = " + buddyID;
            
            rs = stmt.executeQuery(sql);
            
            if(rs.next())
            {
                isBuddy = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isBuddy;
    }
}
