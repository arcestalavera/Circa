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
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
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
                <p>1,392 people are going</p>
                <p>Hosted by <a href = "User?action=view&id=<%=host.getUserID()%>"><%=host.getFirstName()%> <%=host.getLastName()%></a></p>
                <%
                    if (loggedUser.getUserID() != host.getUserID()) {
                %>
                <button class = "event-join">Join</button>
                <%
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

            <!-- POSTS and COMMENTS -->
            <div id = "input-post-div">
                <form action = "Post" onsubmit = "return checkPost()" method = "post">
                    <h4 class = "input-post-text">Post something about <%=event.getEventName()%>!</h4>
                    <hr width = "60%"/>
                    <textarea rows="5" cols = "40" class = "input-post-textarea" placeholder = "Say something about <%=event.getEventName()%>" name = "postText"></textarea>
                    <br><input type = "submit" class = "input-post-submit"/>
                </form>
            </div>
            <div id = "posts-div">
                <h3 align = "center" class = "event-comment-header">Posts about "<%=event.getEventName()%>"</h3>
                <%
                    //get posts of event
                    ArrayList<Post> postList = event.getPostList();

                    if (postList != null) {
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
                        <form action = "DeletePost?id=<%=postList.get(i).getPostID()%>" onsubmit = "return deletePost()" method = "post">
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
                                <form action = "EditPost?id=<%=postList.get(i).getPostID()%>" method = "post">
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
                        <p align = "right"><%=postList.get(i).getLikeList().size()%> likes | <a class = "comment-link">Comment</a> <a href= "Like?post=<%=postList.get(i).getPostID()%>&user=<%=loggedUser.getUserID()%>">Like</a></p>
                        <%
                        } else {
                        %>
                        <p align = "right"><%=postList.get(i).getLikeList().size()%> likes | <a class = "comment-link">Comment</a> <a href= "Unlike?post=<%=postList.get(i).getPostID()%>&user=<%=loggedUser.getUserID()%>">Unlike</a></p>
                        <%
                            }
                        %>
                        <div class = "input-comment-div" align = "center">
                            <form action = "Comment?id=<%=postList.get(i).getPostID()%>" method = "post" onsubmit = "return checkComment('<%=i%>')">
                                <textarea name = "commentText" class = "comment-textarea"rows = "2" cols = "70" placeholder = "Comment something here!"></textarea>
                                <br>
                                <input type = "submit" class = "input-post-submit" value = "Comment!"/>
                            </form>
                        </div>
                    </div>
                    <div class = "post-show-comment" align = "center">Show Comments</div>

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
                            <form action = "DeleteComment?id=<%=commentList.get(j).getCommentID()%>" onsubmit = "return deleteComment()" method = "post">
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
                </div>
                <%
                            }
                        }
                    }
                %>
            </div>
        </div>
    </body>
</html>
