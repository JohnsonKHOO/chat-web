package com.a2208.chat.service;

import com.a2208.chat.entity.Friend;
import com.a2208.chat.entity.GroupUser;
import com.a2208.chat.dao.GroupUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupUserService {

    @Autowired
    private GroupUserMapper groupUserMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<GroupUser> listAll() {
        return groupUserMapper.listAll();
    }


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    public GroupUser getById(Long id) {
        return groupUserMapper.getById(id);
    }

    /**
     * 新增，插入所有字段
     *
     * @param groupUser 新增的记录
     * @return 返回影响行数
     */
    public int insert(GroupUser groupUser) {
        return groupUserMapper.insert(groupUser);
    }


    /**
     * 修改，修改所有字段
     *
     * @param groupUser 修改的记录
     * @return 返回影响行数
     */
    public int update(GroupUser groupUser) {
        return groupUserMapper.update(groupUser);
    }

    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    public int delete(Long id) {
        return groupUserMapper.delete(id);
    }

    public List<GroupUser> getByGroupId(Long groupId, String account) {
        return groupUserMapper.getByGroupId(groupId, account);
    }

    public GroupUser isExist(GroupUser gu) {
        return groupUserMapper.isExist(gu);
    }

    public int deleteByBoth(Long ownerId, Long id) {
        return groupUserMapper.deleteByBoth(ownerId,id);
    }

    public List<GroupUser> getByUserId(Long userId, String account) {
        return groupUserMapper.getByUserId(userId,account);
    }
}