package com.thinkgem.jeesite.modules.util;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by boxiaotong on 2017/1/17.
 */

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket/{fansId}")
public class WebSocketUtil {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    private static Map<String, WebSocketUtil> webSocketMap = new HashMap();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("fansId") String fansId, Session session) {
        this.session = session;
        webSocketMap.put(fansId, this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        Iterator iter = webSocketMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry =  (Map.Entry)iter.next();
            String key = (String)entry.getKey();
            WebSocketUtil value= (WebSocketUtil) entry.getValue();
            if(value.equals(this)){
                webSocketMap.remove(key);
            }
        }
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    /*@OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("来自客户端的消息:" + message);
        this.sendMessage(message);
    }*/

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param fansId 粉丝的ID
     * @throws IOException
     */
    public boolean sendMessage(String fansId,String fansName,String receiverId,String receiverName) throws IOException {
        //System.out.println("webSocket:" + fansId);
        WebSocketUtil webSocketUtil = webSocketMap.get(fansId);//查找相应的会话
        //System.out.println("webSocketMap:" + webSocketMap.toString());
        //System.out.println("webSocketUtil:" + webSocketUtil);
        if (webSocketUtil != null) {//客服已打开会话框
            webSocketUtil.session.getBasicRemote().sendText(fansId);//推送到前端
            return true;//用户已经打开会话框
        }else{
            return false;//用户没有打开会话框，需要添加通知提醒
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketUtil.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketUtil.onlineCount--;
    }
}
