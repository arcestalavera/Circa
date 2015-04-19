<%@page import="Database.CircaDatabase"%>
<%@page import="Classes.User"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="Classes.Event"%>
<%@page import="java.util.ArrayList"%>
<html>
    <head>
        <title> ${userDetails.getFirstName()} ${userDetails.getLastName()} | Circa</title>
        <link rel ="shortcut icon" href="img/CircaLogoIcon.ico">

        <meta charset="UTF-8">
        <script type = "text/javascript" src = "js/jquery-1.11.2.min.js">
        </script>
        <script type = "text/javascript" src = "js/header.js">
        </script>
        <script type = "text/javascript" src = "js/UserPage.js"></script>
        <link rel="stylesheet" type="text/css" 	media="all" href="css/header.css" />
        <link rel="stylesheet" type="text/css" 	media="all" href="css/UserPage.css" />

    </head>

    <body bgcolor="#f4f4f4">

        <!-- HEADER -->
        <div id = "header-whole">
            <div id = "header-temp">
            </div>
            <div id = "header">
                <form id = "header-left">
                    <input type = "text" placeholder = "Search for a Person / Event" class = "search-input"/>
                    <a href = "Result.jsp"><input type = "submit" class = "search-button" value = ">"/></a>
                </form>
                <div id = "header-right">
                    <a href = "User?action=view&id=${loggedUser.getUserID()}" class = "text">${loggedUser.getFirstName()}</a>
                    <a href = "Cluster" class = "text">Clusters</a>
                    <a href = "Home" class = "text">Home</a>
                    <a href = "Logout" class = "text">Logout</a>
                </div>
            </div>
            <img src = "img\clusters\CircaLogo.png" class = "header-logo" />
        </div>
        <!-- END HEADER -->	 

        <%
            //get user details
            User userDetails = (User) request.getSession().getAttribute("userDetails");
            //get logged user
            User loggedUser = (User) request.getSession().getAttribute("loggedUser");
            CircaDatabase db = CircaDatabase.getInstance();
        %>

        <div class = "profTop"><IMG class="profPic" src= "<%=userDetails.getProfilePicture()%>"/> <IMG class="profCover" src="img/home/coverfestival.jpg"/></div>
        <div class = "float-left-div">
            <div class="infoDiv">
                <h2 class="infoText"><%=userDetails.getFirstName()%> <%=userDetails.getLastName()%></h2>
                <%
                    if (loggedUser.getUserID() != userDetails.getUserID()) {
                        if (!db.isBuddy(loggedUser.getUserID(), userDetails.getUserID())) {
                %>
                <div id = "buddy-action-div">
                    <form onsubmit = "return buddyAction('add')">
                        <input type = "submit" value = "Add Buddy" class = "buddy-button"/>
                    </form>
                </div>
                <%
                } else {
                %>
                <div id = "buddy-action-div">
                    <form onsubmit = "return buddyAction('delete')">
                        <input type = "submit" value = "Remove Buddy" class = "buddy-button"/>
                    </form>   
                </div>
                <%
                        }
                    }
                %>
                <p class="infoTitle">Clusters | Events</p> 
                <p class="infoTitleContent"> <%=userDetails.getClusters().size()%> || <%=userDetails.getEventList().size()%></p>
            </div>
                <%  userDetails.setBuddyList(db.getUserBuddies(userDetails.getUserID()));
                    if(userDetails.getBuddyList().size() != 0){
            %>
            <div class = "buddies-div">
                <div id = "buddies-tag-div">
                    <p id = "buddies-tag">Buddies</p>
                </div>
                <div id = "buddies-list-div">
                    <ul id = "buddies-list">
                        <%for(User buddy : userDetails.getBuddyList()){%>
                        <li class = "buddies-member">
                            <a href="User?action=view&id=<%=buddy.getUserID()%>">
                                <img src = "<%=buddy.getProfilePicture()%>" title = "<%=buddy.getFirstName()%> <%=buddy.getLastName()%>" width = "60px" height="60px"/>
                            </a>
                        </li>
                        <%}%>
                    </ul>
                </div>
            </div>
            <%}%>
        </div>
        <div class="postDiv">
            <ul class = "tabs">
                <li><a href="#showEvents"><b>Events</b></a></li>
                <li><a href="#showSchedule"><b>Schedule</b></a></li>
            </ul>
            <div id = "showEvents">
                <%
                    if (loggedUser.getUserID() == userDetails.getUserID()) {
                %>
                <h3 class = "text-heading" align = "center">Your Events<hr width = "70%"/></h3>
                    <%
                    } else {
                    %>
                <h3 class = "text-heading" align = "center">Events of <%=userDetails.getFirstName()%><hr width = "70%"/></h3>
                    <%
                        }
                    %>
                <div id = "event-body">
                    <%
                        if (loggedUser.getUserID() == userDetails.getUserID()) {
                    %>
                    <div class = "event-create" align = "center">
                        <button class = "event-create-button" onclick = "window.location.href = 'CreateEvent.jsp'"><b>Create Event!</b></button>
                    </div>
                    <%
                        }
                        ArrayList<Event> eventList = userDetails.getEventList();

                        if (!eventList.isEmpty()) {
                            for (int i = 0; i < eventList.size(); i++) {
                                if (!eventList.get(i).isDeleted()) {
                    %>
                    <h4 class = "event-header"><b><%=eventList.get(i).getEventName()%></b></h4>
                    <div class = "event-description">
                        <%=eventList.get(i).getDescription()%><br/>
                        <%
                            DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
                        %>
                        <h6><%=eventList.get(i).getType()%> | 
                            <%=dateFormat.format(eventList.get(i).getStartDate())%> - 
                            <%=dateFormat.format(eventList.get(i).getEndDate())%>
                        </h6><br>

                        <button type = "submit" onclick = "window.location.href = 'Event?action=view&id=<%=eventList.get(i).getEventID()%>'">View Event</button>

                    </div>
                    <%
                            }
                        }
                    } else {
                        if (loggedUser.getUserID() == userDetails.getUserID()) {
                    %>
                    <h3 class = "event-no-event">You haven't hosted any events yet.</h3>
                    <%
                    } else {

                    %>
                    <h3 class = "event-no-event"><%=userDetails.getFirstName()%> hasn't hosted any events yet.</h3>
                    <%
                            }
                        }
                    %>
                </div>
            </div>
            <div id = "showSchedule" align = "center">
                <%
                    if (loggedUser.getUserID() == userDetails.getUserID()) {
                %>
                <h3 class = "text-heading" align = "center">Your Schedule<hr width = "70%"/></h3>
                    <%
                    } else {
                    %>
                <h3 class = "text-heading" align = "center">Schedule of <%=userDetails.getFirstName()%><hr width = "70%"/></h3>
                    <%
                        }
                    %>
                <input type = "date" class = "schedule-date"/>
            </div>
        </div>
    </body>
</html>