package com.example.officeoa.service.impl;

import com.example.officeoa.mapper.MessageMapper;
import com.example.officeoa.model.MessageInfo;
import com.example.officeoa.service.DepartService;
import com.example.officeoa.service.MessageService;
import com.example.officeoa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:47 2021/3/16
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartService departService;

    @Override
    public List<MessageInfo> queryMyReceiveMessageInfo(Integer uid,Integer departId) {
        //先查找推给个人的消息
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setPushObject(uid);
        messageInfo.setPushType(1);
        List<MessageInfo> selectPushPersonal = messageMapper.select(messageInfo);
        //查找推给部门的消息
        messageInfo.setPushObject(departId);
        messageInfo.setPushType(0);
        List<MessageInfo> selectPushDepart = messageMapper.select(messageInfo);

        List<MessageInfo> allPushMessage = new ArrayList<>();
        allPushMessage.addAll(selectPushPersonal);
        allPushMessage.addAll(selectPushDepart);
        return allPushMessage;
    }

    @Override
    public List<MessageInfo> queryAllMessageInfo() {
        List<MessageInfo> messageInfos = messageMapper.selectAll();
        return messageInfos;
    }

    @Override
    public Boolean insertMessageInfo(MessageInfo messageInfo) {
        int count = messageMapper.insert(messageInfo);
        return count == 1 ? true : false;
    }

    @Override
    public List<MessageInfo> queryMyPushMessageInfo(Integer uid) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setPusher(uid);
        List<MessageInfo> messageInfos = messageMapper.select(messageInfo);
        return messageInfos;
    }

    @Override
    public Boolean updateMessageInfoStatus(Integer mid) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMid(mid);
        messageInfo.setMessageStatus(1);
        int count = messageMapper.updateByPrimaryKeySelective(messageInfo);
        return count == 1 ? true : false;
    }

    @Override
    public MessageInfo queryMessageInfoById(Integer mid) {
        MessageInfo messageInfo = messageMapper.selectByPrimaryKey(mid);
        return messageInfo;
    }

    @Override
    public List<MessageInfo> queryMessageInfoByPusherOrDate(String pusher, Date date) {
        MessageInfo messageInfo = new MessageInfo();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        if(!"".equals(pusher)){
            messageInfo.setPusher(userService.queryUsernameIsExist(pusher));
        }
        if(!"".equals(date)){
            messageInfo.setPushTime(format.format(date));
        }
        List<MessageInfo> messageInfos = messageMapper.select(messageInfo);
        return messageInfos;
    }
}
