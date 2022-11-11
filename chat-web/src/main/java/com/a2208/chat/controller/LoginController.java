package com.a2208.chat.controller;

import cn.hutool.json.JSON;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public Result userLogin(@RequestParam(value = "account") String account,
                            @RequestParam(value = "password") String password,
                            HttpServletRequest request) {
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("password", password);
        loginInfo.put("account", account);
        System.out.println("loginInfo:"+loginInfo);
        User u = userService.login(loginInfo);
        System.out.println("user:" + u);

        if(u != null){
//            Long id = u.getId();
//            Map<String, Long> online = new HashMap<>();
//            online.put("id", id);
//            online.put("stateId", 1L);
            HttpSession session = request.getSession();
            session.setAttribute("account",account);
            session.setAttribute("password",password);
            //一小时没有活动，则session失效
            session.setMaxInactiveInterval(60*60);

            System.out.println(session.getId());
            return ResultUtil.success(u);

        }
        return ResultUtil.fail();
    }


    //默认登出stateId为0，使用登出功能前端传参不需携带stateId
    @RequestMapping(value = "/logout")
    public Result logout(HttpServletRequest request) {
        //防止创建Session
        HttpSession session = request.getSession(false);
        if(session != null){
            session.removeAttribute("account");
            session.removeAttribute("password");
            session.invalidate();
        }
        return ResultUtil.success("登出成功");
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
            User u = new User(account, o.getStr("nickname"),
                    o.getStr("password"),
                    DateUtil.dateParse(o.getStr("birthday")),
                    o.getInt("sex"), avatar, roleId);

            System.out.println("class:" + u);
            if(userService.insert(u) != 0)
                return ResultUtil.success("注册成功");
        }
        return ResultUtil.error("数据错误");
    }
}