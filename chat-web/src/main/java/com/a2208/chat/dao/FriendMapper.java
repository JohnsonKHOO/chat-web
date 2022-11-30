package com.a2208.chat.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.a2208.chat.entity.Friend;

@Mapper
public interface FriendMapper {

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    List<Friend> listAll();


    /**
     *
     *
     *
     * @return 返回记录，没有返回null
     */
    List<Friend> getByUserId(Long userId, String account);

    /**
     * 新增，插入所有字段
     *
     * @param friend 新增的记录
     * @return 返回影响行数
     */
    int insert(Friend friend);

    Friend getById(Long id);

    /**
     * 修改，修改所有字段
     *
     * @param friend 修改的记录
     * @return 返回影响行数
     */
    int update(Friend friend);

    /**
     * 删除记录
     *
     * @param friend 待删除的记录
     * @return 返回影响行数
     */
    int delete(Friend friend);

    Friend isExist(Friend friend);
}