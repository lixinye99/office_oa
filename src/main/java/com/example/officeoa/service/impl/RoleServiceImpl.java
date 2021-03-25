package com.example.officeoa.service.impl;

import com.example.officeoa.mapper.RoleMapper;
import com.example.officeoa.mapper.UserRoleMapper;
import com.example.officeoa.model.Role;
import com.example.officeoa.model.UserRole;
import com.example.officeoa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 13:50 2021/3/11
 */
@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public Integer queryRoleIdByRoleNameZh(String roleNameZh) {
        Role role = new Role();
        role.setRoleNameZh(roleNameZh);
        Role one = roleMapper.selectOne(role);
        return one != null ? one.getRid() : -1;
    }

    @Override
    public Boolean queryRoleNameIsExist(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        Role selectOne = roleMapper.selectOne(role);
        return selectOne != null ? true :false;
    }

    @Override
    public Boolean updateRole(Role role) {
        int count = roleMapper.updateByPrimaryKey(role);
        return count == 1 ? true : false;
    }

    @Override
    public List<Role> queryRoleByRoleNameZh(String roleNameZh) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("roleNameZh","%"+roleNameZh+"%");
        List<Role> roles = roleMapper.selectByExample(example);
        return roles;
    }

    @Override
    public Boolean deleteUserRoleByUserId(Integer uid) {
        UserRole role = new UserRole();
        role.setUid(uid);
        int count = userRoleMapper.delete(role);
        return count == 1 ? true : false;
    }

    @Override
    public Boolean queryRoleIsExistById(Integer rid) {
        Role role = roleMapper.selectByPrimaryKey(rid);
        return role != null ? true : false;
    }

    @Override
    public Integer queryUserRoleIdByUserIdAndRoleId(Integer uid, Integer rid) {
        UserRole userRole = new UserRole();
        userRole.setUid(uid);
        userRole.setRid(rid);
        UserRole one = userRoleMapper.selectOne(userRole);
        return one != null ? one.getId() : -1;
    }

    @Override
    public Boolean changeUserRoleById(UserRole userRole) {
        int count = userRoleMapper.updateByPrimaryKeySelective(userRole);
        return count == 1 ? true : false;
    }

    @Override
    public Boolean insertNewUserRole(UserRole userRole) {
        int count = userRoleMapper.insert(userRole);
        return count == 1 ? true : false;
    }

    @Override
    public List<Role> queryAllRoles() {
        List<Role> roles = roleMapper.selectAll();
        return roles;
    }

    @Override
    public boolean deleteRoleById(Integer rid) {
        int count = roleMapper.deleteByPrimaryKey(rid);
        return count == 1 ? true : false;
    }

    @Override
    public Integer insertRole(Role role) {
        int count = roleMapper.insert(role);
        return count == 1 ? role.getRid() : -1;
    }
}
