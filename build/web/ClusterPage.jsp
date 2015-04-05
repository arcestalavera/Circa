<%-- 
    Document   : ClusterPage
    Created on : Apr 5, 2015, 1:53:13 PM
    Author     : Arren Antioquia
--%>

<%@page import="Classes.User"%>
<%@page import="Classes.Cluster"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title> Cluster | Circa</title>
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
                <div id = "cluster-members-list">
                    <% Cluster cluster = (Cluster)request.getSession().getAttribute("clusterToProcess");
                        for(User clusterMember : cluster.getMemberList()){
                    %>
                    <div class = "cluster-member">
                        <a href = "User?id=<%=clusterMember.getUserID()%>">
                            <img src="<%=clusterMember.getProfilePicture()%>" title = "<%=clusterMember.getFirstName()%> <%=clusterMember.getLastName()%>"width = "60px" height="60px"/>
                        </a>
                    </div>
                    <%}%>
                </div>
            </div>
            
            <div id = "cluster-post-panel">
            </div>
            
            <div id = "cluster-add-member-panel">
            </div>
        </div>
    </body>
</html>