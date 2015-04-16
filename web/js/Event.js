//ajax
//post
function addPost() {
    if (checkPost())
    {
        $.ajax({
            type: "POST",
            url: "Post?action=post&curpage=event",
            data: $("#add-post").serialize(),
            success: function(html) {
                $("#post-list").prepend(html);
                $(".input-post-textarea").val("");
            }
        });
    }
    return false;
}

function deletePost(postID) {
    if (confirmDelete())
    {
        $.ajax({
            type: "POST",
            url: "Post?action=delete&id=" + postID + "&curpage=event",
            success: function() {
                $("#post_" + postID).remove().fadeOut("slow");
            }
        });
    }
    return false;
}

function addComment(postID, size) {
    console.log("i've been called by " + postID);
    var commentInput = $("#post_" + postID).find("#add-comment");
    if (checkComment(postID))
    {
        $.ajax({
            type: "POST",
            url: "Comment?action=add&id=" + postID,
            data: commentInput.serialize(),
            success: function(html) {
                console.log("post = " + postID);
                $("#post_" + postID).find("#comment-list").append(html);
                $(".comment-textarea").val("");
                if (size === 0)
                    $("#post_" + postID).find("#comment-no").remove();
            }
        });
    }
    return false;
}

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

function checkComment(postID) {
    var input = $("#post_" + postID).find(".input-comment-div").find(".comment-textarea").val();
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

function confirmDelete() {
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
    $(document).on("click", ".comment-link", function() {
        $(this).parent().next(".input-comment-div").slideToggle("medium");
    });
    $(document).on("click", ".post-show-comment", function() {

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

    $(document).on("click", ".edit-button", function() {
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