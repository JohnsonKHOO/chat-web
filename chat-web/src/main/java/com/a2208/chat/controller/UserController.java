package com.a2208.chat.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.a2208.chat.ws.ChatEndPoint;
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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (l != null) {
            return ResultUtil.success(l);
        } else
            return ResultUtil.fail();
    }
    //获取总用户数量
    @RequestMapping("/statistics/count")
    public Result count() {
        return ResultUtil.success(userService.countUser());
    }
    //pie图统计，获取用户男女比例
    @RequestMapping("/statistics/pie")
    public Result statisticsPie() {

        Map<String, Integer> map = new HashMap<>();
        map.put("countMale", userService.countBySex(0));
        map.put("countFemale", userService.countBySex(1));
        if (map.isEmpty())
            return ResultUtil.fail();
        return ResultUtil.success(map);
    }
    //条状图统计，获取年龄段用户人数人数
    @RequestMapping("/statistics/line")
    public Result statisticsLine() {

        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> male = new ArrayList<>();
        List<Integer> female = new ArrayList<>();
        for (int i = 0; i <= 70; i += 10) {
            male.add(userService.countByAgeSex(i, i + 9, 0));
            female.add(userService.countByAgeSex(i, i + 9, 1));
        }
        map.put("male", male);
        map.put("female", female);
        if (map.isEmpty())
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
    public Result searchById(@RequestParam(value = "id") String id) {
        System.out.println(id);
        User user = userService.getById(Long.parseLong(id));
        if (user != null)
            return ResultUtil.success(user);

        return ResultUtil.fail();
    }
    //用于后端展示所有图片
    @RequestMapping("/listavatar")
    public Result listAvatar() {
        List<String> l = userService.listAvatar();
        System.out.println(l);
        if (l != null) {
            return ResultUtil.success(l);
        } else
            return ResultUtil.fail();
    }

    /**
     * 根据主键查询
     *
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/search")
    public Result searchByAccount(@RequestParam(value = "account") String account,
                                  @RequestParam(value = "roleId", defaultValue = "1") Integer roleId) {
        System.out.println(account);
        List<User> l = userService.getByAccount(account, roleId);

        if (!l.isEmpty())
            return ResultUtil.success(l);

        return ResultUtil.fail();
    }
    //设置状态，不过没用到
    @RequestMapping(value = "/setstate")
    public Result setState(@RequestParam(value = "id") String id,
                           @RequestParam(value = "stateId") Integer stateId) {
        Long uid = Long.parseLong(id);
        Map<String, Long> state = new HashMap<>();
        state.put("id", uid);
        state.put("stateId", stateId.longValue());
        if (userService.updateStateId(state) != 0)
            return ResultUtil.success(userService.getById(uid));

        return ResultUtil.fail();
    }
    //验证密码
    @RequestMapping(value = "/checkpassword")
    public Result checkPassword(@RequestParam(value = "account") String account,
                           @RequestParam(value = "password") String password) {
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("account", account);
        loginInfo.put("password", password);
        User u = userService.login(loginInfo);
        if(u != null){
            return ResultUtil.success("密码验证正确！");
        }

        return ResultUtil.fail();
    }
    //更新密码
    @RequestMapping(value = "/updatepassword")
    public Result updatePassword(@RequestParam(value = "id") String id,
                                @RequestParam(value = "password") String password) {
        if(id != null && password != null){
            if(userService.updatePassword(id, password) != 0){
                return ResultUtil.success("更新密码成功");
            }

        }
        return ResultUtil.fail();
    }
    //更新个人签名
    @RequestMapping(value = "/updatesignature")
    public Result updateSignature(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "signature") String signature) {
        if(id != null && signature != null){
            if(userService.updateSignature(id, signature) != 0){
                return ResultUtil.success(userService.getById(Long.parseLong(id)));
            }

        }
        return ResultUtil.fail();
    }
    //更新头像
    @RequestMapping(value = "/updateavatar")
    public Result updateAvatar(@RequestParam(value = "id") String id,
                               @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        if(id != null && file != null){
            String avatar = FileUtil.uploadFile(file);
            if(userService.updateAvatar(id, avatar) != 0){
                return ResultUtil.success(userService.getById(Long.parseLong(id)));
            }
        }
        return ResultUtil.fail();
    }
    //获取在线人数(在WebSocket服务中的人数)
    @RequestMapping("/online")
    public Result online() {
        return ResultUtil.success(ChatEndPoint.getOnline());
    }

    /**
     * 修改
     *
     * @return 返回影响行数
     */
    @RequestMapping("/update")
    public Result update(@RequestParam(value = "user") String user) throws ParseException {
        if (user != null) {
            System.out.println("update requested:" + user);
            JSONObject o = JSONUtil.parseObj(user);
            Long id = o.getLong("id");
            String account = o.getStr("account");
            User oldUser = userService.getById(id);
            //账号已经存在同时不是原先账号
            if (userService.isExist(account) != null && !oldUser.getAccount().equals(account)) {
                return ResultUtil.fail();
            }

            User u = new User(id, account, o.getStr("nickname"),
                    o.getStr("password"),
                    DateUtil.dateParse(o.getStr("birthday")),
                    o.getInt("sex"), null, oldUser.getRoleId());

            System.out.println("class:" + u);
            if (userService.update(u) != 0) {
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
    public Result delete(@RequestParam(value = "id") String id) {
        if (userService.delete(Long.parseLong(id)) != 0)
            return ResultUtil.success(id);

        return ResultUtil.fail();
    }

}