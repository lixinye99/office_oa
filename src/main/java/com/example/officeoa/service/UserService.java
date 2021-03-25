package com.example.officeoa.service;

import com.example.officeoa.model.Role;
import com.example.officeoa.model.UserInfo;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 18:02 2021/3/4
 */
public interface UserService {

    /**
     * 查询用户是不是已经存在了,存在返回用户的id
     * @param username
     * @return
     */
    Integer queryUsernameIsExist(String username);

    /**
     * 查询邮箱是否已经被使用了
     * @param email
     * @return
     */
    Boolean queryEmailIsExist(String email);

    /**
     * 查询所有的user信息
     * @return
     */
    List<UserInfo> queryAllUserInfo();

    /**
     * 根据部门id，查找该部门下的员工
     * @param id
     * @return
     */
    List<UserInfo> queryUserInfoByDepartId(Integer id);


    /**
     * 插入一个新的用户
     * @param userInfo
     * @return
     */
    Integer insertUser(UserInfo userInfo);

    /**
     * 改变用户的锁定状态
     * @param id
     * @return
     */
    Boolean updateUserStatusByUserId(Integer id);

    /**
     * 通过用户的id删除用户
     * @param id
     * @return
     */
    Boolean deleteUserByUserId(Integer id);

    /**
     * 根据用户的id查询用户的信息
     * @param uid
     * @return
     */
    UserInfo queryUserInfoByUserId(Integer uid);

    /**
     * 根据用户的id对用户的信息进行更改
     * @return
     * @param userInfo
     */
    boolean updateUserInfoByUserId(UserInfo userInfo);

    /**
     * 查询是否有用户处于输入的部门编号对应的部门下
     * @param departId
     * @return
     */
    boolean queryHasUserInDepartByDepartId(Integer departId);

    /**
     * 获取所有部门管理员的用户
     * @return
     */
    List<String> getManagerUserList();

    /**
     * 获取用户的所有权限信息
     * @return
     */
    List<Role> getUserRolesByUserId(Integer id);
}
