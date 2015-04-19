function addComment(postID) {
    $.ajax({
        type: "POST",
        url: "Comment?action=add&id=" + postID + "&curpage=cluster",
        data: $(".new-comment-form").serialize(),
        success: function(html) {
            $("#post_" + postID).find("#comment-list").append(html);
            $(".new-comment-comment-field").val("");
        }
    });
    return false;
}

function deleteComment(commentID) {
    $.ajax({
        type: "POST",
        url: "Comment?action=delete&id=" + commentID + "&curpage=cluster",
        success: function() {
            $("#comment_" + commentID).remove();
        }
    });
    return false;
}

function likePost(postID, userID, count) {
    $.ajax({
        type: "POST",
        url: "Like?action=like&pid=" + postID + "&uid=" + userID + "&curpage=cluster",
        success: function() {
            count++;
            if (count === 1)
            {
                $("#post_" + postID).find(".post-container").append("<p class = \"post-like-count\">  | 1 like </p>");
            }
            else if (count > 1)
            {
                $("#post_" + postID).find(".post-like-count").html("<p class = \"post-like-count\">  | " + count + " like </p>");
            }
            $("#post_" + postID).find(".post-like-option").html("<a class = \"like-link\" onclick = \"return unlikePost(" + postID + ", " + userID + ", " + count + ")\">Unlike </a>");
        }
    });
    return false;
}

function unlikePost(postID, userID, count) {
    $.ajax({
        type: "POST",
        url: "Like?action=unlike&pid=" + postID + "&uid=" + userID + "&curpage=cluster",
        success: function() {
            count--;
            console.log("post = " + postID + " count = " + count);
            if (count === 0)
            {
                $("#post_" + postID).find(".post-like-count").remove();
            }
            else if (count >= 1)
            {
                $("#post_" + postID).find(".post-like-count").html("<p class = \"post-like-count\">  | " + count + " like </p>");
            }
            $("#post_" + postID).find(".post-like-option").html("<a class = \"like-link\" onclick = \"return likePost(" + postID + ", " + userID + ", " + count + ")\">Like </a>");
        }
    });
    return false;
}

$(document).ready(function() {
    $(document).on('hover', '.delete-cluster-member-button', function() {
        $(this).attr('src', 'img/clusterpage/DeleteButtonSmallHover.png');
    }, function() {
        $(this).attr('src', 'img/clusterpage/DeleteButtonSmall.png');
    });
    $(document).on('hover', '.delete-post-button', function() {
        $(this).attr('src', 'img/clusters/DeleteButtonHover.png');
    }, function() {
        $(this).attr('src', 'img/clusters/DeleteButton.png');
    });
    $(document).on('hover', '.delete-comment-button', function() {
        $(this).attr('src', 'img/clusterpage/DeleteButtonSmallHover.png');
    }, function() {
        $(this).attr('src', 'img/clusterpage/DeleteButtonSmall.png');
    });
    $(document).on('mouseenter', '.post-container', function() {
        if ($(this).find('.delete-post-form').length) {
            $(this).find('.delete-post-form').css("visibility", "visible");
        }
    });
    $(document).on('mouseleave', '.post-container', function() {
        if ($(this).find('.delete-post-form').length) {
            $(this).find('.delete-post-form').css("visibility", "hidden");
        }
    });
    $(document).on('mouseenter', '.post-comment-commenter-div', function() {
        if ($(this).find('.delete-comment-form').length) {
            $(this).find('.delete-comment-form').css("visibility", "visible");
        }
    });
    $(document).on('mouseleave', '.post-comment-commenter-div', function() {
        if ($(this).find('.delete-comment-form').length) {
            $(this).find('.delete-comment-form').css("visibility", "hidden");
        }
    });
    $("#cluster-name").keypress(function(event) {
        if (event.which == 13) {
            event.preventDefault();
            $("#cluster-name-div").submit();
        }
    });
    $(".new-comment-comment-field").keypress(function(event) {
        if (event.which == 13) {
            event.preventDefault();
            $(this).parent().submit();
        }
    });
});