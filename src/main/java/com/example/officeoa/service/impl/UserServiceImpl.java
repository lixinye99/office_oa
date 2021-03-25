package com.example.officeoa.service.impl;

import com.example.officeoa.mapper.UserExtraMapper;
import com.example.officeoa.mapper.UserMapper;
import com.example.officeoa.model.Role;
import com.example.officeoa.model.UserInfo;
import com.example.officeoa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 18:02 2021/3/4
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserExtraMapper userExtraMapper;


    @Override
    public Integer queryUsernameIsExist(String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        UserInfo user = userMapper.selectOne(userInfo);
        return user != null ? user.getUid() : -1;
    }

    @Override
    public Boolean queryEmailIsExist(String email) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        UserInfo user = userMapper.selectOne(userInfo);
        return user != null ? true : false;
    }

    @Override
    public List<UserInfo> queryAllUserInfo() {
        List<UserInfo> userInfos = userMapper.selectAll();
        return userInfos;
    }

    @Override
    public List<UserInfo> queryUserInfoByDepartId(Integer id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setDepartId(id);
        List<UserInfo> userInfos = userMapper.select(userInfo);
        return userInfos;
    }

    @Override
    public Integer insertUser(UserInfo userInfo) {
        int count = userMapper.insert(userInfo);
        return count != 0 ? userInfo.getUid() : -1;
    }

    @Override
    public Boolean updateUserStatusByUserId(Integer id) {
        UserInfo beforeQuery = new UserInfo();
        beforeQuery.setUid(id);
        UserInfo userInfo = userMapper.selectOne(beforeQuery);
        userInfo.setEnlock(userInfo.getEnlock()==0?1:0);
        int count = userMapper.updateByPrimaryKey(userInfo);
        return count == 1 ? true : false;
    }

    @Override
    public Boolean deleteUserByUserId(Integer id) {
        int count = userMapper.deleteByPrimaryKey(id);
        return count == 1 ? true : false;

    }

    @Override
    public UserInfo queryUserInfoByUserId(Integer uid) {
        UserInfo userInfo = userMapper.selectByPrimaryKey(uid);
        return userInfo;
    }

    @Override
    public boolean updateUserInfoByUserId(UserInfo userInfo) {
        int count = userMapper.updateByPrimaryKeySelective(userInfo);
        return count == 1 ? true : false;
    }

    @Override
    public boolean queryHasUserInDepartByDepartId(Integer departId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setDepartId(departId);
        List<UserInfo> select = userMapper.select(userInfo);
        return select.size() != 0 ? true : false;
    }

    @Override
    public List<String> getManagerUserList() {
        List<String> managerUserList = userExtraMapper.getManagerUserList();
        return managerUserList;
    }

    @Override
    public List<Role> getUserRolesByUserId(Integer id) {
        List<Role> userRoles = userExtraMapper.getUserRolesByUserId(id);
        return userRoles;
    }


}
