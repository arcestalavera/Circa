<html>
    <head>
        <title>Your Clusters | Circa</title>
        <link rel="shortcut icon" href="img/clusters/CircaLogoIcon.ico" />

        <script type = "text/javascript" src = "js/jquery-1.11.2.min.js"></script>
        <script type = "text/javascript" src = "js/header.js"></script>
        <script type = "text/javascript" src = "js/clusters.js"></script>
        <link rel="stylesheet" type="text/css" 	media="all" href="css/header.css" />
        <link rel="stylesheet" type="text/css" 	media="all" href="css/clusters.css" />
    </head>
    <body>
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
                    <a href = "UserPage.jsp" class = "text">${loggedUser.getFirstName()}</a>
                    <a href = "Clusters.jsp" class = "text">Clusters</a>
                    <a href = "Home.jsp" class = "text">Home</a>
                    <a href = "Logout" class = "text">Logout</a>
                </div>
            </div>
            <img src = "img\clusters\CircaLogo.png" class = "header-logo" />
        </div>    
        <!-- END HEADER -->

        <div id = "user-cluster">
            <ul id = "user-cluster-list">
                <li class = "cluster-item">
                    <div class = "cluster-item-elements">
                        Team Nerds United
                        <!--button class = "cluster-view-members-button">View Members</button-->
                    </div>
                    <div class = "cluster-members-list">
                        <div class = "cluster-members">
                            <img src="img\clusters\party1.jpg" />
                        </div>
                        <div class = "cluster-members">
                            <img src="img\clusters\party2.jpg" />
                        </div>
                    </div>
                </li>
                <li class = "cluster-item">
                    <div class = "cluster-item-elements">
                        Team Fabcon
                        <!--button class = "cluster-view-members-button">View Members</button-->
                    </div>
                    <div class = "cluster-members-list">
                        <div class = "cluster-members">
                            <img src="img\clusters\party1.jpg" />
                        </div>
                        <div class = "cluster-members">
                            <img src="img\clusters\party3.jpg" />
                        </div>
                    </div>
                </li>
                <li class = "cluster-item">
                    <div class = "cluster-item-elements">
                        Born2Party
                        <!--button class = "cluster-view-members-button">View Members</button-->
                    </div>
                    <div class = "cluster-members-list">
                        <div class = "cluster-members">
                            <img src="img\clusters\party3.jpg" />
                        </div>
                        <div class = "cluster-members">
                            <img src="img\clusters\party1.jpg" />
                        </div>
                        <div class = "cluster-members">
                            <img src="img\clusters\party2.jpg" />
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </body>
</html>