<%@page import="Database.CircaDatabase"%>
<%@page import="Classes.Comment"%>
<%@page import="Classes.Post"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.User"%>
<%@page import="Classes.Event"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<html>
    <head>
        <title>${eventDetails.getEventName()} | Circa</title>
        <link rel ="shortcut icon" href="img/CircaLogoIcon.ico">

        <!-- HEADER SCRIPT -->
        <script type = "text/javascript" src = "js/jquery-1.11.2.min.js">
        </script>
        <script type = "text/javascript" src = "js/header.js">
        </script>
        <script type = "text/javascript" src = "js/Event.js">
        </script>
        <link rel="stylesheet" type="text/css" 	media="all" href="css/header.css" />
        <!-- END HEADER -->

        <link rel="stylesheet" type="text/css" 	media="all" href="css/event.css" />

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body style = "margin: 0px;">
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

        <!-- EVENT -->
        <%
            //get Event details of event
            Event event = (Event) request.getSession().getAttribute("eventDetails");
            DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
            //get host
            User host = event.getHost();
            User loggedUser = (User) request.getSession().getAttribute("loggedUser");
            CircaDatabase db = CircaDatabase.getInstance();
        %>
        <div id = "event-whole">
            <div id = "event-header-div" align = "center">
                <img src = "<%=host.getProfilePicture()%>" alt = "napoleon" class = "profile-pic"/>
                <img src = "img/event/event1.jpg" alt = "napoleon borntoparty" class = "event-header-img"/>
            </div>
            <h1 class = "event-title" align = "center" style = "color: #940000;"><%=event.getEventName()%></h1>

            <div id = "event-body-div" align = "center">
                <p class = "event-description"><%=event.getDescription()%></p>
                <p style = "color: #940000;"><b><%=dateFormat.format(event.getStartDate())%> - <%=dateFormat.format(event.getEndDate())%> | <%=event.getType()%> Event</b></p>
                <p>Venue: <%=event.getVenue()%></p>
                <p><%=event.getAttendingList().size()%> people are going</p>
                <p>Hosted by <a href = "User?action=view&id=<%=host.getUserID()%>"><%=host.getFirstName()%> <%=host.getLastName()%></a></p>
                <%
                    if (loggedUser.getUserID() != host.getUserID()) {
                        if (!db.isJoining(event.getEventID(), loggedUser.getUserID())) {
                            if (event.getType().equals("Closed")) {
                                if (db.isInvited(event.getEventID(), loggedUser.getUserID())) {
                %>
                <form action = "Event?action=join&id=<%=event.getEventID()%>" method = "post">
                    <%=event.getHost().getFirstName()%> invited you. What do you say?
                    <input type = "submit" class = "event-join" value = "Accept"/>
                    <input type = "submit" class = "event-join" value = "Decline"/>
                </form>
                <%
                } else if (db.isRequested(event.getEventID(), loggedUser.getUserID())) {
                %>
                You have already requested to join this event.
                <%
                } else {
                %>
                This is a closed event. You need to ask the host's permission to join!
                <form action = "Event?action=join&id=<%=event.getEventID()%>" method = "post">
                    <input type = "submit" class = "event-join" value = "Request to Join"/>
                </form>
                <%
                    }
                } else if (event.getType().equals("Public")) {
                %>
                <form action = "Event?action=join&id=<%=event.getEventID()%>" method = "post">
                    <input type = "submit" class = "event-join" value = "Join"/>
                </form>
                <%
                    }
                    //if not joining
                } else {
                %>
                <form action = "Event?action=leave&id=<%=event.getEventID()%>" method = "post">
                    <input type = "submit" class = "event-join" value = "Leave"/>
                </form>
                <%
                    }
                } else {
                %>
                <form action = "Event?action=edit" method = "post">
                    <input type = "submit" class = "event-join" value = "Edit Event Details"/>
                </form>
                <form action = "Event?action=delete&id=<%=event.getEventID()%>" onsubmit = "return deleteEvent()" method = "post">
                    <input type = "submit" class = "event-join" value = "Cancel Event"/>
                </form>
                <%
                    }
                %>
                <hr>
            </div>
            <%
                if (event.getType().equals("Closed")) {
                    if (loggedUser.getUserID() == host.getUserID()) {
            %>

            <!-- EVENT REQUESTS FOR HOST TO APPROVE-->
            <h3 align = "center" class = "event-comment-header">Requests to join your event</h3>
            <%
                ArrayList<User> requestList = event.getRequestList();

                if (requestList.isEmpty()) {
            %>
            <h3 class = "empty-text" align = "center">Your event has no requests right now.</h3>
            <hr width = "40%">
            <%
            } else {
            %>
            <div id = "request-div">
                <%
                    for (User requestor : requestList) {
                %>
                <div class = "request-container">
                    <a href = "User?action=view&id=<%=requestor.getUserID()%>">
                        <img src = "<%=requestor.getProfilePicture()%>" class = "request-prof-pic" title = "<%=requestor.getFirstName()%> <%=requestor.getLastName()%>"/>
                    </a><br>
                    <form action = "Event?action=approve&eid=<%=event.getEventID()%>&uid=<%=requestor.getUserID()%>" method = "post">
                        <input type = "submit" class = "request-button" value = "Approve"/>
                    </form>
                    <form action = "Event?action=reject&eid=<%=event.getEventID()%>&uid=<%=requestor.getUserID()%>" method = "post">
                        <input type = "submit" class = "request-button" value = "Reject"/>
                    </form>
                </div>
                <%
                    }
                %>
            </div>
            <%
                        }
                    }
                }
            %>
            <!-- POSTS and COMMENTS -->
            <div id = "input-post-div">
                <form action = "Post?action=post&curpage=event" onsubmit = "return checkPost()" method = "post">
                    <h4 class = "input-post-text">Post something about <%=event.getEventName()%>!</h4>
                    <hr width = "60%"/>
                    <textarea rows="5" cols = "40" class = "input-post-textarea" placeholder = "Say something about <%=event.getEventName()%>" name = "postText"></textarea>
                    <br><input type = "submit" class = "input-post-submit"/>
                </form>
            </div>
            <h3 align = "center" class = "event-comment-header">Posts about "<%=event.getEventName()%>"</h3>
            <%
                ArrayList<Post> postList = event.getPostList();

                if (postList.isEmpty()) {
            %>
            <h2 align = "center" class = "empty-text">There are no posts to display.</h2>
            <%
            } else if (!postList.isEmpty()) {
            %>
            <div id = "posts-div">
                <%
                    for (int i = 0; i < postList.size(); i++) {
                        if (!postList.get(i).isDeleted()) {
                            //get poster
                            User poster = postList.get(i).getPoster();
                            //get commentList
                            ArrayList<Comment> commentList = postList.get(i).getCommentList();
                %>
                <div id = "event-post-whole">
                    <div class = "event-post-div">
                        <%
                            if (poster.getUserID() == loggedUser.getUserID()) {
                        %>
                        <form action = "Post?action=delete&id=<%=postList.get(i).getPostID()%>&curpage=event" onsubmit = "return deletePost()" method = "post">
                            <input type = "submit" class = "remove-post" value = "x"/>
                        </form>
                        <%
                            }
                        %>
                        <img src = "<%=poster.getProfilePicture()%>" alt = "<%=poster.getFirstName()%> <%=poster.getLastName()%>" class = "post-pic"/>
                        <br>

                        <a href = "User?action=view&id=<%=poster.getUserID()%>">
                            <b><%=poster.getFirstName()%> <%=poster.getLastName()%></b>
                        </a>

                        <%
                            if (poster.getUserID() == loggedUser.getUserID()) {
                        %>
                        <div id = "edit-container">
                            <button class = "edit-button">Edit This Post</button>

                            <div class = "post-text-div"> 
                                <%=postList.get(i).getPostText()%>
                            </div>
                            <div class = "edit-post-div" align = "center">
                                <form action = "Post?action=edit&id=<%=postList.get(i).getPostID()%>&curpage=event" method = "post">
                                    <textarea name = "postEditText" class = "edit-post-textarea" rows = "5" cols = "40"><%=postList.get(i).getPostText()%></textarea><br>
                                    <input type = "submit" value = "Submit" class = "post-edit-submit"/>
                                </form>
                            </div>
                        </div>
                        <%
                        } else {
                        %>
                        <%=postList.get(i).getPostText()%>
                        <%
                            }
                            if (!db.isLiked(postList.get(i).getPostID(), loggedUser.getUserID())) {
                        %>                      
                        <p align = "right"><%=postList.get(i).getLikeList().size()%> likes | <a class = "comment-link">Comment</a> <a href= "Like?action=like&pid=<%=postList.get(i).getPostID()%>&uid=<%=loggedUser.getUserID()%>&curpage=event">Like</a></p>
                        <%
                        } else {
                        %>
                        <p align = "right"><%=postList.get(i).getLikeList().size()%> likes | <a class = "comment-link">Comment</a> <a href= "Like?action=unlike&pid=<%=postList.get(i).getPostID()%>&uid=<%=loggedUser.getUserID()%>">Unlike</a></p>
                        <%
                            }
                        %>
                        <div class = "input-comment-div" align = "center">
                            <form action = "Comment?action=add&id=<%=postList.get(i).getPostID()%>&curpage=event" method = "post" onsubmit = "return checkComment('<%=i%>')">
                                <input type = "hidden" name = "curpage" value = "event" />
                                <textarea name = "commentText" class = "comment-textarea"rows = "2" cols = "70" placeholder = "Comment something here!"></textarea>
                                <br>
                                <input type = "submit" class = "input-post-submit" value = "Comment!"/>
                            </form>
                        </div>
                    </div>
                    <div class = "post-show-comment" align = "center">Show Comments</div>
                    <%
                        if (commentList.isEmpty()) {
                    %>
                    <div class ="post-comments-whole">
                        <h3 align = "center" class = "empty-text">This post has no comments yet.</h3>
                    </div>
                    <%
                    } else {
                    %>
                    <div class = "post-comments-whole">
                        <%
                            for (int j = 0; j < commentList.size(); j++) {
                                //get commenter
                                User commenter = commentList.get(j).getCommenter();
                        %>
                        <div class = "post-comment">
                            <%
                                if (commenter.getUserID() == loggedUser.getUserID()) {
                            %>
                            <form action = "Comment?action=delete&id=<%=commentList.get(j).getCommentID()%>&curpage=event" onsubmit = "return deleteComment()" method = "post">
                                <input type = "submit" class = "remove-post" value = "x"/>
                            </form>
                            <%
                                }
                            %>
                            <img src = "<%=commenter.getProfilePicture()%>" alt = "<%=commenter.getFirstName()%> <%=commenter.getLastName()%>" class = "comment-pic"/>
                            <p class = "event-post-text"><a href = "User?action=view&id=<%=commenter.getUserID()%>"><b>
                                        <br><%=commenter.getFirstName()%> <%=commenter.getLastName()%></b></a> <%=commentList.get(j).getCommentText()%></p>
                        </div>
                        <%
                            }
                        %>
                    </div>
                    <%
                        }
                    %>
                </div>
                <%
                        }
                    }
                %>
            </div>
            <%
                    }
            %>
        </div>
    </body>
</html>
