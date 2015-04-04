$(document).ready(function () {
    $('.delete-cluster-button').hover(function(){
       $(this).attr('src', 'img/clusters/DeleteButtonHover.png'); 
    }, function() {
        $(this).attr('src', 'img/clusters/DeleteButton.png'); 
    });
});