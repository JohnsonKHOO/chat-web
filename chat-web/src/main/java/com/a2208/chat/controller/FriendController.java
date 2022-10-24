package com.a2208.chat.controller;

import java.util.List;

import com.a2208.chat.utils.Result;
import com.a2208.chat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2208.chat.entity.Friend;
import com.a2208.chat.service.FriendService;

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
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/getById")
    public Friend getById(Long id) {
        return friendService.getById(id);
    }

    /**
     * 新增，忽略null字段
     *
     * @param friend 新增的记录
     * @return 返回影响行数
     */
    @RequestMapping("/insert")
    public int insert(@RequestBody Friend friend) {
        return friendService.insert(friend);
    }

    /**
     * 修改，忽略null字段
     *
     * @param friend 修改的记录
     * @return 返回影响行数
     */
    @RequestMapping("/update")
    public int update(@RequestBody Friend friend) {
        return friendService.update(friend);
    }

    /**
     * 删除记录
     *
     * @param friend 待删除的记录
     * @return 返回影响行数
     */
    @RequestMapping("/delete")
    public int delete(@RequestBody Friend friend) {
        return friendService.delete(friend);
    }

}