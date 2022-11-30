package com.a2208.chat.ws;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.a2208.chat.entity.GroupMessage;
import com.a2208.chat.entity.Message;
import com.a2208.chat.entity.User;
import com.a2208.chat.service.GroupMessageService;
import com.a2208.chat.service.MessageService;
import com.a2208.chat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author websocket服务
 * 有bug，未完成
 * 群聊
 */
@ServerEndpoint(value = "/group/chat/{account}/{groupId}")
@Component
public class GroupEndPoint {
    private static final Logger log = LoggerFactory.getLogger(GroupEndPoint.class);
    /**
     * 记录每个群内连接数量
     */
    private static final Map<String, ConcurrentHashMap<String, GroupEndPoint>> groupOnline = new ConcurrentHashMap<>();
    private String groupId;
    private String account;
    private Session session;
    private static GroupMessageService groupMessageService;
    private static UserService userService;

    @Autowired
    public void setMessageService(GroupMessageService groupMessageService) {
        GroupEndPoint.groupMessageService = groupMessageService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        GroupEndPoint.userService = userService;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("account") String account) {
        this.account = account;
        this.session = session;
        if(!groupOnline.containsKey(this.groupId)){
            ConcurrentHashMap<String,GroupEndPoint> group = new ConcurrentHashMap<>();
            // 添加用户
            group.put(account, this);
            groupOnline.put(this.groupId, group);
        }else{
            groupOnline.get(groupId).put(account, this);
        }

        log.info("有新用户加入，account={}, 当前在线人数为：{}", account,
                groupOnline.get(groupId).size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("groupUsers", array);
        for (Object key : groupOnline.keySet()) {
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
    public void onClose(Session session,
                        @PathParam("account") String account,
                        @PathParam("groupId") String id) {
        groupOnline.get(id).remove(account);
        log.info("有一连接关闭，移除account={}的用户session, 当前群内在线人数为：{}"
                , account, groupOnline.get(id).size());
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
    public void onMessage(String message, Session session,
                          @PathParam("account") String account,
                          @PathParam("groupId") String id) {
        log.info("服务端收到用户account={}的消息:{}", account, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String toAccount = obj.getStr("to");
        String text = obj.getStr("text");
        // 根据 to用户名来获取 session，再通过session发送消息文本
        Session toSession = groupOnline.get(groupId).get(account).session;

        if (toSession != null) {
            // 组装消息发送给前端
            JSONObject jsonObject = new JSONObject();
            // from 是 zhang
            jsonObject.set("from", account);
            // text 同上面的text
            jsonObject.set("text", text);
            sendAllMessage(jsonObject.toString());

            log.info("发送给用户account={}，消息：{}", toAccount, jsonObject);
        } else {
            log.info("用户={} 未上线", toAccount);
        }
        //插入数据库
        insertGroupMessage(account, id, text);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        ConcurrentHashMap<String, GroupEndPoint> map = new ConcurrentHashMap<>();
        try {
            for (String key : groupOnline.get(groupId).keySet()) {
                GroupEndPoint groupEndPoint = map.get(key);
                groupEndPoint.sendMessage(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
    public static int getGroupOnline() {
        return groupOnline.size();
    }

    //插入群消息
    private void insertGroupMessage(String from, String to, String content){
        try {
            //后端存储信息
            User fromUser = userService.getOne(from);
            //后端存储信息
            Long fromId = userService.getOne(from).getId();
            GroupMessage gm = new GroupMessage(null,fromId ,Long.parseLong(to),
                    content, null);
            groupMessageService.insert(gm);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}