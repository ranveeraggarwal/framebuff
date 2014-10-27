/**
 * 
 */

var wsocket;
var serviceLocation;
var $message;
var $chatWindow;
var room = '';

function onMessageReceived(evt) {

	console.log(evt.data);
	var msg = JSON.parse(evt.data); // native API
	printMessage(msg);
}

function printMessage(msg){
	
	var currDate = new Date(msg.chatDate);
	var user = "user"+msg.userId;
	var userName;
	var flag = false;
	if(localStorage.getItem(user)){
		userName = localStorage.getItem(user);
	}
	else{
		$.ajax({
			url: "/GetUsername?userId="+msg.userId,
			type: "GET",
			async: false,
			success: function(data){
				userName=data;
				localStorage.setItem(user,userName);
				console.log(data);
			}
		});
	}

	var $messageLine = $('<tr><td class="user label label-info">' + userName
			+ '</td><td class="message badge">' + msg.message + ' : sent on '
			+ currDate.toLocaleDateString() + '</td></tr>');
	$chatWindow.append($messageLine);
}
function sendMessage() {
	if (wsocket.readyState != 1)
		return false;
	var msg = '{"message":"' + $message.val() + '"}';
	wsocket.send(msg);
	$message.val('').focus();
}

function connectToChatserver() {
	wsocket = new WebSocket(serviceLocation + room);
	console.log(wsocket);
	wsocket.onmessage = onMessageReceived;
	wsocket.onopen = function(evt) {
		console.log("Connection stablished :)");
	};
	wsocket.onclose = function(evt) {
		console.log("Connection closed");
		console.log("Reason: "+evt.reason);
	};
	wsocket.onerror = function(evt) {
		console.log("Some error occured while connection...try again later");
	};

}

function leaveRoom() {
	wsocket.close();
	$chatWindow.empty();
	$('.chat-wrapper').hide();
	$('.chat-signin').show();
}

function fetchChat(offset){
	var JSONdata = {
			"videoId": room,
			"offset" : offset
	};
	$.ajax({
		url: "/FetchChat",
		type: "POST",
		data: JSONdata,
		dataType: "JSON",
		success: function(data){
			fetchedChat = data;
			$.each(fetchedChat, function(key, value){
				printMessage(value);
			});
		},
		error : function (data){
			console.log(data);
		}
	});
	
}

$(document).ready(function() {
	$message = $('#message');
	$chatWindow = $('#response');
	$('.chat-wrapper').hide();
	hostname = window.location.host;
	serviceLocation = "ws://"+ hostname+"/chat/";
	

	$('#enterRoom').click(function(evt) {
		roomName = $('#chatroom option:selected').html();
		room = $('#chatroom option:selected').val();
		$(document).prop('title', roomName);
		
		fetchChat(0);
		
		evt.preventDefault();
		connectToChatserver();
		$('.chat-wrapper h2').text('Chat #'+ roomName);
		$('.chat-signin').hide();
		$('.chat-wrapper').show();
		$message.focus();
	});
	$('#do-chat').submit(function(evt) {
		evt.preventDefault();
		sendMessage();
	});

	$('#leave-room').click(function() {
		leaveRoom();
	});
});