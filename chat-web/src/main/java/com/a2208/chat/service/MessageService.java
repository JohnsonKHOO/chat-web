package com.a2208.chat.service;

import com.a2208.chat.entity.GroupUser;
import com.a2208.chat.entity.Message;
import com.a2208.chat.dao.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<Message> listAll() {
        return messageMapper.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    public Message getById(Long id) {
        return messageMapper.getById(id);
    }

    /**
     * 新增，插入所有字段
     *
     * @param message 新增的记录
     * @return 返回影响行数
     */
    public int insert(Message message) {
        return messageMapper.insert(message);
    }

    /**
     * 修改，修改所有字段
     *
     * @param message 修改的记录
     * @return 返回影响行数
     */
    public int update(Message message) {
        return messageMapper.update(message);
    }

    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Long id) {
        return messageMapper.delete(id);
    }

    public List<Message> getByUserId(Long fromUserId,Long toUserId, String content) {
        return messageMapper.getByUserId(fromUserId, toUserId, content);
    }
}