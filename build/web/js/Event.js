function checkPost() {
    var input = $(".input-post-textarea").val();
    console.log("input = " + input);
    if (input === "")
    {
        alert("You're posting nothing! :(");
        return false;
    }
    else
    {
        return true;
    }
}

function checkComment() {
    var input = $(".comment-textarea").val();
    console.log("input = " + input);
    if (input === "")
    {
        alert("You're commenting nothing! :(");
        return false;
    }
    else
    {
        return true;
    }
}

$(document).ready(function() {
    $('.comment-link').click(function() {
        $(this).parent().next(".input-comment-div").slideToggle("medium");
    });
    $('.post-show-comment').click(function() {
        
        $(this).next(".post-comments-whole").slideToggle("medium");
        if ($(this).html() === "Show Comments")
        {
            $(this).html("Hide Comments");
            $(this).css("margin-bottom", "0");
        }
        else if ($(this).html() === "Hide Comments")
        {
            $(this).html("Show Comments");
            $(this).css("margin-bottom", "10px");
        }
    });
});