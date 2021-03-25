package com.example.officeoa.mapper;

import com.example.officeoa.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 16:32 2021/3/8
 */
@Repository
@Mapper
public interface UserExtraMapper {

    /**
     * 根据用户的id读取用户的权限集合
     * @param userId
     * @return
     */
    List<Role> getUserRolesByUserId(@Param("userId") Integer userId);

    /**
     * 查找拥有部门管理员权限的用户
     * @return
     */
    List<String> getManagerUserList();
}
