//---- AJAX -----------

function buddyAction(action) {
    $.ajax({
        type: "POST",
        url: "Buddy?action=" + action,
        success: function() {
            if (action === "add")
                $("#buddy-action-div").html("<form onsubmit = 'return buddyAction(\"delete\")'>\n" +
                        "<input type = 'submit' value = 'Remove Buddy' class = 'buddy-button' />" +
                        "</form>");
            else if (action === "delete")
                $("#buddy-action-div").html("<form onsubmit = 'return buddyAction(\"add\")'>\n" +
                        "<input type = 'submit' value = 'Add Buddy' class = 'buddy-button'/>" +
                        "</form>");
        }
    });
    return false;
}

$(document).ready(function() {
    $('ul.tabs').each(function() {
        var $active, $content, $links = $(this).find('a');
        $active = $($links[0]);
        $active.addClass('active');
        $content = $($active[0].hash);
        $links.not($active).each(function() {
            $(this.hash).hide();
        });
        $(this).on('click', 'a', function(e) {
            $active.removeClass('active');
            $content.hide();
            $active = $(this);
            $content = $(this.hash);
            $active.addClass('active');
            $content.show();
            e.preventDefault();
        });
    });
    $(".event-header").click(function() {
        $(this).next(".event-description").slideToggle("medium");
    });
});
