/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    $('.delete-cluster-member-button').hover(function(){
       $(this).attr('src', 'img/clusterpage/DeleteButtonSmallHover.png'); 
    }, function() {
        $(this).attr('src', 'img/clusterpage/DeleteButtonSmall.png'); 
    });
    
    $('.delete-post-button').hover(function(){
       $(this).attr('src', 'img/clusters/DeleteButtonHover.png'); 
    }, function() {
        $(this).attr('src', 'img/clusters/DeleteButton.png'); 
    });
    
    $('.delete-comment-button').hover(function(){
       $(this).attr('src', 'img/clusterpage/DeleteButtonSmallHover.png'); 
    }, function() {
        $(this).attr('src', 'img/clusterpage/DeleteButtonSmall.png'); 
    });
    
    $('.post-container').mouseenter(function(){
        if ($(this).find('.delete-post-form').length) {
            $(this).find('.delete-post-form').css("visibility", "visible");
        }
    });
    
    $('.post-container').mouseleave(function(){
        if ($(this).find('.delete-post-form').length) {
            $(this).find('.delete-post-form').css("visibility", "hidden");
        }
    });
    
    $('.post-comment-commenter-div').mouseenter(function(){
        if ($(this).find('.delete-comment-form').length) {
            $(this).find('.delete-comment-form').css("visibility", "visible");
        }
    });
    
    $('.post-comment-commenter-div').mouseleave(function(){
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