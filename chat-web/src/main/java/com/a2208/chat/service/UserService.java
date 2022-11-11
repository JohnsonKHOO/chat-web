package com.a2208.chat.service;

import com.a2208.chat.entity.User;
import com.a2208.chat.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<User> listAll() { return userMapper.listAll(); }

    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    public List<User> listAdmin() { return userMapper.listAdmin(); }

    /**
     * 查询用户性别人数占比
     * @param sex 性别
     * @return Integer
     */
    public Integer countBySex(Integer sex){return userMapper.countBySex(sex);}

    /**
     * 查询年龄段用户人数
     * @param fromAge,toAge,sex 年龄段
     * @return Integer
     */
    public Integer countByAgeSex(Integer fromAge,Integer toAge,Integer sex){return userMapper.countByAgeSex(fromAge, toAge, sex);}

    /**
     * 查询所有记录
     * @param loginInfo 登录信息
     * @return 返回集合，没有返回空List
     */
    public User login(Map<String, String> loginInfo){ return userMapper.login(loginInfo); }

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    public User getById(Long id) {
        return userMapper.getById(id);
    }

    /**
     * 根据账号查询
     *
     * @param account 账号
     * @return 返回记录，没有返回null
     */
    public List<User> getByAccount(String account, Integer roleId) {
        return userMapper.getByAccount(account, roleId);
    }

    public User isExist(String account){
        return userMapper.isExist(account);
    }
    /**
     * 新增，插入所有字段
     *
     * @param user 新增的记录
     * @return 返回影响行数
     */
    public int insert(User user) {
        return userMapper.insert(user);
    }

    /**
     * 修改，修改所有字段
     *
     * @param user 修改的记录
     * @return 返回影响行数
     */
    public int update(User user) {
        return userMapper.update(user);
    }

    public int updateStateId(Map<String, Long> map){
        return userMapper.updateStateId(map);
    }
    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    public Long delete(Long id) {
        return userMapper.delete(id);
    }

}