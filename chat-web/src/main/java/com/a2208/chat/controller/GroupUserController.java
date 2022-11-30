package com.a2208.chat.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.a2208.chat.entity.Friend;
import com.a2208.chat.entity.GroupUser;
import com.a2208.chat.entity.User;
import com.a2208.chat.service.GroupUserService;
import com.a2208.chat.utils.Result;
import com.a2208.chat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/group/user")
public class GroupUserController {

    @Autowired
    private GroupUserService groupUserService;

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/info")
    public Result searchById(@RequestParam(value = "id")String id) {
        System.out.println(id);
        GroupUser groupUser = groupUserService.getById(Long.parseLong(id));
        if(groupUser != null)
            return ResultUtil.success(groupUser);

        return ResultUtil.fail();
    }
    /**
     * 根据groupId查询
     *
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/listmember")
    public Result getByGroupId(@RequestParam(value = "groupId") String groupId,
                              @RequestParam(value = "account") String account) {
        List<GroupUser> l = groupUserService.getByGroupId(Long.parseLong(groupId), account);
        System.out.println(l);
        if (!l.isEmpty())
            return ResultUtil.success(l);

        return ResultUtil.fail();
    }
    /**
     * 根据user_id查询
     *
     * @param userId user_id
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/list")
    public Result getByUserId(@RequestParam(value = "userId") String userId,
                              @RequestParam(value = "account") String account) {
        List<GroupUser> l = groupUserService.getByUserId(Long.parseLong(userId), account);
        System.out.println(l);
        if (!l.isEmpty())
            return ResultUtil.success(l);

        return ResultUtil.fail();
    }

    /**
     * 新增，忽略null字段
     *
     * @param groupUser 新增的记录
     * @return 返回影响行数
     */
    @RequestMapping("/insert")
    public Result insert(@RequestParam(value = "groupUser")String groupUser){
        if(groupUser != null){
            System.out.println(groupUser);
            JSONObject o = JSONUtil.parseObj(groupUser);

            GroupUser gu = new GroupUser(null, o.getLong("userId"),
                    o.getLong("groupId"),
                    o.getStr("name"));

            System.out.println("class:" + gu);
            if(groupUserService.isExist(gu) == null){
                if(groupUserService.insert(gu) != 0) {
                    return ResultUtil.success("加入群组成功！");
                }
            }
            return ResultUtil.fail();
        }
        return ResultUtil.error("数据错误！");
    }

    /**
     * 修改，忽略null字段
     *
     * @param groupUser 修改的记录
     * @return 返回影响行数
     */
    @RequestMapping("/update")
    public Result update(@RequestParam(value = "groupUser")String groupUser){
        if(groupUser != null){
            System.out.println("update requested:" + groupUser);
            JSONObject o = JSONUtil.parseObj(groupUser);
            Long id = o.getLong("id");

            GroupUser gu = new GroupUser(id,
                    o.getLong("userId"), o.getLong("groupId"),
                    o.getStr("name"));

            System.out.println("class:" + gu);
            if(groupUserService.update(gu) != 0) {
                return ResultUtil.success(groupUserService.getById(id));
            }
        }
        return ResultUtil.error(groupUser);
    }
    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam(value="id")String id) {
        if(groupUserService.delete(Long.parseLong(id)) != 0)
            return ResultUtil.success(id);

        return ResultUtil.fail();
    }


}