package com.a2208.chat.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2208.chat.entity.Message;
import com.a2208.chat.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    @RequestMapping("/list")
    public List<Message> listAll() {
        return messageService.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/getById")
    public Message getById(Long id) {
        return messageService.getById(id);
    }

    /**
     * 新增，忽略null字段
     *
     * @param message 新增的记录
     * @return 返回影响行数
     */
    @RequestMapping("/insert")
    public int insert(@RequestBody Message message) {
        return messageService.insert(message);
    }

    /**
     * 修改，忽略null字段
     *
     * @param message 修改的记录
     * @return 返回影响行数
     */
    @RequestMapping("/update")
    public int update(@RequestBody Message message) {
        return messageService.update(message);
    }

    /**
     * 删除记录
     *
     * @param message 待删除的记录
     * @return 返回影响行数
     */
    @RequestMapping("/delete")
    public int delete(@RequestBody Message message) {
        return messageService.delete(message);
    }

}