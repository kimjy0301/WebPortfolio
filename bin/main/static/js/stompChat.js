    $(function () {
        var sendBtn = $('.msg_send_btn');
        var createBtn = $('.create');
        var endBtn = $('.exit');
        var result = Math.floor(Math.random() * 10000) + 1;
        var roomId = result;
        var messageInput = $('.write_msg');        
        var chatBox =  $('.msg_history');        
        var member = "test";
        var sock;
        var client;

        function setConnected(connected) {
        	
        	if (connected == true){
        		$(".create").hide();
        		$(".exit").show();
        	}else{
        		$(".create").show();
        		$(".exit").hide();
        	}
            
        }
        
        function disconnect() {
            if (client !== null) {
            	setConnected(false);
            	client.disconnect();
            }
        }

        
        endBtn.click(function () {
          	 disconnect();
        });
        
        
        sendBtn.click(function () {
            var message = messageInput.val();
        	var room = roomId;
        	
            client.send('/publish/chat/message/' + room, {callUser : 'client'}, JSON.stringify({chatRoomId: room, message: message, writer: 'client'}));
            messageInput.val('');
        });
        
        createBtn.click(function () {
        	sock = new SockJS("/stomp-chat");
            client = Stomp.over(sock); // 1. SockJS를 내부에 들고 있는 client를 내어준다.

            // 2. connection이 맺어지면 실행된다.
            client.connect({callUser : 'client'}, function () {
            	setConnected(true);
            	
                client.send('/publish/chat/create', {}, JSON.stringify({roomName: roomId})); 
                client.subscribe('/subscribe/chat/room/' + roomId, function (chat) {            	
                    var content = JSON.parse(chat.body);
                    var sender = content.writer                
                    var Now = new Date();

                    var NowTime = Now.getFullYear();
                    NowTime += '-' + Now.getMonth() + 1 ;
                    NowTime += '-' + Now.getDate();
                    NowTime += ' ' + Now.getHours();
                    NowTime += ':' + Now.getMinutes();
                    NowTime += ':' + Now.getSeconds();

                    if ( sender == 'client'){   
                    	chatBox.append('<div class="outgoing_msg"><div class="sent_msg"><p>' + content.message +'</p><span class="time_date">' + NowTime + '</span></div></div>')               	
                    } else if ( sender == 'server'){
                    	chatBox.append('<div class="incoming_msg"><div class="incoming_msg_img"><img src="/img/team/face2.jpg" alt="sunil"></div><div class="received_msg"><div class="received_withd_msg"><p>' + content.message +'</p><span class="time_date">' + NowTime + '</span></div></div></div>')               	           	
                    }
                    $(chatBox).scrollTop($(chatBox)[0].scrollHeight);
                
                }, {callUser : 'client'});                
            });
            
        
        });
        
        $('.write_msg').keypress(function (e) {
        	 var key = e.which;
        	 if(key == 13)  // the enter key code
        	  {
        	    $('button[class = msg_send_btn]').click();
        	    return false;  
        	  }
        });   
        
        
        
    });