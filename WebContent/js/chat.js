/**
 * 
 */

var wsocket;
var serviceLocation = "ws://localhost:8080/chat/";
var $nickName;
var $message;
var $chatWindow;
var room = '';

function onMessageReceived(evt) {
	//var msg = eval('(' + evt.data + ')');
	var msg = JSON.parse(evt.data); // native API
	var currDate = new Date(msg.received);
	var $messageLine = $('<tr><td class="user label label-info">' + msg.sender
			+ '</td><td class="message badge">' + msg.message + ' : sent on '
			+ currDate.toLocaleDateString() + '</td></tr>');
	$chatWindow.append($messageLine);
}
function sendMessage() {
	if (wsocket.readyState != 1)
		return false;
	var msg = '{"message":"' + $message.val() + '", "sender":"'
			+ $nickName.val() + '"}';
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
	};

}

function leaveRoom() {
	wsocket.close();
	$chatWindow.empty();
	$('.chat-wrapper').hide();
	$('.chat-signin').show();
	$nickName.focus();
}

$(document).ready(function() {
	$nickName = $('#nickname');
	$message = $('#message');
	$chatWindow = $('#response');
	$('.chat-wrapper').hide();
	$nickName.focus();

	$('#enterRoom').click(function(evt) {
		evt.preventDefault();
		connectToChatserver();
		$('.chat-wrapper h2').text('Chat # ' + $nickName.val() + "@" + room);
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