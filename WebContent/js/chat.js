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
			url: "/getUser?userId="+msg.userId,
			type: "GET",
			async: false,
			success: function(data){
				userName=data['username'];
				localStorage.setItem(user,userName);
				console.log(data);
			}
		});
	}

	var $messageLine = $('<tr><td class="user label-info col-md-2">' + userName
			+ '</td><td class="message col-md-8" style="word-wrap: break-word;min-width: 160px;max-width: 160px;"><div>' + msg.message + '</div></td><td><input type="radio" name="trivia" value="trivia"> Trivia <br><input type="radio" name="fact" value="fact"> Fact </td><td class="message label-info col-md-1">'
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

function startChat(){
	$(document).prop('title', roomName);
	fetchChat(0);
	
	connectToChatserver();
	$('.chat-wrapper h2').text('Chat #'+ roomName);
	$('.chat-signin').hide();
	$('.chat-wrapper').show();
	$message.focus();
}

$(document).ready(function() {
	$message = $('#message');
	$chatWindow = $('#response');
	$('.chat-wrapper').hide();
	hostname = window.location.host;
	serviceLocation = "ws://"+ hostname+"/chat/";
	
	var loc = location.pathname;
	var pathArr = loc.split("/");
	if(pathArr.length > 2){
		$.ajax({
			url: '/getVideo?videoId='+pathArr[2],
			type: 'GET',
			dataType: 'JSON',
			async: false,
			success: function(data){
				roomName = data.title;
				room = data.videoId;
				startChat();
			}
		});
	}
	$('.chat-signin').submit(function(evt) {
		evt.preventDefault();
		roomName = $('.videoTitle', this).html();
		room = $('.videoId', this).data("value");
		startChat();
		return false;
	});
	
	$('#do-chat').submit(function(evt) {
		evt.preventDefault();
		sendMessage();
	});

	$('#leave-room').click(function() {
		leaveRoom();
		window.history.pushState("","","/Chat")
	});
	
});

var rowpos = $('#response tr:last').position();

$('#response').scrollTop(rowpos.top);