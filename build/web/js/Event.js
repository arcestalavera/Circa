function checkPost(){
    var input = $(".input-post-textarea").val();
    console.log("input = " + input);
    if(input === "")
    {
        alert("You're posting nothing! :(");
        return false;
    }
    else
    {
        return true;
    }
}

$(document).ready(function(){
   $('.comment-link').click(function(){
      $(this).parent().next(".input-comment-div").slideToggle("medium"); 
   });
   
   $('.post-show-comment').click(function(){
      $(this).next(".post-comments-whole").slideToggle("medium");    
   });
});