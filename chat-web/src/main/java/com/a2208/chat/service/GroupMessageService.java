package com.a2208.chat.service;

import com.a2208.chat.entity.GroupMessage;
import com.a2208.chat.dao.GroupMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMessageService {

    @Autowired
    private GroupMessageMapper groupMessageMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<GroupMessage> listAll() {
        return groupMessageMapper.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    public GroupMessage getById(Long id) {
        return groupMessageMapper.getById(id);
    }

    /**
     * 新增，插入所有字段
     *
     * @param groupMessage 新增的记录
     * @return 返回影响行数
     */
    public int insert(GroupMessage groupMessage) {
        return groupMessageMapper.insert(groupMessage);
    }


    /**
     * 修改，修改所有字段
     *
     * @param groupMessage 修改的记录
     * @return 返回影响行数
     */
    public int update(GroupMessage groupMessage) {
        return groupMessageMapper.update(groupMessage);
    }


    /**
     * 删除记录
     *
     * @param groupMessage 待删除的记录
     * @return 返回影响行数
     */
    public int delete(GroupMessage groupMessage) {
        return groupMessageMapper.delete(groupMessage);
    }

}