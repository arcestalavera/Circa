<%@page import="Classes.RequestToJoin"%>
<%@page import="Classes.InviteToEvent"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Classes.Comment"%>
<%@page import="Classes.Post"%>
<%@page import="Classes.Cluster"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.Event"%>
<%@page import="Classes.Event"%>
<%@page import="Database.CircaDatabase"%>
<%@page import="Classes.User"%>
<html>
    <head>
        <title>Home | Circa </title>
        <link rel ="shortcut icon" href="img/CircaLogoIcon.ico">

        <meta charset="UTF-8">
        <script type = "text/javascript" src = "js/jquery-1.11.2.min.js">
        </script>
        <script type = "text/javascript" src = "js/header.js">
        </script>
        <script type = "text/javascript" src = "js/Home.js">
        </script>
        <link rel="stylesheet" type="text/css" 	media="all" href="css/header.css" />
        <link rel="stylesheet" type="text/css" 	media="all" href="css/Home.css" />
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
                <div id = "buddies-div">
                    <div id = "buddies-tag-div">
                        <p id = "buddies-tag">Buddies</p>
                    </div>

                    <ul id = "user-buddies-list">
                        <%  for (User buddy : user.getBuddyList()) {
                        %>
                        <li class = "buddies-member">
                            <a href = "User?action=view&id=<%=buddy.getUserID()%>">
                                <img src="<%=buddy.getProfilePicture()%>" class = "buddies-member-img" title = "<%=buddy.getFirstName()%> <%=buddy.getLastName()%>" width = "60px" height="60px" />
                            </a>
                        </li>
                        <%}%>
                    </ul>
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
            <div id = "home-post-panel">
                <%  ArrayList<Event> viewableEvents = new ArrayList<>();
                    boolean flag = false;
                    boolean hasPost = false;
                    
                    // add buddies events viewable to user
                    for(User buddy : user.getBuddyList()){
                        for(Event event : buddy.getEventList()){
                            flag = false;
                            for(Cluster cluster : buddy.getClusters()){
                                if(db.isViewableToCluster(event.getEventID(), cluster.getClusterID())
                                   && db.isClusterMember(user.getUserID(), cluster.getClusterID())){
                                    viewableEvents.add(event);
                                    if(event.getPostList().size() != 0)
                                        hasPost = true;
                                    flag = true;
                                }
                            }
                        }
                    }
                    
                    // get count of events from buddies
                    int buddyEventCount = viewableEvents.size();
                    
                    // add users events to viewable events
                    for(Event event : user.getEventList()){
                        viewableEvents.add(event);
                        if(event.getPostList().size() != 0)
                            hasPost = true;
                    }
                    
                    if(viewableEvents.size() != 0 && hasPost){
                        for (Event event : viewableEvents) {
                            for(Post post : event.getPostList()){
                                if (!post.isDeleted()) {
                                    System.out.println(post.getPostID());
                %>
                <div class = "post-div">
                    <div class = "post-container">
                        <a href = "User?action=view&id=<%=post.getPoster().getUserID()%>">
                            <img src ="<%=post.getPoster().getProfilePicture()%>" title = "<%=post.getPoster().getFirstName()%> <%=post.getPoster().getLastName()%>" class ="post-poster-img" height = "50px" width="50px"/>
                        </a>

                        <p class = "post-poster-name">
                            <a href = "User?action=view&id=<%=post.getPoster().getUserID()%>" class = "post-poster-name-link">
                                <%=post.getPoster().getFirstName()%> <%=post.getPoster().getLastName()%>
                            </a>
                        </p>
                        <%  if (post.getPoster().getUserID() == user.getUserID()) {
                        %>
                        <form class = "delete-post-form"action = "Post?action=delete&id=<%=post.getPostID()%>&curpage=home" method = "post">
                            <input type = "image" src = "img/clusters/DeleteButton.png" class = "delete-post-button" />
                        </form>
                        <%}%>
                        <a href = "Event?action=view&id=<%=post.getEvent().getEventID()%>" class = "post-event-name-link">
                            <p class = "post-event-name"><%=post.getEvent().getEventName()%></p>
                        </a>

                        <p class = "post-text"><%=post.getPostText()%></p>
                        <%  if (db.isLiked(post.getPostID(), user.getUserID())) {
                        %>
                        <p class = "post-like-option">
                            <a class = "like-link" href= "Like?action=unlike&pid=<%=post.getPostID()%>&uid=<%=user.getUserID()%>&curpage=home">Unlike</a>
                        </p>
                        <%
                        } else {
                        %>
                        <p class = "post-like-option">
                            <a class = "like-link" href= "Like?action=like&pid=<%=post.getPostID()%>&uid=<%=user.getUserID()%>&curpage=home">Like</a>
                        </p>
                        <%}
                            if (post.getLikeList().size() == 1) {
                        %>
                        <p class = "post-like-count">  | <%=post.getLikeList().size()%> like </p>
                        <%  } else if (post.getLikeList().size() > 1) {
                        %>
                        <p class = "post-like-count">  | <%=post.getLikeList().size()%> likes </p>
                        <%}%>
                    </div>
                    <div class = "post-comment-container">
                        <ul>
                            <%  for (Comment comment : post.getCommentList()) {%>
                            <li class = "post-comment-commenter-div">
                                <a href = "User?action=view&id=<%=comment.getCommenter().getUserID()%>">
                                    <img src = "<%=comment.getCommenter().getProfilePicture()%>" title = "<%=comment.getCommenter().getFirstName()%> <%=comment.getCommenter().getLastName()%>" class = "post-comment-commenter-img" height = "30px" width="30px">
                                </a>

                                <div class = "post-comment-commenter-info">
                                    <p class = "post-comment-commenter-name">
                                        <a href = "User?action=view&id=<%=comment.getCommenter().getUserID()%>" class = "link">
                                            <%=comment.getCommenter().getFirstName()%> <%=comment.getCommenter().getLastName()%>
                                        </a>
                                    </p>
                                    <% if (comment.getCommenter().getUserID() == user.getUserID()) {
                                    %>
                                    <form class = "delete-comment-form" action="Comment?action=delete&id=<%=comment.getCommentID()%>&curpage=home" method = "post">
                                        <input type="image" src="img/clusterpage/DeleteButtonSmall.png" class="delete-comment-button"/>
                                    </form>
                                    <%}%>
                                    <p class = "post-comment-commenter-text"><%=comment.getCommentText()%></p>
                                </div>
                            </li>
                            <%}%>
                        </ul>
                        <div class = "new-comment-div">
                            <a href = "User?action=view&id=${loggedUser.getUserID()}">
                                <img src = "${loggedUser.getProfilePicture()}" title = "${loggedUser.getFirstName()} ${loggedUser.getLastName()}" class = "post-comment-commenter-img" height = "30px" width="30px">
                            </a>
                            <form class = "new-comment-form" action = "Comment?action=add&id=<%=post.getPostID()%>" method = "post">
                                <input type = "hidden" name = "curpage" value = "home" />
                                <input type = "text" class = "new-comment-comment-field" name = "commentText" placeholder = "Write a comment"/>
                            </form>
                        </div>
                    </div>
                </div>
                <%          }
                        }
                    }
                } else {
                %>
                <div class = "post-div">
                    <div id = "post-no-post-container-div">
                        <img src ="img/clusterpage/SorryIcon.png" id ="post-no-post-img" height = "100px" width="100px"/>
                        <p id = "post-no-post-text">Sorry, no post to show.</p>
                    </div>
                </div>
                <%  }
                %>
            </div>
            <div id = "home-other-panel">
                <%  ArrayList<InviteToEvent> inviteList = db.getPendingInviteToEvent(user.getUserID());
                    ArrayList<RequestToJoin> requestList = db.getPendingRequestToJoin(user.getUserID());
                    
                    if(inviteList.size() != 0 || requestList.size() != 0){
                        int notifSize = inviteList.size() + requestList.size();
                %>
                <div class = "home-other-notif-panel" id = "request-invite-div">
                    <div class = "notif-tag-div">
                        <p class = "notif-tag">Notification</p>
                    </div>

                    <ul class = "home-notif-list">
                        <%  
                            for(InviteToEvent invite:inviteList){
                        %>
                        <li class = "home-notif-item" id = "inv_notif_<%=invite.getEvent().getEventID()%><%=invite.getInvited().getUserID()%>">
                            <a href = "User?action=view&id=<%=invite.getHost().getUserID()%>">
                                <img src = "<%=invite.getHost().getProfilePicture()%>" class = "home-notif-pic" title = "<%=invite.getHost().getFirstName()%> <%=invite.getHost().getLastName()%>" width = "40px" height="40px" />
                            </a>
                            <div class = "home-notif-info-div">
                                <p class = "home-notif-text">
                                    <a href = "User?action=view&id=<%=invite.getHost().getUserID()%>" class = "link">
                                        <%=invite.getHost().getFirstName()%> <%=invite.getHost().getLastName()%>
                                    </a>
                                     invited you to
                                     <a href = "Event?action=view&id=<%=invite.getEvent().getEventID()%>" class = "link">
                                        <%=invite.getEvent().getEventName()%>
                                    </a>
                                </p>
                                <form class = "approve-form">
                                    <input type = "submit" value ="Approve" class = "approve-button"/>
                                </form>
                                <form class = "reject-form">
                                    <input type = "submit" value = "Reject" class = "reject-button"/>
                                </form>
                            </div>
                        </li>
                        <%
                            }
                            for(RequestToJoin requestItem: requestList){ 
                        %>
                        <li class = "home-notif-item" id = "req_notif_<%=requestItem.getEvent().getEventID()%><%=requestItem.getRequestor().getUserID()%>">
                            <a href = "User?action=view&id=<%=requestItem.getRequestor().getUserID()%>">
                                <img src = "<%=requestItem.getRequestor().getProfilePicture()%>" class = "home-notif-pic" title = "<%=requestItem.getRequestor().getFirstName()%> <%=requestItem.getRequestor().getLastName()%>" width = "40px" height="40px" />
                            </a>
                            <div class = "home-notif-info-div">
                                <p class = "home-notif-text">
                                    <a href = "User?action=view&id=<%=requestItem.getRequestor().getUserID()%>" class = "link">
                                        <%=requestItem.getRequestor().getFirstName()%> <%=requestItem.getRequestor().getLastName()%>
                                    </a>
                                     requested to join
                                     <a href = "Event?action=view&id=<%=requestItem.getEvent().getEventID()%>" class = "link">
                                        <%=requestItem.getEvent().getEventName()%>
                                    </a>
                                </p>
                                <form class = "approve-form" onsubmit = "return answerRequest('Approved', <%=requestItem.getEvent().getEventID()%>, <%=requestItem.getRequestor().getUserID()%>, <%=notifSize%>)">
                                    <input type = "submit" value ="Approve" class = "approve-button"/>
                                </form>
                                <form class = "reject-form" onsubmit = "return answerRequest('Rejected', <%=requestItem.getEvent().getEventID()%>, <%=requestItem.getRequestor().getUserID()%>, <%=notifSize%>)">
                                    <input type = "submit" value = "Reject" class = "reject-button"/>
                                </form>
                            </div>
                        </li>
                        <%}%>
                    </ul>
                </div>
                <%  }
                    if(viewableEvents.size() != buddyEventCount){ 
                %>
                <div class = "home-other-event-panel">
                    <div class = "event-tag-div">
                        <p class = "event-tag">Your Events</p>
                    </div>

                    <ul class = "home-event-list">
                        <%  for(int i = buddyEventCount; i < viewableEvents.size(); i++){
                                Event event = viewableEvents.get(i);
                                SimpleDateFormat ddMMMMyyFormat = new SimpleDateFormat("MMM dd, yyyy");
                                String strDate = ddMMMMyyFormat.format(event.getStartDate());
                        %>
                        <li class = "home-event-item">
                            <a href = "Event?action=view&id=<%=event.getEventID()%>">
                                <img src = "<%=event.getEventPicture()%>" class = "home-event-pic" title = "<%=event.getEventName()%>" width = "40px" height="40px" />
                            </a>
                            <div class = "home-event-info-div">
                                
                                <p class = "home-event-name">
                                    <a href = "Event?action=view&id=<%=event.getEventID()%>" class = "link">
                                        <%=event.getEventName()%>
                                    </a>
                                </p>
                                
                                <p class = "home-event-info"><%=event.getVenue()%> - <%=strDate%></p>
                            </div>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                </div>
                <%  }
                    if(buddyEventCount != 0){
                %>  
                <div class = "home-other-event-panel">
                    <div class = "event-tag-div">
                        <p class = "event-tag">Events Shared by Buddies</p>
                    </div>

                    <ul class = "home-event-list">
                        <%  
                            for(int i = 0; i < buddyEventCount; i++){
                                Event event = viewableEvents.get(i);
                                SimpleDateFormat ddMMMMyyFormat = new SimpleDateFormat("MMM dd, yyyy");
                                String strDate = ddMMMMyyFormat.format(event.getStartDate());
                        %>
                        <li class = "home-event-item">
                            <a href = "Event?action=view&id=<%=event.getEventID()%>">
                                <img src = "<%=event.getEventPicture()%>" class = "home-event-pic" title = "<%=event.getEventName()%>" width = "40px" height="40px" />
                            </a>
                            <div class = "home-event-info-div">
                                
                                <p class = "home-event-name">
                                    <a href = "Event?action=view&id=<%=event.getEventID()%>" class = "link">
                                        <%=event.getEventName()%>
                                    </a>
                                </p>
                                
                                <p class = "home-event-info"><%=event.getVenue()%> - <%=strDate%></p>
                            </div>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                </div>
                <%  }
                %>
            </div>
        </div>
    </body>
</html>