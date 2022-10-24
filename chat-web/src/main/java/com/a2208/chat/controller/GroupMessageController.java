package com.a2208.chat.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2208.chat.entity.GroupMessage;
import com.a2208.chat.service.GroupMessageService;

@RestController
@RequestMapping("/group/message")
public class GroupMessageController {

    @Autowired
    private GroupMessageService groupMessageService;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    @RequestMapping("/list")
    public List<GroupMessage> listAll() {
        return groupMessageService.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/getById")
    public GroupMessage getById(Long id) {
        return groupMessageService.getById(id);
    }

    /**
     * 新增，忽略null字段
     *
     * @param groupMessage 新增的记录
     * @return 返回影响行数
     */
    @RequestMapping("/insert")
    public int insert(@RequestBody GroupMessage groupMessage) {
        return groupMessageService.insert(groupMessage);
    }

    /**
     * 修改，忽略null字段
     *
     * @param groupMessage 修改的记录
     * @return 返回影响行数
     */
    @RequestMapping("/update")
    public int update(@RequestBody GroupMessage groupMessage) {
        return groupMessageService.update(groupMessage);
    }

    /**
     * 删除记录
     *
     * @param groupMessage 待删除的记录
     * @return 返回影响行数
     */
    @RequestMapping("/delete")
    public int delete(@RequestBody GroupMessage groupMessage) {
        return groupMessageService.delete(groupMessage);
    }

}