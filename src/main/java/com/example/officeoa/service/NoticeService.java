package com.example.officeoa.service;

import com.example.officeoa.model.NoticeInfo;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 10:21 2021/3/22
 */
public interface NoticeService {

    /**
     * 插入一条新的通知信息
     * @param noticeInfo
     * @return
     */
    Boolean insertNoticeInfo(NoticeInfo noticeInfo);

    /**
     * 查询所有的通知消息
     * @return
     */
    List<NoticeInfo> queryAllNoticeInfo();

    /**
     * 根据nid列表删除公告
     * @param noticeNidList
     * @return
     */
    Boolean deleteNoticeInfoByNidList(Integer[] noticeNidList);

    /**
     * 更新公告信息
     * @param noticeInfo
     * @return
     */
    Boolean updateNoticeInfo(NoticeInfo noticeInfo);
}
