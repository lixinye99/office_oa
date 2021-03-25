package com.example.officeoa.service;

import com.example.officeoa.model.MessageInfo;

import java.util.Date;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:47 2021/3/16
 */
public interface MessageService {
    /**
     * 获取当前登录用户收到的消息信息
     * @param uid
     * @param departId
     * @return
     */
    List<MessageInfo> queryMyReceiveMessageInfo(Integer uid,Integer departId);


    /**
     * 获取全部的消息信息
     * @return
     */
    List<MessageInfo> queryAllMessageInfo();

    /**
     * 插入消息信息
     * @param messageInfo
     * @return
     */
    Boolean insertMessageInfo(MessageInfo messageInfo);

    /**
     * 获取当前登录对象推送的消息信息
     * @param uid
     * @return
     */
    List<MessageInfo> queryMyPushMessageInfo(Integer uid);

    /**
     * 根据消息的id修改消息的状态
     * @param mid
     * @return
     */
    Boolean updateMessageInfoStatus(Integer mid);

    /**
     * 根据编号查找信息
     * @param mid
     * @return
     */
    MessageInfo queryMessageInfoById(Integer mid);

    /**
     * 根据推送人或者时间来查找信息
     * @param pusher
     * @param date
     * @return
     */
    List<MessageInfo> queryMessageInfoByPusherOrDate(String pusher, Date date);
}
