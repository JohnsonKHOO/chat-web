package com.a2208.chat.ws;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.a2208.chat.entity.Message;
import com.a2208.chat.entity.User;
import com.a2208.chat.service.MessageService;
import com.a2208.chat.service.UserService;
import com.a2208.chat.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author websocket服务
 * 单聊
 */
@ServerEndpoint(value = "/chat/{account}")
@Component
public class ChatEndPoint {
    private static final Logger log = LoggerFactory.getLogger(ChatEndPoint.class);
    /**
     * 记录当前在线连接数
     */
    private static final Map<String, Session> online = new ConcurrentHashMap<>();

    //在websocket里不能直接注入service或bean
    private static MessageService messageService;
    private static UserService userService;

    public static int getOnline() {
        return online.size();
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        ChatEndPoint.messageService = messageService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        ChatEndPoint.userService = userService;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("account") String account) {
        if (online.containsKey(account)) {
            return;
        }
        online.put(account, session);
        log.info("有新用户加入，account={}, 当前在线人数为：{}", account, online.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (Object key : online.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("account", key);
            array.add(jsonObject);
        }
        // 后台发送消息给所有的客户端
        sendAllMessage(JSONUtil.toJsonStr(result));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("account") String account) {
        online.remove(account);
        log.info("有一连接关闭，移除account={}的用户session, 当前在线人数为：{}"
                , account, online.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("account") String account) {
        log.info("服务端收到用户account={}的消息:{}", account, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String toAccount = obj.getStr("to");
        String text = obj.getStr("text");
        // 根据 to用户名来获取 session，再通过session发送消息文本
        Session toSession = null;
        if (online.get(toAccount) != null) {
            toSession = online.get(toAccount);
        }
        if (toSession != null) {
            // 组装消息发送给前端
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("from", account);
            jsonObject.set("text", text);
            this.sendMessage(jsonObject.toString(), toSession);
            log.info("发送给用户account={}，消息：{}", toAccount, jsonObject);

        } else {
            log.info("用户={} 未上线", toAccount);
        }
        //插入数据库
        insertMessage(account, toAccount, text);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : online.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
    //插入消息
    private void insertMessage(String from, String to, String content){
        try {
            //后端存储信息
            User fromUser = userService.getOne(from);
            User toUser = userService.getOne(to);
            Message m = new Message(null, fromUser.getId(), toUser.getId(),
                    content, 0, null);
            log.info("从用户id={}，发给：{}", fromUser.getId(), toUser.getId());
            messageService.insert(m);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}