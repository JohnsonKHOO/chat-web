package com.a2208.chat.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.a2208.chat.entity.Group;

@Mapper
public interface GroupMapper {

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    List<Group> listAll();


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    Group getById(Integer id);

    /**
     * 新增，插入所有字段
     *
     * @param group 新增的记录
     * @return 返回影响行数
     */
    int insert(Group group);


    /**
     * 修改，修改所有字段
     *
     * @param group 修改的记录
     * @return 返回影响行数
     */
    int update(Group group);


    /**
     * 删除记录
     *
     * @param group 待删除的记录
     * @return 返回影响行数
     */
    int delete(Group group);

}