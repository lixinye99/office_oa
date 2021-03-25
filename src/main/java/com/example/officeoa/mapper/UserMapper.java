package com.example.officeoa.mapper;

import com.example.officeoa.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 16:59 2021/3/7
 */
@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<UserInfo> {

}
