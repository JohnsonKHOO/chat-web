package com.a2208.chat.controller;

import com.a2208.chat.entity.User;
import com.a2208.chat.service.UserService;
import com.a2208.chat.utils.DateUtil;
import com.a2208.chat.utils.Result;
import com.a2208.chat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
                            @RequestParam(value = "password") String password) {
        Map<String, String> loginInfo = new HashMap<>();

        loginInfo.put("password", password);
        loginInfo.put("account", account);
        System.out.println("loginInfo:"+loginInfo);
        User u = userService.login(loginInfo);
        System.out.println("user:" + u);

        if(u != null){
            return ResultUtil.success(u);
        }
        else
            return ResultUtil.fail();
    }

    //注册
    @RequestMapping("/register")
    public Result register(@RequestParam(value = "account") String account,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "nickname") String nickname,
                           @RequestParam(value = "birthday") String birthday,
                           @RequestParam(value = "sex") Integer sex,
                           @RequestParam(value = "avatar") String avatar) throws ParseException {

        User user = new User(account,nickname,password,DateUtil.dateParse(birthday),sex,avatar,1);
        System.out.println("register requested:" + user);
        if(userService.getByAccount(account) != null){
            return ResultUtil.fail();
        }
        if(userService.insert(user) != 0)
            return ResultUtil.success(user);

        return ResultUtil.error(user);
    }
}