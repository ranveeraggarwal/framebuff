/**
 * 
 */

$("#username").change(function(e){
	var username = $(this).val();
	$.ajax({
		url: '/CheckUsername?username='+username,
		type: 'GET',
		success: function (data){
			data = data.trim();
			console.log(data);
			if (data == 'TRUE'){
				$("#username").parent("div").removeClass("has-error").addClass("has-success");
			}
			else {
				$("#username").parent("div").removeClass("has-success").addClass("has-error");
			}
		}
	});
});

$("#email").change(function(e){
	var username = $(this).val();
	$.ajax({
		url: '/CheckUsername?email='+username,
		type: 'GET',
		success: function (data){
			data = data.trim();
			console.log(data);
			if (data == 'TRUE'){
				$("#email").parent("div").removeClass("has-error").addClass("has-success");
			}
			else {
				$("#email").parent("div").removeClass("has-success").addClass("has-error");
			}
		}
	});
});

$("#password,#password2").change(function(e){
	var pass1 = $("#password").val();
	var pass2 = $("#password2").val();
	if ((pass1 != pass2) || pass1.length < 8){
		$("#password").parent("div").removeClass("has-success").addClass("has-error");
		$("#password2").parent("div").removeClass("has-success").addClass("has-error");
	}
	else {
		$("#password").parent("div").removeClass("has-error").addClass("has-success");
		$("#password2").parent("div").removeClass("has-error").addClass("has-success");
	}
});