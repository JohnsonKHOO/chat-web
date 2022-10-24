package com.a2208.chat.service;

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
    public Group getById(Integer id) {
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
     * @param group 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Group group) {
        return groupMapper.delete(group);
    }

}