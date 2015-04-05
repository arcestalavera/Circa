/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Arces
 */
public class Event {
    private int eventID;
    private String eventName, venue, type, description, eventPicture;
    private User host;
    private Date startDate, endDate;
    private ArrayList<Post> postList;
    private ArrayList<User> attendingList, invitedList, requestList;
    private boolean isDeleted;
    
    public Event(int eventID, String eventName, String venue, String type, String description, Date startDate, Date endDate, User host, String eventPicture, boolean isDeleted){
        this.eventID = eventID;
        this.eventName = eventName;
        this.venue = venue;
        this.type = type;
        this.description = description;
        this.host = host;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventPicture = eventPicture;
        this.isDeleted = isDeleted;
        
        attendingList = new ArrayList<>();
        invitedList = new ArrayList<>();
        requestList = new ArrayList<>();
    }
    
    public void addAttending(User attendee){
        getAttendingList().add(attendee);
    }
    
    public void removeAttending(User attendee){
        getAttendingList().remove(attendee);
    }
    
    public void addInvited(User invited){
        getInvitedList().add(invited);
    }
    
    public void removeInvited(User invited){
        getInvitedList().remove(invited);
    }
    
    public void addRequest(User requestor){
        getRequestList().add(requestor);
    }
    
    public void removeRequest(User requestor){
        getRequestList().remove(requestor);
    }
    
    public void addPost(Post post){
        getPostList().add(post);
    }
    
    public void removePost(Post post){
        getPostList().remove(post);
    }

    /**
     * @return the eventID
     */
    public int getEventID() {
        return eventID;
    }

    /**
     * @param eventID the eventID to set
     */
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    /**
     * @return the eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @param eventName the eventName to set
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * @return the venue
     */
    public String getVenue() {
        return venue;
    }

    /**
     * @param venue the venue to set
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the host
     */
    public User getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(User host) {
        this.host = host;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the postList
     */
    public ArrayList<Post> getPostList() {
        return postList;
    }

    /**
     * @param postList the postList to set
     */
    public void setPostList(ArrayList<Post> postList) {
        this.postList = postList;
    }

    /**
     * @return the attendingList
     */
    public ArrayList<User> getAttendingList() {
        return attendingList;
    }

    /**
     * @param attendingList the attendingList to set
     */
    public void setAttendingList(ArrayList<User> attendingList) {
        this.attendingList = attendingList;
    }

    /**
     * @return the invitedList
     */
    public ArrayList<User> getInvitedList() {
        return invitedList;
    }

    /**
     * @param invitedList the invitedList to set
     */
    public void setInvitedList(ArrayList<User> invitedList) {
        this.invitedList = invitedList;
    }

    /**
     * @return the requestList
     */
    public ArrayList<User> getRequestList() {
        return requestList;
    }

    /**
     * @param requestList the requestList to set
     */
    public void setRequestList(ArrayList<User> requestList) {
        this.requestList = requestList;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }


    public String getEventPicture() {
        return eventPicture;
    }

    public void setEventPicture(String eventPicture) {
        this.eventPicture = eventPicture;
    }
    /**
     * @return the isDeleted
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;

    }
}
