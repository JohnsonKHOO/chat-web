package com.a2208.chat.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.a2208.chat.entity.Message;

@Mapper
public interface MessageMapper {

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    List<Message> listAll();


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    Message getById(Long id);

    /**
     * 新增，插入所有字段
     *
     * @param message 新增的记录
     * @return 返回影响行数
     */
    int insert(Message message);


    /**
     * 修改，修改所有字段
     *
     * @param message 修改的记录
     * @return 返回影响行数
     */
    int update(Message message);


    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    int delete(Long id);

    List<Message> getByUserId(Long fromUserId, Long toUserId,String content);
}