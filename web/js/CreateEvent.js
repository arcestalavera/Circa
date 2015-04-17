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

    $("#view-select").change(function() {
        var type = $("#view-select option:selected").val();
        switch (type)
        {
            case "Public":
                $("#view-header").html("Anyone can view your event");
                $("#view-specified").hide();
                $("#invite-list > li").show();
                break;
            case "Specified":
                $("#view-header").html("Only specified buddies / clusters can see your event");
                $("#view-specified").show();
                $("#view-specified").css("display", "table");
                $("#invite-list > li").hide();
                $("input:checkbox").attr("checked", false);
                break;
        }
    });

    $(document).on("change", "#view-buddy", function() {
        if (this.checked) {
            var inviteBuddy = $("#invite-buddy_" + this.value);
            inviteBuddy.find("#invite-buddy").attr("checked", false);
            inviteBuddy.show();
        }
        else if (!this.checked){
            var inviteBuddy = $("#invite-buddy_" + this.value);
            inviteBuddy.find("#invite-buddy").attr("checked", false);
            inviteBuddy.hide();
        }
    });
});