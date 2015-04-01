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

        <link rel="stylesheet" type="text/css" 	media="all" href="css/result.css" />
        <script>
            $(document).ready(function() {
                $(".result-img").hover(function() {
                    $(this).next(".result-img-caption").fadeIn(500);
                });
                $(".result-img").mouseout(function() {
                    $(this).next(".result-img-caption").fadeOut(500);
                });
            });
        </script>
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

        <!-- RESULT -->
        <div id = "result-whole">
            <div id = "result-header-div">
                <h1 align = "center"> Results found for 'Party'</h1>
            </div>
            <div id = "result-button-div" align = "center">
                <button class = "result-button" name = "event-button">Events</button> |
                <button class = "result-button" name = "people-button">People</button>
            </div>
            <h1 align = "center" style = "font-family: century gothic; text-decoration: underline;">Events</h1>
            <div class ="result-body-div" align = "center">
                <figure>
                    <img src = "img/event/event1.jpg" class = "result-img" alt = "happy thursday"/>
                    <figcaption class = "result-img-caption">Let's go party together! Let's all have fun and enjoy the wonderful Thursday :-)</figcaption>
                </figure>
                <h2>Happy Thursday!</h2>
                <p>by Napoleon Borntoparty</p>
                <p>March 5, 2015 | 7:00PM | Public Event</p>
                <p>Venue: TBA</p>
                <button class = "choice-button">Join</button>
                <button class = "choice-button"><a href = "Event.jsp">View Event</a></button>
            </div>
            <div class ="result-body-div" align = "center">
                <figure>
                    <img src = "img/event/event2.jpg" class = "result-img" alt = "fire works"/>
                    <figcaption class = "result-img-caption">Nothing beats a good celebration with lots of fireworks! Come on and join in the fun!</figcaption>
                </figure>
                <h2>Fireworks Party!</h2>
                <p>by Harry Party</p>
                <p>March 7, 2015 | 8:00PM | Public Event</p>
                <p>Enchanted Kingdom</p>
                <button class = "choice-button">Join</button>
                <button class = "choice-button"><a href = "event.html">View Event</a></button>
            </div>
            <div class ="result-body-div" align = "center">
                <figure>
                    <img src = "img/event/event3.jpg" class = "result-img" alt = "happy thursday"/>
                    <figcaption class = "result-img-caption">Hang out Hang out lang guys! Minsan lang 'to!</figcaption>
                </figure>
                <h2>Hang Out with SUPERFRIENDS</h2>
                <p>You are invited by Sisa Mistrit</p>
                <p>March 8, 2015 | 2:00PM | Closed Event</p>
                <p>SM Aura</p>
                <button class = "choice-button">Join</button>
                <button class = "choice-button">Decline</button>
                <button class = "choice-button"><a href = "event.html">View Event</a></button>
            </div>
        </div>
    </body>
</html>