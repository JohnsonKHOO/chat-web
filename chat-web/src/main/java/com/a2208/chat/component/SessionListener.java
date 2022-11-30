package com.a2208.chat.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//session监听
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
    public static final Logger logger= LoggerFactory.getLogger(SessionListener.class);
    private static final Map<String, HttpSession> sessions = new HashMap<>();

    //获取在线用户信息
    public static List<HttpSession> getActiveSessions(){
        logger.info("--activeSessions--");
        return new ArrayList<>(sessions.values());
    }
    //登出时处理session
    public static void sessionLogout(HttpServletRequest request){
        logger.info("--sessionLogout--");
        HttpSession session = request.getSession(false);
        logger.info("session----" + session);
        if(sessions.containsKey(session.getId())){
            sessions.remove(session.getId(),session);
            session.removeAttribute("account");
            session.invalidate();
        }
    }
    //获取在线人数
    public static int getOnline(){
        return sessions.size();
    }


    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        logger.info("--attributeAdded--");
        logger.info("key----:"+httpSessionBindingEvent.getName());
        logger.info("value---:"+httpSessionBindingEvent.getValue());

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        logger.info("--attributeRemoved--");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        logger.info("--attributeReplaced--");
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        logger.info("---sessionCreated----");
        sessions.put(event.getSession().getId(),event.getSession());
        logger.info("online----" + getOnline());

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        logger.info("---sessionDestroyed----");
        sessions.remove(event.getSession().getId());
        logger.info("online----" + getOnline());

    }

}