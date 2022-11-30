package com.a2208.chat.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.a2208.chat.entity.Friend;
import com.a2208.chat.service.FriendService;
import com.a2208.chat.utils.Result;
import com.a2208.chat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    @RequestMapping("/list")
    public Result listAll() {
        List<Friend> l = friendService.listAll();
        if(l != null){
            return ResultUtil.success(l);
        }
        else
            return ResultUtil.fail();
    }
    /**
     * 根据id查询
     *
     * @param id id
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/info")
    public Result searchById(@RequestParam(value = "id") String id) {
        System.out.println(id);
        Friend friend = friendService.getById(Long.parseLong(id));
        if (friend != null)
            return ResultUtil.success(friend);

        return ResultUtil.fail();
    }

    /**
     * 根据user_id查询
     *
     * @param userId user_id
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/search")
    public Result getByUserId(@RequestParam(value = "userId") String userId,
                              @RequestParam(value = "account") String account) {
        List<Friend> l = friendService.getByUserId(Long.parseLong(userId), account);
        System.out.println(l);
        if (!l.isEmpty())
            return ResultUtil.success(l);

        return ResultUtil.fail();
    }

    /**
     * 插入好友记录
     *
     * @param userId,friendId 新增记录
     * @return 返回影响行数
     */
    @RequestMapping("/add")
    public Result insert(@RequestParam(value = "userId") String userId,
                         @RequestParam(value = "friendId") String friendId) {
        if (userId != null && friendId != null) {

            if(userId.equals(friendId))
                return ResultUtil.error("不能添加自己为好友！");

            Friend f1 = new Friend(null, Long.parseLong(userId),
                    Long.parseLong(friendId), "默认好友");
            Friend f2 = new Friend(null, Long.parseLong(friendId),
                    Long.parseLong(userId), "默认好友");
            if(friendService.isExist(f1) == null && friendService.isExist(f2) == null){
                if (friendService.insert(f1) != 0 && friendService.insert(f2) != 0) {
                    return ResultUtil.success("添加好友成功！");
                }
            }
            return ResultUtil.fail();
        }
        return ResultUtil.error("数据错误");
    }

    /**
     * 修改，忽略null字段
     *
     * @param friend 修改的记录
     * @return 返回影响行数
     */
    @RequestMapping("/update")
    public Result update(@RequestParam(value = "friend") String friend) {
        if (friend != null) {
            System.out.println("更新请求" + friend);
            JSONObject o = JSONUtil.parseObj(friend);
            Long id = o.getLong("id");
            Friend f = new Friend(id, o.getLong("userId"),
                    o.getLong("friendId"),
                    o.getStr("name"));

            if (friendService.update(f) != 0) {
                return ResultUtil.success("修改成功");
            }
        }
        return ResultUtil.error(friend);
    }

    /**
     * 删除记录
     *
     * @param friend 待删除的记录
     * @return 返回影响行数
     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam(value = "friend") String friend) {
        if(friend != null){
            JSONObject o = JSONUtil.parseObj(friend);
            Long userId = o.getLong("userId");
            Long friendId = o.getLong("friendId");

            Friend f1 = new Friend(o.getLong("id"), userId, friendId, null);
            Friend f2 = new Friend(null, friendId, userId, null);

            if (friendService.delete(f1)!= 0 && friendService.delete(f2)!= 0)
                return ResultUtil.success("删除好友成功！");
        }
        return ResultUtil.fail();
    }

}