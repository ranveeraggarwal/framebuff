/**
 * 
 */

$("#follow-user").click(function (e){
	$.ajax({
		url: '/ToggleFollow?toFollow='+toFollow,
		type: 'GET',
		success: function(data){
			data = data.trim();
			ihtml = $("#follow-user").html();
			console.log(data, ihtml);
			if (data == 'DONE'){
				if (ihtml == 'Follow') $("#follow-user").html('UnFollow');
				else $("#follow-user").html('Follow');
			}
		}
	});
});