package com.a2208.chat.service;

import com.a2208.chat.entity.Friend;
import com.a2208.chat.dao.FriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    @Autowired
    private FriendMapper friendMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<Friend> listAll() {
        return friendMapper.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    public Friend getById(Long id) {
        return friendMapper.getById(id);
    }

    /**
     * 新增，插入所有字段
     *
     * @param friend 新增的记录
     * @return 返回影响行数
     */
    public int insert(Friend friend) {
        return friendMapper.insert(friend);
    }


    /**
     * 修改，修改所有字段
     *
     * @param friend 修改的记录
     * @return 返回影响行数
     */
    public int update(Friend friend) {
        return friendMapper.update(friend);
    }


    /**
     * 删除记录
     *
     * @param friend 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Friend friend) {
        return friendMapper.delete(friend);
    }

}