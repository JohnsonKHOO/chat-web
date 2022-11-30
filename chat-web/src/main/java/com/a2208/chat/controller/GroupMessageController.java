package com.a2208.chat.controller;

import java.io.IOException;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.a2208.chat.entity.Message;
import com.a2208.chat.utils.FileUtil;
import com.a2208.chat.utils.Result;
import com.a2208.chat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2208.chat.entity.GroupMessage;
import com.a2208.chat.service.GroupMessageService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/group/message")
public class GroupMessageController {

    @Autowired
    private GroupMessageService groupMessageService;

    /**
     * 根据用户与好友id查询和聊天内容查询
     *
     * @param fromUserId,toGroupId,content 从用户id发给好友id的消息内容
     * @return 返回记录，没有返回null
     */
    @RequestMapping("/list")
    public Result getByUserId(@RequestParam(value = "fromUserId") String fromUserId,
                              @RequestParam(value = "toGroupId") String toGroupId,
                              @RequestParam(value = "content") String content) {
        List<GroupMessage> l = groupMessageService.getByGroupId(Long.parseLong(fromUserId),
                Long.parseLong(toGroupId), content);
        System.out.println(l);
        if (!l.isEmpty())
            return ResultUtil.success(l);

        return ResultUtil.fail();
    }


    /**
     * 修改，忽略null字段
     *
     * @param groupMessage 修改的记录
     * @return 返回影响行数
     */
    @RequestMapping("/update")
    public Result update(@RequestParam(value = "groupMessage") String groupMessage,
                         @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        if (groupMessage != null) {
            JSONObject o = JSONUtil.parseObj(groupMessage);
            String img = FileUtil.uploadFile(file);
            GroupMessage gm = new GroupMessage(o.getLong("id"), o.getLong("fromUserId"),
                    o.getLong("toGroupId"), o.getStr("content"), img);

            if (groupMessageService.update(gm) != 0) {
                return ResultUtil.success("修改信息成功！");
            }
        }
        return ResultUtil.error("数据错误");
    }

    /**
     * 删除记录
     *
     * @param id 待删除的记录
     * @return 返回影响行数
     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam(value = "id") String id) {
        if (groupMessageService.delete(Long.parseLong(id)) != 0)
            return ResultUtil.success("删除好友记录" + id);

        return ResultUtil.fail();
    }

}