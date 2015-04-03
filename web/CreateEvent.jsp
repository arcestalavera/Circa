<%-- 
    Document   : CreateEvent
    Created on : 04 2, 15, 12:06:19 PM
    Author     : Arces
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Event | Circa</title>

        <link rel ="shortcut icon" href="img/CircaLogoIcon.ico">

        <!-- HEADER SCRIPT -->
        <script type = "text/javascript" src = "js/jquery-1.11.2.min.js">
        </script>
        <script type = "text/javascript" src = "js/header.js">
        </script>
        <link rel="stylesheet" type="text/css" 	media="all" href="css/header.css" />
        <!-- END HEADER -->
        <script type = "text/javascript" src = "js/CreateEvent.js">
        </script>
        <link rel="stylesheet" type="text/css" 	media="all" href="css/CreateEvent.css" />
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

        <div id = "create-event-title">
            <h1>Create an Event</h1>
        </div>
        <form action = "CreateEvent" method = "post">
            <div id = "create-event-whole">
                <h2 class = "event-title">Enter event details here!</h2>
                <div class = "event-body" align = "center">
                    <h2 class = "event-body-title">Name of Event</h2> <input required type = "text" name = "eventName" class = "event-body-input" placeholder = "Name"/><br>
                    <br><h2 class = "event-body-title">Venue</h2> <input required type = "text" name = "eventVenue" class = "event-body-input" placeholder = "Venue"/><br>
                    <br><h3 class = "event-body-title">Description</h3><textarea rows="5" cols = "50" name = "eventDescription" placeholder = "Tell us something about your event"></textarea>
                    <br><h2 class = "event-body-title">Start</h2><hr width = "30%">
                    <text class = "event-body-title"><b>Date:</b></text> <input required type = "date" name = "eventStartDate"/> 
                    <text class = "event-body-title"><b>Time:</b></text> <input required type = "time" name = "eventStartTime"/>
                    <br><br><h2 class = "event-body-title">End</h2><hr width = "30%">
                    <text class = "event-body-title"><b>Date:</b></text> <input required type = "date" name = "eventEndDate"/> 
                    <text class = "event-body-title"><b>Time:</b></text> <input required type = "time" name = "eventEndTime"/>
                    <br><br><h3 class = "event-body-title">Type</h3>
                    <select class = "event-body-input"  name = "eventType" required>
                        <option value = "Public">Public</option>
                        <option value = "Closed">Closed</option>
                        <option value = "Private">Private</option>
                    </select>
                    <br><br>
                    <input type = "submit" value = "Publish Event!" class = "event-submit"/>
                </div>
            </div>

            <div id = "invite-user-whole">
                <h2 class = "invite-title">Invite your buddies!</h2>
                <div class = "invite-body">
                </div>
            </div>
        </form>
    </body>
</html>
