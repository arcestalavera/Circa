<%@page import="Classes.Cluster"%>
<%@page import="Database.CircaDatabase"%>
<%@page import="Classes.User"%>
<html>
    <head>
        <title>Your Clusters | Circa</title>
        <link rel ="shortcut icon" href="img/CircaLogoIcon.ico">

        <script type = "text/javascript" src = "js/jquery-1.11.2.min.js"></script>
        <script type = "text/javascript" src = "js/header.js"></script>
        <script type = "text/javascript" src = "js/clusters.js"></script>
        <link rel="stylesheet" type="text/css" 	media="all" href="css/header.css" />
        <link rel="stylesheet" type="text/css" 	media="all" href="css/clusters.css" />
    </head>
    <body bgcolor = "f4f4f4">
        <%  User user = (User) request.getSession().getAttribute("loggedUser");
            CircaDatabase db = CircaDatabase.getInstance();
            user.setEventList(db.getEvents(user.getUserID()));
            user.setBuddyList(db.getUserBuddies(user.getUserID()));
            user.setClusters(db.getUserClusters(user.getUserID()));
                
            for(User buddy : user.getBuddyList()){
                buddy.setClusters(db.getUserClusters(buddy.getUserID()));
                buddy.setEventList(db.getEvents(buddy.getUserID()));
            }
        %>
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

        
        <div id = "user-cluster">
            <div id = "cluster-heading">
                <div id = "cluster-tag">Clusters</div>

                <form id = "new-cluster-form" action = "Cluster" method = "POST">
                    <input type = "text" name = "new-cluster-name" placeholder = "Cluster Name" />
                    <input type = "hidden" name = "form-type" value = "add-cluster" />
                    <input type = "submit" id = "add-cluster-button" value ="Add Cluster" />
                </form>
            </div>
            <ul id = "user-cluster-list">
                <li class = "cluster-item">
                    <div class = "cluster-item-elements">
                        <p id = "buddies-tag">Buddies</p>
                    </div>
                    <div class = "cluster-members-list">
                        <%  for(User buddy : user.getBuddyList()){%>
                            <div class = "cluster-members">
                                <a href = "User?action=view&id=<%=buddy.getUserID()%>">
                                    <img src="<%=buddy.getProfilePicture()%>" title = "<%=buddy.getFirstName()%> <%=buddy.getLastName()%>"width = "50px" height="50px"/>
                                </a>
                            </div>
                        <%}%>
                    </div>
                </li>
                
                <% user.setClusters(db.getUserClusters(user.getUserID()));
                    
                for(Cluster cluster : user.getClusters()){
                %>
                <li class = "cluster-item">
                    <div class = "cluster-item-elements">
                        <form action = "ViewCluster" method="GET" class = "view-members-form">
                            <input type = "hidden" name = "clusterID" value = "<%=cluster.getClusterID()%>"/>
                            <input type = "submit" class = "cluster-name" value = "<%=cluster.getName()%>">
                        </form>
                        <form action = "Cluster" method="POST" class = "delete-cluster-form">
                            <input type = "hidden" name = "clusterID" value = "<%=cluster.getClusterID()%>"/>
                            <input type = "hidden" name = "form-type" value = "delete-cluster" />
                            <input type = "image" src = "img/clusters/DeleteButton.png" class = "delete-cluster-button"/>
                        </form>
                    </div>
                    <div class = "cluster-members-list">
                        <%for(User clusterMember : cluster.getMemberList()){%>
                            <div class = "cluster-members">
                                <a href = "User?action=view&id=<%=clusterMember.getUserID()%>">
                                    <img src="<%=clusterMember.getProfilePicture()%>" title = "<%=clusterMember.getFirstName()%> <%=clusterMember.getLastName()%>"width = "50px" height="50px"/>
                                </a>
                            </div>
                        <%}%>
                    </div>
                </li>
                <%}%>
            </ul>
        </div>
    </body>
</html>