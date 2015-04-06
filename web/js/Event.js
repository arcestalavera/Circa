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

function checkComment(i) {
    var input = $(".comment-textarea").eq(i).val();
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

function deletePost() {
    if (confirm("Do you really want to delete this post?"))
        return true;
    else
        return false;
}

function deleteComment() {
    if (confirm("Do you really want to delete this comment?"))
        return true;
    else
        return false;
}

function deleteEvent() {
    if (confirm("Do you really want to cancel this event?"))
        return true;
    else
        return false;
}

$(document).ready(function() {
    var isEditing = false;
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

    $('.edit-button').click(function() {
        if ($(this).html() === "Edit This Post")
        {
            $(this).siblings(".post-text-div").toggle("fast");
            $(this).siblings(".edit-post-div").toggle("slow");
            $(this).html("Cancel Edit");
        }
        else
        {
            $(this).siblings(".edit-post-div").fadeOut("fast");
            $(this).siblings(".post-text-div").toggle("fast");
            $(this).html("Edit This Post");
        }
    });
});