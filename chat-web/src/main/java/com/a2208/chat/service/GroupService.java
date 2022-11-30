package com.a2208.chat.service;

import com.a2208.chat.entity.Friend;
import com.a2208.chat.entity.Group;
import com.a2208.chat.dao.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupMapper groupMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<Group> listAll() {
        return groupMapper.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    public Group getById(Long id) {
        return groupMapper.getById(id);
    }

    /**
     * 新增，插入所有字段
     *
     * @param group 新增的记录
     * @return 返回影响行数
     */
    public int insert(Group group) {
        return groupMapper.insert(group);
    }


    /**
     * 修改，修改所有字段
     *
     * @param group 修改的记录
     * @return 返回影响行数
     */
    public int update(Group group) {
        return groupMapper.update(group);
    }

    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Long id) {
        return groupMapper.delete(id);
    }

    public List<Group> getByName(String name) {
        return groupMapper.getByName(name);
    }

    public int updateSignature(String id, String signature) {
        return groupMapper.updateSignature(id, signature);
    }

    public int updateNotice(String id, String notice) {
        return groupMapper.updateNotice(id, notice);
    }

    public int updateAvatar(String id, String avatar) {
        return groupMapper.updateAvatar(id, avatar);
    }
}