function addCluster() {
    $.ajax({
        type: "POST",
        url: "Cluster",
        data: $("#new-cluster-form").serialize(),
        success: function(html) {
            $("#user-cluster-list").append(html);
        }
    });

    return false;
}

function deleteCluster(clusterID) {
    $.ajax({
        type: "POST",
        url: "Cluster",
        data: $("#cluster_" + clusterID).find(".delete-cluster-form").serialize(),
        success: function() {
            $("#cluster_" + clusterID).remove();
        }
    });

    return false;
}
$(document).ready(function() {
    $('.delete-cluster-button').hover(function() {
        $(this).attr('src', 'img/clusters/DeleteButtonHover.png');
    }, function() {
        $(this).attr('src', 'img/clusters/DeleteButton.png');
    });
});