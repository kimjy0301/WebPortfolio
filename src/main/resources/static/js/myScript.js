            function myMap() {
                var myLatlng;
                var map;
                var marker;
                <!-- 구글맵에서 좌표값을 얻고 입력해주세요.  -->
                myLatlng = new google.maps.LatLng(37.708635, 126.855970);
                var mapOptions = {
                    zoom: 17, <!--지도 확대, 축소 정도 설정 -->
                    center: myLatlng,
                    mapTypeId: google
                        .maps
                        .MapTypeId
                        .ROADMAP,
                    scrollwheel: false, <!-- 마우스 스크롤 false -->
                    draggable: false <!-- 마우스 드레그(클릭) false -->
                };
                map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
                <!-- 위치정보이름 및 위치정보주소 입력하세요. -->
                var contentString = '<p style="line-height: 20px;">집</p><p>경기도 고양시의 시골마을</p>';
                var infowindow = new google.maps.InfoWindow({content: contentString});
                marker = new google.maps.Marker({position: myLatlng, map: map, title: 'Marker'});
                infowindow.open(map, marker);
                google
                    .maps
                    .event
                    .addListener(marker, 'click', function () {
                        infowindow.open(map, marker);
                    });
            }           






$(document).ready(function () {
                function popToast(level, msg) {
                    toastr.options = {
                        "closeButton": false,
                        "debug": false,
                        "newestOnTop": false,
                        "progressBar": false,
                        "positionClass": "toast-bottom-full-width",
                        "preventDuplicates": false,
                        "onclick": null,
                        "showDuration": "300",
                        "hideDuration": "700",
                        "timeOut": "3000",
                        "extendedTimeOut": "700",
                        "showEasing": "swing",
                        "hideEasing": "linear",
                        "showMethod": "fadeIn",
                        "hideMethod": "fadeOut"
                    }
                    Command : toastr[level](msg);
                }
                $("#testbuttonn").on('click', function () {
                    popToast("info", "메소드로 호출 토스트");
                });
                $("#toastSendButton1").on('click', function () {
                    popToast("success", '<DIV class="center-block"><img class="center-block" style="width:200px" src="https://media.giphy.com/media/13EOgFTzFfiUM/giphy.gif">&nbsp<p class="text-center">저장성공!!!!</p></DIV>');
                });
                $("#toastSendButton2").on('click', function () {
                    popToast("error", "#toastSendButton2 에러 토스트 호출");
                });
                $("#moreview").on('click', function () {
                    popToast("info", '<DIV class="center-block"><img class="center-block" style="width:100%"  src="https://media.giphy.com/media/xUySTwI1AoIbf57QKk/giphy.gif">&nbsp<p class="text-center">출발!!</p></DIV>');
                    $("#msgSendButton").on('click', function () { // to_ajax();
                    });
                    function to_ajax() { // var queryString =
											// $("form[name=messageForm]").serialize()
											// ;
                        var queryString = JSON.stringify($("form[name=messageForm]").serializeObject());
                        console.log(queryString);
                        popToast("info", "전송중..");
                        $.ajax({
                            type: 'post',
                            url: 'insertajax',
                            data: queryString,
                            dataType: 'json',
                            contentType: 'application/json;charset=UTF-8',
                            success: function (data) {
                                if (data.resultMessage == "success") {
                                    popToast("success", '<DIV class="center-block"><img class="center-block" style="width:100%" src="https://media.giphy.com/media/a0h7sAqON67nO/giphy.gif">&nbsp<p class="text-justify">저장성공!!!!</p></DIV>');
                                    popToast("info", data.name + "님 의견 감사합니다.");
                                    $("#messageForm")[0].reset();
                                    $('#messageTable > tbody:last').append('<tr><td>' + data.name + '</td><td>' + data.email + '</td><td>' + data.text + '</td></tr>');
                                } else {
                                    var returnMessage = data.resultMessage;
                                    popToast("warning", returnMessage);
                                }
                            },
                            error: function (xhr) {
                                var err = JSON.parse(xhr.responseText);
                                popToast("warning", err.message);
                            }
                        });
                    }
                });
            });
            function privacyFunction() {
                popToast("info", '<DIV class="center-block"><img class="center-block" style="width:100%" src="https://media.giphy.com/media/dkGhBWE3SyzXW/giphy.gif">&nbsp<p class="text-center">아무것도 없어~~~~~~</p></DIV>');
            }
            function termsFunction() {
                popToast("info", '<DIV class="center-block"><img class="center-block" style="width:100%" src="https://media.giphy.com/media/l1J9qrAVNHt1bDi2A/giphy.gif">&nbsp<p class="text-center">여기도없음..^^</p></DIV>');
            }
            function pageTopFunction() {
                popToast("info", '<DIV class="center-block"><img class="center-block" style="width:100%" src="https://media.giphy.com/media/3ohhwfZXnlXE0UfFjW/source.gif">&nbsp<p class="text-center">맨위로!!</p></DIV>');
            }
            
            
            
            $(window).on('load', function () {
                $('#loading').fadeOut(800);         
                
            });
            
            
            $( document ).ajaxStop(function() {
            	$('#loading').fadeOut(2000); //ajax종료시 로딩바를 숨겨준다.
            	});
            
            $( document ).ajaxStart(function() {
            	$('#loading').fadeIn(300); //ajax실행시 로딩바를 보여준다.
            	});
            
            
            
            
            