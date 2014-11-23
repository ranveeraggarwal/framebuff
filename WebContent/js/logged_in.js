/**
 * 
 */
    $("#logout").click( function(event){
    	event.preventDefault();
    	$.ajax({
    		url: '/Logout',
    		type: 'GET',
    		success: function (data){
    			location.reload();
    		},
    		error: function (data){
    			location.reload();
    		}
    	});
    });