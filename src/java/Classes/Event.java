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
    private String eventName, venue, type;
    private User host;
    private Date startDate, endDate;
    private ArrayList<Post> postList;
    private ArrayList<User> attendingList, invitedList, requestList;
    
    public Event(int eventID, String eventName, String venue, String type, Date startDate, Date endDate, User host){
        this.eventID = eventID;
        this.eventName = eventName;
        this.venue = venue;
        this.type = type;
        this.host = host;
        this.startDate = startDate;
        this.endDate = endDate;
        
        attendingList = new ArrayList<>();
        invitedList = new ArrayList<>();
        requestList = new ArrayList<>();
    }
    
    public void addAttending(User attendee){
        attendingList.add(attendee);
    }
    
    public void removeAttending(User attendee){
        attendingList.remove(attendee);
    }
    
    public void addInvited(User invited){
        invitedList.add(invited);
    }
    
    public void removeInvited(User invited){
        invitedList.remove(invited);
    }
    
    public void addRequest(User requestor){
        requestList.add(requestor);
    }
    
    public void removeRequest(User requestor){
        requestList.remove(requestor);
    }
    
    public void addPost(Post post){
        postList.add(post);
    }
    
    public void removePost(Post post){
        postList.remove(post);
    }
}
