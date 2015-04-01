<html>
    <head>
        <title> Circa - Home </title>
        <link rel ="shortcut icon" href="img/CircaLogoIcon.ico">
        
        <meta charset="UTF-8">
        <script type = "text/javascript" src = "js/jquery-1.11.2.min.js">
        </script>
        <script type = "text/javascript" src = "js/header.js">
        </script>
        <link rel="stylesheet" type="text/css" 	media="all" href="css/header.css" />
        <link rel="stylesheet" type="text/css" 	media="all" href="css/Home.css" />

    </head>


    <body bgcolor="#f4f4f4">

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
                    <a href = "User" class = "text">${loggedUser.getFirstName()}</a>
                    <a href = "Cluster" class = "text">Clusters</a>
                    <a href = "Home" class = "text">Home</a>
                    <a href = "Logout" class = "text">Logout</a>
                </div>
            </div>
            <img src = "img\clusters\CircaLogo.png" class = "header-logo" />
        </div>    
        <!-- END HEADER -->
        
        <div class = "postBar">
            <div class="postBarTop"></div>
            <input type="text" class="postBarText" placeholder="Got a new event?"/>
            <div class="postBarBottom"><button type="button" class="postButton">Circulate</button></div>
        </div>

        <div class = "post">
            <IMG class = "postDP" src="img/logo.png"/>
            <div class="postOptions">
                <IMG class = "options" src="img/home/uncircled.png"/>
                <IMG class = "options" src="img/home/recircle.png"/>
                <IMG class = "options" src="img/home/ellipsis.png"/></div>
            <div class="textLinks"> Circa </div>
            <div class="postText"><p> Welcome to Circa! Your Social Planning Buddy WUBBY!</p></div>
            <div class = "postPhoto"><IMG class ="photo" src="img/home/weeeeeeeeeee.jpg"/></div>
            <div class ="postComment"><input type="text" placeholder="Write a comment..." class="textInput"/><IMG class ="commentPhoto" src="img/logo.png"/></div>
        </div>
    </body>
</html>