//ajax
//post
function addPost(size) {
    if (checkPost())
    {
        $.ajax({
            type: "POST",
            url: "Post?action=post&curpage=event",
            data: $("#add-post").serialize(),
            success: function(html) {
                $("#post-list").prepend(html);
                $(".input-post-textarea").val("");
                if (size === 0)
                    $("#post-list").find("#no-post").hide();
            }
        });
    }
    return false;
}

function deletePost(postID, size) {
    if (confirmDelete())
    {
        $.ajax({
            type: "POST",
            url: "Post?action=delete&id=" + postID + "&curpage=event",
            success: function() {
                $("#post_" + postID).remove().fadeOut("slow");
                size++;
                if (size !== 0)
                    $("#post-list").find("#no-post").show();
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
                $("#post_" + postID).find("#comment-list").append(html);
                $(".comment-textarea").val("");
                if (size === 0)
                    $("#post_" + postID).find("#comment-no").remove();
            }
        });
    }
    return false;
}

function deleteComment(commentID) {
    if (confirmDeleteComment())
    {
        $.ajax({
            type: "POST",
            url: "Comment?action=delete&id=" + commentID + "&curpage=event",
            success: function() {
                $("#comment_" + commentID).remove().fadeOut("slow");
            }
        });
    }
    return false;
}

function likePost(action, postID, userID, likeCount) {
    $.ajax({
        type: "GET",
        url: "Like?action=" + action + "&pid=" + postID + "&uid=" + userID + "&curpage=event",
        success: function() {
            if (action === "like")
            {
                likeCount++;
                $("#post_" + postID).find("#comment-par").html(likeCount + " likes | <a class = 'comment-link'>Comment</a> <a onclick = 'return likePost(\"unlike\", " + postID + ", " + userID + ", " + likeCount + "); return false;'>Unlike</a>");
            }
            else if (action === "unlike")
            {
                likeCount--;
                $("#post_" + postID).find("#comment-par").html(likeCount + " likes | <a class = 'comment-link'>Comment</a> <a onclick = 'return likePost(\"like\", " + postID + ", " + userID + ", " + likeCount + "); return false;'>Like</a>");
            }
        }
    });


    return false;
}


function editPost(postID) {
    $.ajax({
        type: "POST",
        url: "Post?action=edit&id=" + postID + "&curpage=event",
        data: $("#post_" + postID).find("#edit-form").serialize(),
        success: function(newText) {
            $("#post_" + postID).find(".post-text-div").html(newText);
            $("#post_" + postID).find(".post-text-div").toggle("fast");
            $("#post_" + postID).find(".edit-post-div").toggle("slow");
            $("#post_" + postID).find(".edit-button").html("Edit This Post");
        }
    });

    return false;
}

function answerRequest(eventID, userID, answer, size, count)
{
    $.ajax({
        type: "POST",
        url: "Event?action=answer&answer=" + answer + "&eid=" + eventID + "&uid=" + userID,
        success: function() {
            $("#request_" + userID).remove();
            size--;
            if (size === 0)
            {
                console.log("no more requests");
                $("#request-list").append("<li id = 'no-request'>\n" +
                        "<h3 class = 'empty-text' align = 'center'>Your event has no requests right now.</h3>\n" +
                        "</li>");
            }
            if (answer === "Approved")
            {
                count++;
                $("#attend-count").html(count + " people are going");
            }
        }
    });

    return false;
}

function joinEvent(eventID, type, count) {
    $.ajax({
        type: "POST",
        url: "Event?action=join&id=" + eventID,
        success: function() {
            if (type === "Closed")
                $("#request-join-message").html("You have already requested to join this event.");
            else if (type === "Public")
            {
                count++;
                $("#attend-count").html(count + " people are going");
                $("#request-join-message").html("<form onsubmit = 'return leaveEvent(" + eventID + ", \"" + type + "\", " + count + ")'>\n" +
                        "<input type = 'submit' class = 'event-join' value = 'Leave'/>\n" +
                        "</form>");
            }
        }
    });

    return false;
}

function leaveEvent(eventID, type, count) {
    console.log("went here");
    count--;
    $.ajax({
        type: "GET",
        url: "Event?action=leave&id=" + eventID,
        success: function() {
            var typeButton, typeMessage;
            if (type === "Public")
            {
                typeButton = "Join";
                typeMessage = "";
            }
            else if (type === "Closed")
            {
                typeButton = "Request to Join";
                typeMessage = "This is a closed event. You need to ask the host's permission to join!<br>";
            }
            $("#request-join-message").html("<form onsubmit = 'return joinEvent(" + eventID + ", \"" + type + "\", " + count + ")'>\n" +
                    typeMessage +
                    "<input type = 'submit' class = 'event-join' value = '" + typeButton + "'/>\n" +
                    "</form>");
            $("#attend-count").html(count + " people are going");
        }
    });

    return false;
}

function inviteBuddies() {
    console.log("inviting. . .");
    $.ajax({
        type: "POST",
        url: "Invite?action=invite",
        data: $("#invite-form").serialize(),
        success: function(html) {
            $("input:checkbox[name=invite-buddy]:checked").each(function()
            {
                $("#buddy_" + $(this).val()).remove();
            });

            $("#invited-list").append(html);
        }
    });

    return false;
}

function uninviteBuddy(buddyID) {
    $.ajax({
        type: "POST",
        url: "Invite?action=uninvite&id=" + buddyID,
        success: function(html) {
            $("#invited_" + buddyID).remove();
            $("#invite-list").append(html);
        }
    });

    return false;
}

//--- functions -------------------------
function checkPost() {
    var input = $(".input-post-textarea").val();
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

function confirmDeleteComment() {
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