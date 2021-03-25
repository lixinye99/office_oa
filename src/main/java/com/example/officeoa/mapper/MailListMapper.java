package com.example.officeoa.mapper;

import com.example.officeoa.model.Vo.MailInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:44 2021/3/21
 */
@Repository
@Mapper
public interface MailListMapper {

    /**
     * 查询所有的通讯录的信息
     * @return
     */
    List<MailInfoVo> queryAllMailListInfo();
}
