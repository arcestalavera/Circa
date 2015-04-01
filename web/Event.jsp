<html>
    <head>
        <title>Circa</title>
        <link rel ="shortcut icon" href="img/CircaLogoIcon.ico">
        
        <!-- HEADER SCRIPT -->
        <script type = "text/javascript" src = "js/jquery-1.11.2.min.js">
        </script>
        <script type = "text/javascript" src = "js/header.js">
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
                    <a href = "User" class = "text">${loggedUser.getFirstName()}</a>
                    <a href = "Cluster" class = "text">Clusters</a>
                    <a href = "Home" class = "text">Home</a>
                    <a href = "Logout" class = "text">Logout</a>
                </div>
            </div>
            <img src = "img\clusters\CircaLogo.png" class = "header-logo" />
        </div>    
        <!-- END HEADER -->

        <!-- EVENT -->
        <div id = "event-whole">
            <div id = "event-header-div" align = "center">
                <img src = "img/event/party1.jpg" alt = "napoleon" class = "profile-pic"/>
                <img src = "img/event/event1.jpg" alt = "napoleon borntoparty" class = "event-header-img"/>

            </div>
            <h1 class = "event-title" align = "center" style = "color: #940000;">Happy Thursday!</h1>
            <div id = "event-body-div" align = "center">
                <p class = "event-description">This is it! It's Thursday once again and I just want to have fun and forget 
                    about leading my troops for a while. It's so stressful, 'ya know! Anyways, 
                    see you there! :-)</p>
                <p style = "color: #940000;"><b>March 5, 2015 | 7:00PM | Public Event</b></p>
                <p>Venue: TBA</p>
                <p>1,392 people are going</p>
                <p>Hosted by <a href = "user-page.html">Napoleon Borntoparty</a></p>
                <button class = "event-join">Join</button>
                <hr>
            </div>
            <h3 align = "center" class = "event-comment-header">Posts about "Happy Thursday!"</h3>
            <div class = "event-comment-div">
                <img src = "img/event/party2.jpg" alt = "Sisa mistrit" class = "commenter-pic"/>
                <br><p class = "event-comment-text"><a href = "user-page.html"><b>Sisa Mistrit</b></a> I love it! I'm also very stressed right now. I Need to take some time off. <a href = "event.html">#borntoparty</a></p>
                <p align = "right">44 likes | <a>Comment</a> <a>Like</a></p>
            </div>
            <div class = "event-sub-comment">
                <img src = "img/event/party4.jpg" alt = "anchor tis" class = "sub-commenter-pic"/>
                <p class = "event-comment-text"><a href = "user-page.html"><b><br>Anchor Tis</b></a> I know right!</p>
            </div>
            <div class = "event-comment-div">
                <img src = "img/event/party3.jpg" alt = "harry party" class = "commenter-pic"/>
                <br><p class = "event-comment-text"><a href = "user-page.html"><b>Harry Party</b></a> I'm gonna do a magic show later at this party. lol <a href = "event.html">#borntoparty</a></p>
                <p align = "right">37 likes | <a>Comment</a> <a>Like</a></p>
            </div>
            <div class = "event-comment-div">
                <img src = "img/event/party1.jpg" alt = "borntoparty" class = "commenter-pic"/>
                <br><p class = "event-comment-text"><a href = "user-page.html"><b>Napoleon Borntoparty</b></a> Thank you guys for joining! I'm sure that you'll have fun! See you all ;) <a href = "event.html">#borntoparty</a></p>
                <p align = "right">142 likes | <a>Comment</a> <a>Like</a></p>
            </div>
            <div class = "event-comment-div">
                <img src = "img/event/party4.jpg" alt = "anchor tis" class = "commenter-pic"/>
                <br><p class = "event-comment-text"><a href = "user-page.html"><b>Anchor Tis</b></a> Magdadala ako ng century tuna!</p>
                <p align = "right">23 likes | <a>Comment</a> <a>Like</a></p>
            </div>
            <div class = "event-sub-comment">
                <img src = "img/event/party2.jpg" alt = "anchor tis" class = "sub-commenter-pic"/>
                <p class = "event-comment-text"><a href = "user-page.html"><b><br>Sisa Mistrit</b></a> Yummy!</p>
            </div>
            <div class = "event-sub-comment">
                <img src = "img/event/party3.jpg" alt = "anchor tis" class = "sub-commenter-pic"/>
                <p class = "event-comment-text"><a href = "user-page.html"><b><br>Harry Party</b></a> Cool!</p>
            </div>
        </div>
    </body>
</html>
