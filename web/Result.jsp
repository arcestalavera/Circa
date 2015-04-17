<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Classes.RequestToJoin"%>
<%@page import="Classes.InviteToEvent"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Event"%>
<%@page import="Database.CircaDatabase"%>
<%@page import="Classes.User"%>
<html>
    <head>
        <title>Search Result | Circa</title>
        <link rel ="shortcut icon" href="img/CircaLogoIcon.ico">
        
        <!-- HEADER SCRIPT -->
        <script type = "text/javascript" src = "js/jquery-1.11.2.min.js">
        </script>
        <script type = "text/javascript" src = "js/header.js">
        </script>
        <script type = "text/javascript" src = "js/Result.js">
        </script>
        <link rel="stylesheet" type="text/css" 	media="all" href="css/header.css" />
        <!-- END HEADER -->

        <link rel="stylesheet" type="text/css" 	media="all" href="css/result.css" />
        <script>
            $(document).ready(function() {
                $(".result-img").hover(function() {
                    $(this).next(".result-img-caption").fadeIn(500);
                });
                $(".result-img").mouseout(function() {
                    $(this).next(".result-img-caption").fadeOut(500);
                });
            });
        </script>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body bgcolor="#f4f4f4">
        <%  // header -> used to get all info
            User user = (User) request.getSession().getAttribute("loggedUser");
            CircaDatabase db = CircaDatabase.getInstance();
            user.setEventList(db.getEvents(user.getUserID()));
            for(Event event : user.getEventList()){
                event.setPostList(db.getPosts(event.getEventID()));
            }
            user.setBuddyList(db.getUserBuddies(user.getUserID()));
            user.setClusters(db.getUserClusters(user.getUserID()));

            for (User buddy : user.getBuddyList()) {
                buddy.setClusters(db.getUserClusters(buddy.getUserID()));
                buddy.setEventList(db.getEvents(buddy.getUserID()));
                for(Event event : buddy.getEventList()){
                    event.setPostList(db.getPosts(event.getEventID()));
                }
            }
        %>
        <!-- HEADER -->
        <div id = "header-whole">
            <div id = "header-temp">
            </div>
            <div id = "header">
                <form id = "header-left" action="Search" method="get">
                    <input type = "text" placeholder = "Search for a Person / Event" name = "keyword" class = "search-input"/>
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

        <div id = "user-main-panel">
            <div id = "user-info-panel">
                <div id = "user-main-info">
                    <div id = "user-main-info-container">
                        <a href = "User?action=view&id=<%=user.getUserID()%>">
                            <img src = "<%=user.getProfilePicture()%>" id = "user-image" title = "<%=user.getFirstName()%> <%=user.getLastName()%>"/>
                        </a>
                        <div id = "user-main-info-name-div">
                            <p id = "user-name">
                                <a href = "User?action=view&id=<%=user.getUserID()%>" class = "link">
                                    <%=user.getFirstName()%> <%=user.getLastName()%>
                                </a>
                            </p>
                        </div>
                    </div>
                </div>
                <div id = "trending-div">
                    <div id = "trending-tag-div">
                        <p id = "trending-tag">Trending</p>
                    </div>
                    
                    <ul id = "trending-list">
                        <%  ArrayList<Event> trends = db.getTrendingEvents();
                            for(Event event : trends){
                        %>
                        <li class = "trending-item">
                            <p class = "trending-text">
                                <a href="Event?action=view&id=<%=event.getEventID()%>" class = "link-normal">
                                    <%=event.getEventName()%>
                                </a>
                            </p>
                        </li>
                        <%}%>
                    </ul>
                </div>
            </div>
            <div id= "search-result-panel">
                <div id="search-result-header-div">
                    <div id="event-search-result-info-container">
                        <p id="search-result-header-text">${keyword}</p>
                        <p id = "search-mode">
                            <a id="user-search-mode">People</a> |
                            <a id="event-search-mode">Events</a>
                        </p>
                    </div>
                </div>
                <div id="search-result-div">
                    <div id="user-search-result-div">
                        <ul id ="user-search-list">
                            <%  String keyword = request.getParameter("keyword");
                                ArrayList<User> userList = db.searchUser(keyword);
                                
                                for(User userItem: userList){
                            %>
                            <li class="user-search-item">
                                <a href="User?action=view&id=<%=userItem.getUserID()%>">
                                    <img src="<%=userItem.getProfilePicture()%>" class="user-search-img" title="<%=userItem.getFirstName()%> <%=userItem.getLastName()%>"width="50px" height="50px"/>
                                </a>
                                <div class="user-search-info-div">
                                    <a href="User?action=view&id=<%=userItem.getUserID()%>" class="link">
                                        <p class="user-search-name"><%=userItem.getFirstName()%> <%=userItem.getLastName()%></p>
                                    </a>
                                </div>
                            </li>
                            <%}%>
                        </ul>
                    </div>
                    <div id="event-search-result-div">
                        <ul id ="event-search-list">
                            <%  ArrayList<Event> eventList = db.searchEvent(keyword);
                                SimpleDateFormat ddMMMMyyFormat = new SimpleDateFormat("MMM dd, yyyy");
                                for(Event event:eventList){
                                    String strDate = ddMMMMyyFormat.format(event.getStartDate());
                                    boolean isPrinted = false;
                                    for(int i = 0; i < event.getViewRestriction().size() && !isPrinted; i++){
                                        int clusterID = event.getViewRestriction().get(i);
                                        if((clusterID == 0) || 
                                           (clusterID == 1 && db.isBuddy(user.getUserID(), event.getHost().getUserID())) ||
                                           (db.isClusterMember(user.getUserID(), clusterID)) ||
                                           (event.getHost().getUserID() == user.getUserID())){
                                                isPrinted = true;
                            %>
                            <li class="event-search-item">
                                <a href="Event?action=view&id=<%=event.getEventID()%>">
                                    <img src="<%=event.getEventPicture()%>" class="event-search-img" title="<%=event.getEventName()%>"width="50px" height="50px"/>
                                </a>
                                <div class="event-search-info-div">
                                    <a href="Event?action=view&id=<%=event.getEventID()%>" class="link">
                                        <p class="event-search-name"><%=event.getEventName()%></p>
                                    </a>
                                    <p class = "result-event-info"><%=event.getVenue()%> - <%=strDate%></p>
                                </div>
                            </li>
                            <%
                                        }
                                    }
                                }
                            %>
                        </ul>
                    </div>
                </div>
            </div>
            <div id = "search-result-other-panel">
                <%  if(user.getEventList().size() != 0){
                %>
                <div class = "result-other-event-panel">
                    <div class = "event-tag-div">
                        <p class = "event-tag">Your Events</p>
                    </div>

                    <ul class = "result-event-list">
                        <%  for(int i = 0; i < user.getEventList().size(); i++){
                                Event event = user.getEventList().get(i);
                                
                                String strDate = ddMMMMyyFormat.format(event.getStartDate());
                        %>
                        <li class = "result-event-item">
                            <a href = "Event?action=view&id=<%=event.getEventID()%>">
                                <img src = "<%=event.getEventPicture()%>" class = "result-event-pic" title = "<%=event.getEventName()%>" width = "40px" height="40px" />
                            </a>
                            <div class = "result-event-info-div">
                                
                                <p class = "result-event-name">
                                    <a href = "Event?action=view&id=<%=event.getEventID()%>" class = "link">
                                        <%=event.getEventName()%>
                                    </a>
                                </p>
                                
                                <p class = "result-event-info"><%=event.getVenue()%> - <%=strDate%></p>
                            </div>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                </div>
                <%}%>
            </div>
        </div>
    </body>
</html>