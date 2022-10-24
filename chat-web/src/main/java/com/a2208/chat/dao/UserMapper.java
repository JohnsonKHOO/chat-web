package com.a2208.chat.dao;

import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Mapper;
import com.a2208.chat.entity.User;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    /**
     * 查询所有用户记录
     *
     * @return 返回集合，没有返回空List
     */
    List<User> listAll();

    /**
     * 查询所有管理员记录
     *
     * @return 返回集合，没有返回空List
     */
    List<User> listAdmin();

    /**
     * 查询所有男性用户人数
     * @param sex 性别
     * @return Integer
     */
    Integer countBySex(Integer sex);

    /**
     * 查询所有男性用户人数
     * @param fromAge,toAge 性别
     * @return Integer
     */
    Integer countByAge(Integer fromAge,Integer toAge);

    /**
     * 查询所有男性用户人数
     * @param fromAge,toAge,sex 性别
     * @return Integer
     */
    Integer countByAgeSex(Integer fromAge, Integer toAge, Integer sex);

    /**
     * 查询登录记录
     * @param map 请求登录的数据
     * @return 返回记录，没有返回null
     */
    User login(Map<String, String> map);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    User getById(Long id);

    /**
     * 根据账号查询
     *
     * @param account 账号不重复
     * @return 返回记录，没有返回null
     */
    User getByAccount(String account);

    /**
     * 新增，插入所有字段
     *
     * @param user 新增的记录
     * @return 返回影响行数
     */
    int insert(User user);


    /**
     * 修改，修改所有字段
     *
     * @param user 修改的记录
     * @return 返回影响行数
     */
    int update(User user);

    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    Long delete(Long id);

}