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
                    <a href = "User?id=${loggedUser.getUserID()}" class = "text">${loggedUser.getFirstName()}</a>
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
        %>

        <div class = "profTop"><IMG class="profPic" src= "<%=userDetails.getProfilePicture()%>"/> <IMG class="profCover" src="img/home/coverfestival.jpg"/></div>

        <div class="infoDiv">
            <div class="infoText"><%=userDetails.getFirstName()%> <%=userDetails.getLastName()%></div>
            <div class="infoTitle">Clusters | Events</div> 
            <div class="infoTitleContent"> 126 || 7</div>
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

                        if (eventList != null) {
                            for (int i = 0; i < eventList.size(); i++) {
                    %>
                    <h4 class = "event-header"><b><%=eventList.get(i).getEventName()%></b></h4>
                    <div class = "event-description">
                        <%=eventList.get(i).getDescription()%><br/>
                        <%
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        %>
                        <h6><%=eventList.get(i).getType()%> | 
                            <%=dateFormat.format(eventList.get(i).getStartDate())%> - 
                            <%=dateFormat.format(eventList.get(i).getEndDate())%>
                        </h6><br><button onclick = "goToEvent('<%=eventList.get(i).getEventID()%>')">View Event</button>
                    </div>
                    <%
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