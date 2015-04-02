$(document).ready(function() {
    var isOpened = false;
    $(".invite-title").click(function() {
        if (!isOpened)
        {
            $("#create-event-whole").fadeOut("medium");
            $("#invite-user-whole").animate({bottom: '+=40px'});
            isOpened = true;
        }
        else
        {
            $("#create-event-whole").fadeIn("medium");
            $("#invite-user-whole").animate({bottom: '-=40px'});
            isOpened = false;
        }
        
        $(".invite-body").slideToggle("medium");
    });
});