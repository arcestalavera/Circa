
package Classes;

/**
 *
 * @author Arren Antioquia
 */
public class InviteToEvent {
    private User host;
    private Event event;
    private User invited;
    private String status;
    
    public InviteToEvent(User host, Event event, User invited, String status){
        this.host = host;
        this.event = event;
        this.invited = invited;
        this.status = status;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getInvited() {
        return invited;
    }

    public void setInvited(User invited) {
        this.invited = invited;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
