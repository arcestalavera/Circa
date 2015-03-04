// background slider
$(window).load(function() {
	var i = 0;
	var images = ['img/login/img2.jpg', 'img/login/img3.jpg', 'img/login/img1.jpg'];
	var image = $('#backgroundImage');
				
	setInterval(function(){
		image.fadeOut(300, function () {
			image.attr('src', images[i++]);
			image.fadeIn(300);
		});
		if(i == images.length)
			i = 0;
	}, 10000);
});

// logo rotate
$(document).ready(function(){
	var flag = true;
	setInterval(function(){
		if(flag){
			$('#circaLogo').css({'transition': 'all 1s ease-in-out 0s', 'transform': 'rotate(360deg)'});
			flag = false;
		}else{
			$('#circaLogo').css({'transition': 'all 1s ease-in-out 0s', 'transform': 'rotate(0deg)'});
			flag = true;
		}
	}, 10000);
});


//background set to width of screen
$(document).ready(function(){
	//$('#header').css('width', screen.availWidth + 'px');
});


$(document).ready(function(){
	$("#signUpDiv").css('left', '+=400px');
});

$(document).ready(function(){
	$('#signUpButton').click(function(){
		$("#welcomeDiv").animate({ left: '-=300px', opacity: 0 }, 'slow');
		$('#header').slideUp('slow');
		$("#signUpDiv").css('display', 'block');
		$("#signUpDiv").animate({ left: '-=400px', opacity: 1}, 'slow');
	});
});

$(document).ready(function(){
	$('#signUpCircaLabel').click(function(){
		$("#signUpDiv").animate({ left: '+=400px', opacity: 0}, 'slow');
		$("#signUpDiv").css('display', 'none');
		$("#welcomeDiv").animate({ left: '+=300px', opacity: 1 }, 'slow');
		$('#header').slideDown('slow');
	});
});