<%@page import="Classes.Post"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Classes.User"%>
<%@page import="Classes.Event"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<html>
    <head>
        <title>${eventDetails.getEventName()}</title>
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
                    <a href = "User?id=${loggedUser.getUserID()}" class = "text">${loggedUser.getFirstName()}</a>
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
                <p>Hosted by <a href = "User?id=<%=host.getUserID()%>"><%=host.getFirstName()%> <%=host.getLastName()%></a></p>
                <button class = "event-join">Join</button>
                <hr>
            </div>

            <!-- POSTS and COMMENTS -->
            <div id = "input-post-div">
                <form action = "PostToEvent" onsubmit = "return checkPost()" method = "post">
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
                            //get poster
                            User poster = postList.get(i).getPoster();
                %>
                <div id = "event-post-whole">
                    <div class = "event-post-div">
                        <img src = "<%=poster.getProfilePicture()%>" alt = "<%=poster.getFirstName()%> <%=poster.getLastName()%>" class = "post-pic"/>
                        <br><p class = "event-post-text"><a href = "User?id=<%=poster.getUserID()%>"><b><%=poster.getFirstName()%> <%=poster.getLastName()%></b></a> <%=postList.get(i).getPostText()%></p>
                        <p align = "right">44 likes | <a class = "comment-link">Comment</a> <a>Like</a></p>
                        <div class = "input-comment-div" align = "center">
                            <form action = "CommentOnPost" onsubmit = "return checkComment()">
                                <textarea class = "comment-textarea"rows = "2" cols = "70" placeholder = "Comment something here!"></textarea>
                                <br>
                                <input type = "submit" class = "input-post-submit" value = "Comment!"/>
                            </form>
                        </div>
                    </div>
                    <div class = "post-show-comment" align = "center">Show Comments</div>
                    <div class = "post-comments-whole">
                        <div class = "post-comment">
                            <img src = "img/event/party4.jpg" alt = "anchor tis" class = "comment-pic"/>
                            <p class = "event-post-text"><a href = "user-page.html"><b><br>Anchor Tis</b></a> I know right!</p>
                        </div>
                        <div class = "post-comment">
                            <img src = "img/event/party4.jpg" alt = "anchor tis" class = "comment-pic"/>
                            <p class = "event-post-text"><a href = "user-page.html"><b><br>Anchor Tis</b></a> I know right!</p>
                        </div>
                    </div>
                </div>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </body>
</html>
