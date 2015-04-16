
package Classes;

/**
 *
 * @author Arren Antioquia
 */
public class RequestToJoin {
    private User host;
    private Event event;
    private User requestor;
    private String status;
    
    public RequestToJoin(User host, Event event, User requestor, String status){
        this.host = host;
        this.event = event;
        this.requestor = requestor;
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

    public User getRequestor() {
        return requestor;
    }

    public void setInvited(User requestor) {
        this.requestor = requestor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
