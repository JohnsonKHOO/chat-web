package com.a2208.chat.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.a2208.chat.component.SessionListener;
import com.a2208.chat.entity.User;
import com.a2208.chat.service.UserService;
import com.a2208.chat.utils.DateUtil;
import com.a2208.chat.utils.FileUtil;
import com.a2208.chat.utils.Result;
import com.a2208.chat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService userService;

    //登录
    @RequestMapping(value = "/login")
    public Result userLogin(HttpServletRequest request) {
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("account", request.getParameter("account"));
        loginInfo.put("password", request.getParameter("password"));
        System.out.println("loginInfo:"+loginInfo);
        User u = userService.login(loginInfo);
        System.out.println("user:" + u);

        if(u != null){
            HttpSession session = request.getSession();
            session.setAttribute("account", request.getParameter("account"));
            //30分钟没有活动，则session失效
            session.setMaxInactiveInterval(30*60);

            return ResultUtil.success(u);
        }
        return ResultUtil.fail();
    }

    //登出时删除session，目前还有问题，无法获取网页session
    @RequestMapping(value = "/logout")
    public Result logout(HttpServletRequest request) {
        System.out.println("session1: " + request.getSession(false));
        SessionListener.sessionLogout(request);
        //防止创建Session
//        HttpSession session = request.getSession(false);
//        System.out.println("session" + session);
//        if(session != null){
//            session.removeAttribute("loginInfo");
//            session.invalidate();
//        }

        return ResultUtil.success("登出成功");
    }
    //获取当前页面session，但是getAttribute这个方法有错
    @RequestMapping(value = "/getsession")
    public Result getSession(HttpServletRequest request) {
        if(WebUtils.getSessionAttribute(request, "id") != null){
            return ResultUtil.success("正在使用！");
        }

        return ResultUtil.error("session不存在");
    }

    //注册
    @RequestMapping("/register")
    public Result register(@RequestParam(value = "user")String user,
                           @RequestParam(value = "roleId", required = false, defaultValue = "1")Integer roleId,
                           @RequestParam(value = "file", required = false) MultipartFile file
    ) throws ParseException, IOException {
        if(user != null){
            System.out.println("register requested:" + user);
            JSONObject o = JSONUtil.parseObj(user);
            String account = o.getStr("account");

            //账号已经存在
            if(userService.isExist(account) != null){
                return ResultUtil.fail();
            }
            String avatar = FileUtil.uploadFile(file);
            User u = new User(null, account, o.getStr("nickname"),
                    o.getStr("password"),
                    DateUtil.dateParse(o.getStr("birthday")),
                    o.getInt("sex"), avatar, roleId);

            System.out.println("class:" + u);
            if(userService.insert(u) != 0)
                return ResultUtil.success("注册成功！");
        }
        return ResultUtil.error("数据错误！");
    }
}