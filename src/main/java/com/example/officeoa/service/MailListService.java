package com.example.officeoa.service;

import com.example.officeoa.model.Vo.MailInfoVo;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:15 2021/3/21
 */
public interface MailListService {

    /**
     * 查找所有的通讯录信息
     * @return
     */
    List<MailInfoVo> queryAllMailListInfo();
}
