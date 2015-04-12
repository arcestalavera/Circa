$(document).ready(function() {
    var isOpened = false;
    $(".invite-title").click(function() {
        if (!isOpened)
        {
            $("#create-event-whole").fadeOut("medium");
            $("#invite-user-whole").animate({bottom: '+=15px'});
            $(this).css('opacity','1');
            isOpened = true;
        }
        else
        {
            $("#create-event-whole").fadeIn("medium");
            $("#invite-user-whole").animate({bottom: '-=15px'});
            $(this).css('opacity','.6');
            isOpened = false;
        }
        
        $(".invite-body").slideToggle("medium");
    });
});