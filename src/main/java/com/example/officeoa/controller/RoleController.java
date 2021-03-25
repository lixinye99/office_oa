package com.example.officeoa.controller;

import com.example.officeoa.model.Role;
import com.example.officeoa.service.RoleService;
import com.example.officeoa.utils.JsonResult;
import com.example.officeoa.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 18:13 2021/3/12
 */
@RestController
public class RoleController {
    @Autowired
    RoleService roleService;


    /**
     * 插入新的角色信息
     * @param role
     * @return
     */
    @PostMapping("/admin/insertRole")
    public JsonResult insertRole(Role role){
        if(!roleService.queryRoleNameIsExist(role.getRoleName())){
            Integer newRoleId = roleService.insertRole(role);
            return newRoleId != -1 ? JsonResult.success() : JsonResult.failure(ResultCode.SAVE_ERROR);
        }
        return JsonResult.failure(ResultCode.ROLENAME_IS_EXIST);
    }

    /**
     * 获取所有的角色信息
     * @return
     */
    @GetMapping("/admin/getAllRole")
    public JsonResult getAllRole(){
        List<Role> roles = roleService.queryAllRoles();
        return roles.size() != 0 ? JsonResult.success(roles) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    /**
     * 根据id删除角色信息
     * @param rid
     * @return
     */
    @DeleteMapping("/admin/deleteRole/{rid}")
    public JsonResult deleteRoleById(@PathVariable Integer rid){
        if(roleService.queryRoleIsExistById(rid)){
            boolean flag = roleService.deleteRoleById(rid);
            return flag == true ? JsonResult.success() : JsonResult.failure(ResultCode.DELETE_ERROR);
        }
        return JsonResult.failure(ResultCode.ROLEID_NOTFOUND);
    }

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    @PutMapping("/admin/updateRole")
    public JsonResult updateRole(Role role){
        boolean flag = roleService.updateRole(role);
        return flag == true ? JsonResult.success() : JsonResult.failure(ResultCode.UPDATE_ERROR);
    }

    /**
     * 根据条件查询角色信息列表
     * @param roleNameZh
     * @return
     */
    @GetMapping("/admin/queryRoleByRoleNameZh")
    public JsonResult queryRoleByRoleNameZh(String roleNameZh){
        List<Role> roleList =  roleService.queryRoleByRoleNameZh(roleNameZh);
        return roleList.size() != 0 ? JsonResult.success(roleList) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }

}
