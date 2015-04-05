<%-- 
    Document   : ClusterPage
    Created on : Apr 5, 2015, 1:53:13 PM
    Author     : Arren Antioquia
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Database.CircaDatabase"%>
<%@page import="Classes.Event"%>
<%@page import="Classes.User"%>
<%@page import="Classes.Cluster"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>${clusterToProcess.getName()}  Cluster | Circa</title>
        <link rel ="shortcut icon" href="img/CircaLogoIcon.ico">

        <script type = "text/javascript" src = "js/jquery-1.11.2.min.js"></script>
        <script type = "text/javascript" src = "js/header.js"></script>
        <!--script type = "text/javascript" src = "js/clusters.js"></script-->
        <link rel="stylesheet" type="text/css" 	media="all" href="css/header.css" />
        <link rel="stylesheet" type="text/css" 	media="all" href="css/ClusterPage.css" />
    </head>
    <body bgcolor = "f4f4f4">
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
        <div id = "cluster-main-panel">
            <div id = "cluster-info-panel">
                <div id = "cluster-name-div"><p id = "cluster-name">${clusterToProcess.getName()}</p></div>
                <ul id = "cluster-members-list">
                    <% Cluster cluster = (Cluster)request.getSession().getAttribute("clusterToProcess");
                        for(User clusterMember : cluster.getMemberList()){
                    %>
                    <li class = "cluster-member">
                        <a href = "User?id=<%=clusterMember.getUserID()%>">
                            <img src="<%=clusterMember.getProfilePicture()%>" title = "<%=clusterMember.getFirstName()%> <%=clusterMember.getLastName()%>" width = "60px" height="60px" />
                        </a>
                    </li>
                    <%}%>
                </ul>
            </div>
            
            <div id = "cluster-post-panel">
            </div>
            
            <div id = "cluster-other-panel">
                <div id = "event-tag-div">
                    <p id = "event-tag">Events</p>
                </div>
                
                <ul id = "cluster-event-list">
                    <%  User user = (User)request.getSession().getAttribute("loggedUser");
                        CircaDatabase db = CircaDatabase.getInstance();
                        for(Event event : user.getEventList()){
                            if(db.isViewableToCluster(event.getEventID(), cluster.getClusterID())){
                                SimpleDateFormat ddMMMMyyFormat = new SimpleDateFormat("MMM dd, yyyy");
                                String strDate = ddMMMMyyFormat.format(event.getStartDate());
                    %>
                    <li class = "cluster-event-item">
                        <img src = "<%=event.getEventPicture()%>" class = "cluster-event-pic" title = "<%=event.getEventName()%>" width = "40px" height="40px" />
                        <div class = "cluster-event-info-div">
                            <p class = "cluster-event-name"><%=event.getEventName()%></p>
                            <p class = "cluster-event-info"><%=event.getVenue()%> - <%=strDate%></p>
                        </div>
                    </li>
                    <%
                            }
                        }
                    %>
                </ul>
            </div>
        </div>
    </body>
</html>