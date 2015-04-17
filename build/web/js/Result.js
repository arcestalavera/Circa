$(document).ready(function () {
    $('#user-search-mode').css('color', '#AD0000');
    
    $('#user-search-mode').click(function(){
        $(this).css('color', '#AD0000');
        $('#event-search-mode').css('color', '#DCDCDC');
        $('#user-search-list').css('display', 'block');
        $('#event-search-list').css('display', 'none');
    });
    $('#event-search-mode').click(function(){
        $(this).css('color', '#AD0000');
        $('#user-search-mode').css('color', '#DCDCDC');
        $('#event-search-list').css('display', 'block');
        $('#user-search-list').css('display', 'none');
    });
});