package com.example.officeoa.service;

import com.example.officeoa.model.Role;
import com.example.officeoa.model.UserRole;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 13:48 2021/3/11
 */
public interface RoleService {

    /**
     * 根据权限名称查找权限id
     * @param roleNameZh
     * @return
     */
    Integer queryRoleIdByRoleNameZh(String roleNameZh);

    /**
     * 检查权限的名称是否已经被使用
     * @param roleName
     * @return
     */
    Boolean queryRoleNameIsExist(String roleName);

    /**
     * 根据id查询权限是否存在
     * @param rid
     * @return
     */
    Boolean queryRoleIsExistById(Integer rid);

    /**
     * 根据userid和roleid查找user_role表中存储的用户权限id
     * @param uid
     * @param rid
     * @return
     */
    Integer queryUserRoleIdByUserIdAndRoleId(Integer uid,Integer rid);


    /**
     * 根据user_role表中的主键进行修改用户的权限
     * @param userRole
     * @return
     */
    Boolean changeUserRoleById(UserRole userRole);

    /**
     * 插入新的用户——权限
     * @param userRole
     * @return
     */
    Boolean insertNewUserRole(UserRole userRole);

    /**
     * 查找所有的role
     * @return
     */
    List<Role> queryAllRoles();

    /**
     * 根据id删除权限
     * @param rid
     * @return
     */
    boolean deleteRoleById(Integer rid);

    /**
     * 插入新的角色信息
     * @param role
     * @return
     */
    Integer insertRole(Role role);

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    Boolean updateRole(Role role);

    /**
     * 根据查询的条件查询角色信息
     * @param roleNameZh
     * @return
     */
    List<Role> queryRoleByRoleNameZh(String roleNameZh);

    /**
     * 根据用户的id删除用户的权限信息
     * @param uid
     * @return
     */
    Boolean deleteUserRoleByUserId(Integer uid);
}
