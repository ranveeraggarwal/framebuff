/**
 * 
 */

var wsocket;
var serviceLocation = "ws://localhost:8080/chat/";
var $message;
var $chatWindow;
var room = '';

function onMessageReceived(evt) {

	var msg = JSON.parse(evt.data); // native API
	var currDate = new Date(msg.chatDate);
	var $messageLine = $('<tr><td class="user label label-info">' + msg.userId
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
	room = $('#chatroom option:selected').val();
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
	}

}

function leaveRoom() {
	wsocket.close();
	$chatWindow.empty();
	$('.chat-wrapper').hide();
	$('.chat-signin').show();
}

$(document).ready(function() {
	$message = $('#message');
	$chatWindow = $('#response');
	$('.chat-wrapper').hide();
	roomName = $('#chatroom option:selected').html();

	$('#enterRoom').click(function(evt) {
		evt.preventDefault();
		connectToChatserver();
		$('.chat-wrapper h2').text('Chat #'+ roomName);
		$('.chat-signin').hide();
		$('.chat-wrapper').show();
		$message.focus();
	});
	$('#do-chat').submit(function(evt) {
		evt.preventDefault();
		sendMessage()
	});

	$('#leave-room').click(function() {
		leaveRoom();
	});
});