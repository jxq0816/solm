/**
 * Created by boxiaotong on 2017/3/24.
 */
var websocket = null;
$(document).ready(function () {
    var fansId = $("#senderId").val();
    var ip = $("#ip").val();
    var port = $("#port").val();
//判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        var url = "ws://" + ip + ":" + port + "/solm/websocket/" + fansId;
        websocket = new WebSocket(url);
    }
    else {
        alert('当前浏览器 Not support websocket');
    }

//连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

//连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    }

//接收到消息的回调方法
    websocket.onmessage = function (event) {
        var fansId = event.data;
        var senderId = $("#senderId").val();
        if (senderId == fansId) {
            reload();
        } else {
            setMessageInnerHTML(fansId);
        }
    }

//连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }
})
//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    document.getElementById('message').innerHTML += innerHTML + '<br/>';
}
//发送消息
function send() {
    var message = document.getElementById('text').value;
    websocket.send(message);
}
//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}
function chartCommit() {
    var text=$("#text").val();
    if(text==null||text==''||text==undefined){
        alert("回复内容不能为空");
        return ;
    }
    var data= $('#charForm').serialize();
    $.ajax({
        type: "POST",
        url: ctx + '/weibo/weiBoMail/chartSave',
        dataType: "html",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: data,
        success: function (result) {

            var rs = jQuery.parseJSON(result);
            var info = rs.info;
            alert(info);
            var senderId = rs.senderId
            var senderName = rs.senderName;
            var receiverId = rs.receiverId
            var receiverName = rs.receiverName;
            var url = ctx + "/weibo/weiBoMail/chart?last=1&senderId=" + senderId + "&receiverId=" + receiverId + "&receiverName=" + receiverName + "&senderName=" + senderName;
            window.location.href = url;
        }
    });
}
function reload() {
    var senderId = $("#senderId").val();
    var senderName = $("#senderName").val();
    var receiverId = $("#receiverId").val();
    var receiverName = $("#receiverName").val();
    var url = ctx + "/weibo/weiBoMail/chart?last=1&senderId=" + senderId + "&receiverId=" + receiverId + "&receiverName=" + receiverName + "&senderName=" + senderName;
    window.location.href = url;
}