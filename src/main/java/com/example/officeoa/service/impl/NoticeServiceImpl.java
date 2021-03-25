package com.example.officeoa.service.impl;

import com.example.officeoa.mapper.NoticeMapper;
import com.example.officeoa.model.NoticeInfo;
import com.example.officeoa.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 10:22 2021/3/22
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Boolean insertNoticeInfo(NoticeInfo noticeInfo) {
        int count = noticeMapper.insert(noticeInfo);
        return count == 1 ? true : false;
    }

    @Override
    public List<NoticeInfo> queryAllNoticeInfo() {
        List<NoticeInfo> noticeInfoList = noticeMapper.selectAll();
        return noticeInfoList;
    }

    @Override
    public Boolean deleteNoticeInfoByNidList(Integer[] noticeNidList) {
        Example example = new Example(NoticeInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("nid", Arrays.asList(noticeNidList));
        int count = noticeMapper.deleteByExample(example);
        return count != 0 ? true : false;
    }

    @Override
    public Boolean updateNoticeInfo(NoticeInfo noticeInfo) {
        int count = noticeMapper.updateByPrimaryKeySelective(noticeInfo);
        return count == 1 ? true : false;
    }
}
