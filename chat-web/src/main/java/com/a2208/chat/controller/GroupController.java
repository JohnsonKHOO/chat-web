package com.a2208.chat.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.a2208.chat.entity.Group;
import com.a2208.chat.entity.GroupUser;
import com.a2208.chat.service.GroupService;
import com.a2208.chat.service.GroupUserService;
import com.a2208.chat.service.UserService;
import com.a2208.chat.utils.FileUtil;
import com.a2208.chat.utils.Result;
import com.a2208.chat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private UserService userService;
    /**
     * 查询所有记录
     *
     * @return 返回集合，没有返回空List
     */
    @RequestMapping("/list")
    public Result listAll() {
        List<Group> l = groupService.listAll();
        if(l != null){
            return ResultUtil.success(l);
        }
        else
            return ResultUtil.fail();
    }


    @RequestMapping("/search")
    public Result getByUserId(@RequestParam(value = "name")String name) {
        List<Group> l = groupService.getByName(name);
        System.out.println(l);
        if(!l.isEmpty())
            return ResultUtil.success(l);

        return ResultUtil.error(name);
    }
    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/info")
    public Result searchById(@RequestParam(value = "id")String id) {
        System.out.println(id);
        Group group = groupService.getById(Long.parseLong(id));
        if(group != null)
            return ResultUtil.success(group);

        return ResultUtil.fail();
    }

    /**
     * 新增，忽略null字段
     *
     * @param group 新增的记录
     * @return 返回影响行数
     */
    @RequestMapping("/insert")
    public Result insert(@RequestParam(value = "group")String group,
                         @RequestParam(value = "file", required = false) MultipartFile file
    ) throws ParseException, IOException {
        if(group != null){
            JSONObject o = JSONUtil.parseObj(group);
            String avatar = FileUtil.uploadFile(file);
            Long ownerId = o.getLong("ownerId");
            Group g = new Group(null, o.getStr("name"),
                    ownerId, avatar, o.getStr("signature"));

            if(groupService.insert(g) != 0){
                //群组创建后会自动返回id
                GroupUser gu = new GroupUser(null,ownerId, g.getId(),
                        userService.getById(ownerId).getNickname() + "(群主)");
                groupUserService.insert(gu);
                return ResultUtil.success("新建群组成功！");
            }

        }
        return ResultUtil.error("数据错误！");
    }
    @RequestMapping(value = "/updatesignature")
    public Result updateSignature(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "signature") String signature) {
        if(id != null && signature != null){
            if(groupService.updateSignature(id, signature) != 0){
                return ResultUtil.success("更新密码成功");
            }

        }
        return ResultUtil.fail();
    }

    @RequestMapping(value = "/updatenotice")
    public Result updateNotice(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "notice") String notice) {
        if(id != null && notice != null){
            if(groupService.updateNotice(id, notice) != 0){
                return ResultUtil.success("更新密码成功");
            }
        }
        return ResultUtil.fail();
    }
    /**
     * 修改，忽略null字段
     *
     * @param group 修改的记录
     * @return 返回影响行数
     */
    @RequestMapping("/update")
    public Result update(@RequestParam(value = "group")String group){
        if(group != null){
            JSONObject o = JSONUtil.parseObj(group);
            Long id = o.getLong("id");
            Long ownerId = o.getLong("ownerId");
            Group g = new Group(id, o.getStr("name"),
                    ownerId, null, null);

            if(userService.getById(ownerId) != null) {
                //删除原先owner的groupuser
                if(groupUserService.deleteByBoth(ownerId, id)!=0 && groupService.update(g) != 0){
                    GroupUser gu = new GroupUser(null,ownerId, g.getId(),
                            userService.getById(ownerId).getNickname()+"(群主)");
                    groupUserService.insert(gu);
                    return ResultUtil.success("更新群组成功！");
                }
            }
        }
        return ResultUtil.error(group);
    }

    @RequestMapping(value = "/updateavatar")
    public Result updateAvatar(@RequestParam(value = "id") String id,
                               @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        if(id != null && file != null){
            String avatar = FileUtil.uploadFile(file);
            if(groupService.updateAvatar(id, avatar) != 0){
                return ResultUtil.success(groupService.getById(Long.parseLong(id)));
            }
        }
        return ResultUtil.fail();
    }

    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam(value="id")String id) {
        if(groupService.delete(Long.parseLong(id)) != 0)
            return ResultUtil.success("删除好友记录" + id);

        return ResultUtil.fail();
    }

}