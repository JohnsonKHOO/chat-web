package com.a2208.chat.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2208.chat.entity.GroupUser;
import com.a2208.chat.service.GroupUserService;

@RestController
@RequestMapping("/group/user")
public class GroupUserController {

    @Autowired
    private GroupUserService groupUserService;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    @RequestMapping("/list")
    public List<GroupUser> listAll() {
        return groupUserService.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/getById")
    public GroupUser getById(Long id) {
        return groupUserService.getById(id);
    }

    /**
     * 新增，忽略null字段
     *
     * @param groupUser 新增的记录
     * @return 返回影响行数
     */
    @RequestMapping("/insert")
    public int insert(@RequestBody GroupUser groupUser) {
        return groupUserService.insert(groupUser);
    }

    /**
     * 修改，忽略null字段
     *
     * @param groupUser 修改的记录
     * @return 返回影响行数
     */
    @RequestMapping("/update")
    public int update(@RequestBody GroupUser groupUser) {
        return groupUserService.update(groupUser);
    }

    /**
     * 删除记录
     *
     * @param groupUser 待删除的记录
     * @return 返回影响行数
     */
    @RequestMapping("/delete")
    public int delete(@RequestBody GroupUser groupUser) {
        return groupUserService.delete(groupUser);
    }

}