package com.example.officeoa.service.impl;

import com.example.officeoa.mapper.MailListMapper;
import com.example.officeoa.model.Vo.MailInfoVo;
import com.example.officeoa.service.MailListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:15 2021/3/21
 */
@Service
public class MailListServiceImpl implements MailListService {

    @Autowired
    private MailListMapper mailListMapper;

    @Override
    public List<MailInfoVo> queryAllMailListInfo() {
        List<MailInfoVo> mailInfoVos = mailListMapper.queryAllMailListInfo();
        return mailInfoVos;
    }
}
