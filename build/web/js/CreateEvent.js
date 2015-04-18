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
                break;
            case "Buddies":
                $("#view-header").html("All your buddies can view your event");
                $("#view-specified").hide();
                break;
            case "Specified":
                $("#view-header").html("Only specified clusters can see your event");
                $("#view-specified").show();
                $("#view-specified").css("display", "table");
                break;
        }
    });
});