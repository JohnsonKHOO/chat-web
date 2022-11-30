package com.a2208.chat.dao;

import java.util.List;

import com.a2208.chat.entity.Friend;
import org.apache.ibatis.annotations.Mapper;
import com.a2208.chat.entity.GroupUser;

@Mapper
public interface GroupUserMapper {

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    List<GroupUser> listAll();


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    GroupUser getById(Long id);

    /**
     * 新增，插入所有字段
     *
     * @param groupUser 新增的记录
     * @return 返回影响行数
     */
    int insert(GroupUser groupUser);


    /**
     * 修改，修改所有字段
     *
     * @param groupUser 修改的记录
     * @return 返回影响行数
     */
    int update(GroupUser groupUser);


    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    int delete(Long id);

    List<GroupUser> getByGroupId(Long groupId, String account);

    GroupUser isExist(GroupUser gu);

    int deleteByBoth(Long ownerId, Long id);

    List<GroupUser> getByUserId(Long userId, String account);
}