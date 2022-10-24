package com.a2208.chat.controller;

import java.text.ParseException;
import java.util.*;

import com.a2208.chat.utils.DateUtil;
import com.a2208.chat.utils.Result;
import com.a2208.chat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.a2208.chat.entity.User;
import com.a2208.chat.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    @RequestMapping("/list")
    public Result listAll() {

        List<User> l = userService.listAll();
        if(l != null){
            return ResultUtil.success(l);
        }
        else
            return ResultUtil.fail();
    }

    @RequestMapping("/statistics/pie")
    public Result statisticsPie() {

        Map<String, Integer> map = new HashMap<>();
        map.put("countMale",userService.countBySex(0));
        map.put("countFemale",userService.countBySex(1));
        if(map.isEmpty())
            return ResultUtil.fail();
        return ResultUtil.success(map);
    }

    @RequestMapping("/statistics/line")
    public Result statisticsLine() {

        Map<String, Map<String, Integer>> map = new HashMap<>();
        Map<String, Integer> male = new HashMap<>();
        Map<String, Integer> female = new HashMap<>();

        for(int i = 0; i <= 70; i += 10){
            int j = i + 10;
            String age = i + "-" + j;
            male.put(age,userService.countByAgeSex(i,j,0));
            female.put(age,userService.countByAgeSex(i,j,1));
        }
        map.put("male", male);
        map.put("female", female);
        if(map.isEmpty())
            return ResultUtil.fail();
        return ResultUtil.success(map);
    }

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/getbyid")
    public Result getById(@RequestParam(value = "id")Long id) {
        System.out.println(id);
        User user = userService.getById(id);
        if(user != null)
            return ResultUtil.success(user);

        return ResultUtil.fail();
    }

    /**
     * 修改，忽略null字段
     *
     *
     * @return 返回影响行数
     */
    @RequestMapping("/update")
    public Result update(@RequestParam(value = "id") Long id,
                      @RequestParam(value = "account") String account,
                      @RequestParam(value = "password") String password,
                      @RequestParam(value = "nickname") String nickname,
                      @RequestParam(value = "birthday") String birthday,
                      @RequestParam(value = "sex") Integer sex,
                      @RequestParam(value = "avatar") String avatar) throws ParseException {

        User user = new User(id,account,nickname,password, DateUtil.dateParse(birthday),sex,avatar,1);
        if(userService.update(user) != 0)
            return ResultUtil.success(user);

        return ResultUtil.fail();
    }

    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam(value="id")Integer id) {
        if(userService.delete(Long.parseLong(String.valueOf(id))) != 0)
            return ResultUtil.success(id);

        return ResultUtil.fail();
    }

}