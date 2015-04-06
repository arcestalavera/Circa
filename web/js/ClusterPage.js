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
});