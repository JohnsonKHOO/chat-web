package com.a2208.chat.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.a2208.chat.component.SessionListener;
import com.a2208.chat.utils.DateUtil;
import com.a2208.chat.utils.FileUtil;
import com.a2208.chat.utils.Result;
import com.a2208.chat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.a2208.chat.entity.User;
import com.a2208.chat.service.UserService;
import org.springframework.web.multipart.MultipartFile;
import sun.text.resources.FormatData;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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

        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> male = new ArrayList<>();
        List<Integer> female = new ArrayList<>();
        for(int i = 0; i <= 70; i += 10){
            male.add(userService.countByAgeSex(i,i + 9,0));
            female.add(userService.countByAgeSex(i,i + 9,1));
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
    @RequestMapping("/info")
    public Result searchById(@RequestParam(value = "id")String id) {
        System.out.println(id);
        User user = userService.getById(Long.parseLong(id));
        if(user != null)
            return ResultUtil.success(user);

        return ResultUtil.fail();
    }

    /**
     * 根据主键查询
     *
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/search")
    public Result searchByAccount(@RequestParam(value = "account")String account,
                                  @RequestParam(value = "roleId", required = false, defaultValue = "1") Integer roleId){
        System.out.println(account);
        List<User> l = userService.getByAccount(account, roleId);

        if(!l.isEmpty())
            return ResultUtil.success(l);

        return ResultUtil.fail();
    }

    @RequestMapping(value = "/setstate")
    public Result setState(@RequestParam(value = "id")String id,
                           @RequestParam(value = "stateId")Integer stateId
    ) {
        Long uid = Long.parseLong(id);
        Map<String, Long> state = new HashMap<>();
        state.put("id", uid);
        state.put("stateId", stateId.longValue());
        if(userService.updateStateId(state) != 0)
            return ResultUtil.success(userService.getById(uid));

        return ResultUtil.fail();
    }

    @RequestMapping("/online")
    public Result online() {
        System.out.println("--------------online--------------");
        return ResultUtil.success(SessionListener.online);
    }

    /**
     * 修改
     *
     *
     * @return 返回影响行数
     */
    @RequestMapping("/update")
    public Result update(@RequestParam(value = "user")String user,
                         @RequestParam(value = "file", required = false) MultipartFile file
    ) throws ParseException, IOException {
        if(user != null){
            System.out.println("update requested:" + user);
            JSONObject o = JSONUtil.parseObj(user);
            Long id = o.getLong("id");
            String account = o.getStr("account");
            User oldUser = userService.getById(id);
            //账号已经存在同时不是原先账号
            if(userService.isExist(account) != null && !oldUser.getAccount().equals(account)){
                return ResultUtil.fail();
            }

            String avatar = FileUtil.uploadFile(file);

            User u = new User(id, account, o.getStr("nickname"),
                    o.getStr("password"),
                    DateUtil.dateParse(o.getStr("birthday")),
                    o.getInt("sex"), avatar, oldUser.getRoleId());

            System.out.println("class:" + u);
            if(userService.update(u) != 0) {
                return ResultUtil.success(userService.getById(id));
            }
        }
        return ResultUtil.error(user);

    }

    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam(value="id")String id) {
        if(userService.delete(Long.parseLong(id)) != 0)
            return ResultUtil.success(id);

        return ResultUtil.fail();
    }

}