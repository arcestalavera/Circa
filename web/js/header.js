
var d = new Date();

$(document).ready(function(){
    var isOpened = false;
    $(".header-logo").click(function(){
        if(!isOpened)
        {	
            $("#header-temp").animate({top: '+=35px', height: '-=25px'});
            isOpened = true;
        }
	else
	{
            $("#header-temp").animate({top: '-=35px', height: '+=25px'});
            isOpened = false;
	}
        $("#header").slideToggle("medium");
    });
});