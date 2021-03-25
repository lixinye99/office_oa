package com.example.officeoa.service.impl;

import com.example.officeoa.mapper.UserExtraMapper;
import com.example.officeoa.mapper.UserMapper;
import com.example.officeoa.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 21:58 2021/3/7
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtraMapper userExtraMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo inputUser = new UserInfo();
        inputUser.setUsername(username);
        UserInfo userInfo= userMapper.selectOne(inputUser);
        if(null == userInfo){
            throw new UsernameNotFoundException("用户不存在");
        }
        userInfo.setRoles(userExtraMapper.getUserRolesByUserId(userInfo.getUid()));
        return new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getAuthorities());
    }
}
