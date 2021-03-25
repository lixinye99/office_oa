package com.example.officeoa.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.officeoa.model.Bo.UserInfoBo;
import com.example.officeoa.model.DepartInfo;
import com.example.officeoa.model.Role;
import com.example.officeoa.model.UserInfo;
import com.example.officeoa.model.UserRole;
import com.example.officeoa.model.Vo.DepartInfoVo;
import com.example.officeoa.model.Vo.UserInfoVo;
import com.example.officeoa.service.DepartService;
import com.example.officeoa.service.RoleService;
import com.example.officeoa.service.UserService;
import com.example.officeoa.utils.JsonResult;
import com.example.officeoa.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 15:22 2021/3/9
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartService departService;

    @Autowired
    private RoleService roleService;


    /**
     * 权限判断是否是管理员
     * @return
     */
    public static boolean hasAdminRole(){
        //获取登录用户的权限，只有用ADMIN权限的用户才可以增删改查用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean role_admin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return role_admin;
    }

    /**
     * 判断权限是不是部门管理员
     * @return
     */
    public static boolean hasManagerRole(){
        //获取登录用户的权限，部门管理员只能查看自己部门下的员工
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean manager_admin = authorities.contains(new SimpleGrantedAuthority("ROLE_MANAGER"));
        return manager_admin;
    }

    /**
     * 插入用户逻辑
     * @param userInfoBo
     * @return
     */
    @PostMapping("/admin/insertUser")
    public JsonResult insertUser(UserInfoBo userInfoBo){
        if(!hasAdminRole()) {
            return JsonResult.failure(ResultCode.PERMISSION_NO_ACCESS);
        }

        //判断用户是否已经注册过，用户已存在返回错误
        Integer integer = userService.queryUsernameIsExist(userInfoBo.getUsername());
        if(integer != -1) {
            return JsonResult.failure(ResultCode.USER_HAS_EXISTED);
        }

        //判断用户的email是否已经使用
        Boolean emailIsExist = userService.queryEmailIsExist(userInfoBo.getEmail());
        if(emailIsExist) {
            return JsonResult.failure(ResultCode.EMAIL_IS_EXIST);
        }

        //根据输入的部门名称，查找对应的id，并把vo类转为pojo进行插入数据库
        //没有部门，返回部门不存在错误
        Integer departId = departService.queryDepartIdByName(userInfoBo.getDepartName());
        //根据输入的权限信息进行权限表的插入
        Integer roleId = roleService.queryRoleIdByRoleNameZh(userInfoBo.getRole());
        if(departId != -1 && roleId != -1){
            //插入用户的基本信息
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(userInfoBo.getUsername());
            userInfo.setDepartId(departId);
            userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfoBo.getPassword()));
            userInfo.setCreateTime(new Date());
            userInfo.setEmail(userInfoBo.getEmail());
            userInfo.setRemark(userInfoBo.getRemark());
            userInfo.setEnlock(0);
            Integer uid = userService.insertUser(userInfo);
            //用返回的用户id，来进行权限的添加
            if(uid == -1) {
                return JsonResult.failure(ResultCode.SAVE_ERROR);
            }else{
                UserRole userRole = new UserRole();
                userRole.setUid(uid);
                userRole.setRid(roleId);
                Boolean insertUserRole = roleService.insertNewUserRole(userRole);
                return insertUserRole == true ? JsonResult.success() : JsonResult.failure(ResultCode.SAVE_ERROR);
            }

        }
        return JsonResult.failure(ResultCode.DEPARTID_NOTFOUND);
    }

    /**
     * 初始化页面查找所有用户
     * @return
     */
    @GetMapping("/getAllUserInfo")
    public JsonResult queryAllUserInfo(Principal principal){

        List<UserInfo> userInfos = new ArrayList<>();
        //管理员可以看到所有的用户，其他用户只能看到同部门下的员工
        if(hasAdminRole()){
            userInfos = userService.queryAllUserInfo();
        }else{
            Integer userId = userService.queryUsernameIsExist(principal.getName());
            UserInfo userInfo = userService.queryUserInfoByUserId(userId);
            userInfos = userService.queryUserInfoByDepartId(userInfo.getDepartId());
        }

        if(userInfos.size() < 1) {
            return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        }
        List<UserInfoVo> infoVoList = new ArrayList<>();
        UserInfoVo userInfoVo;
        for(UserInfo u : userInfos){
            userInfoVo = (UserInfoVo) changeUserPattern(u);
            infoVoList.add(userInfoVo);
        }
        return JsonResult.success(infoVoList);
    }

    /**
     * 根据url上的id锁定用户
     * @param uid
     * @return
     */
    @PutMapping("/admin/lockUser/{uid}")
    public JsonResult lockUserByUserId(@PathVariable Integer uid){
        if(!hasAdminRole()) {
            return JsonResult.failure(ResultCode.PERMISSION_NO_ACCESS);
        }
        Boolean flag = userService.updateUserStatusByUserId(uid);
        return flag ? JsonResult.success() : JsonResult.failure(ResultCode.UPDATE_ERROR);
    }

    /**
     * 根据用户的id对用户信息进行更改
     * @param uid
     * @param userInfoBo
     * @return
     */
    @PutMapping("/admin/updateUser/{uid}")
    public JsonResult updateUserByUserId(@PathVariable Integer uid,UserInfoBo userInfoBo){
        if(!hasAdminRole()) {
            return JsonResult.failure(ResultCode.PERMISSION_NO_ACCESS);
        }
        //判断用户新输入的邮箱是否已经使用
        Boolean emailIsExist = userService.queryEmailIsExist(userInfoBo.getEmail());
        if(emailIsExist)
            return JsonResult.failure(ResultCode.EMAIL_IS_EXIST);

        //用户基本信息的修改
        UserInfo userInfo = (UserInfo) changeUserPattern(userInfoBo, uid);
        boolean infoUpdateFlag = userService.updateUserInfoByUserId(userInfo);
        //进行权限的修改
        //获取要更改的权限id
        Integer newRoleId = roleService.queryRoleIdByRoleNameZh(userInfoBo.getRole());
        //获取旧的权限id
        List<Role> oldRoles = userService.getUserRolesByUserId(uid);
        //查找user_role的主键id，用户后面的根据主键进行修改
        Integer userRoleId = roleService.queryUserRoleIdByUserIdAndRoleId(uid, oldRoles.get(0).getRid());
        //修改user_role表中的用户权限
        UserRole userRole = new UserRole();
        userRole.setId(userRoleId);
        userRole.setUid(uid);
        userRole.setRid(newRoleId);
        boolean roleUpdateFlag = roleService.changeUserRoleById(userRole);
        return infoUpdateFlag == roleUpdateFlag == true ? JsonResult.success() : JsonResult.failure(ResultCode.UPDATE_ERROR);
    }

    /**
     * 根据用户id删除用户
     * @param uid
     * @return
     */
    @DeleteMapping("/admin/deleteUser/{uid}")
    public JsonResult deleteUserByUserId(@PathVariable Integer uid){
        if(!hasAdminRole()) {
            return JsonResult.failure(ResultCode.PERMISSION_NO_ACCESS);
        }
        //如果用户为某一部门的管理员不能直接删除
        List<String> managerUserList = userService.getManagerUserList();
        UserInfo userInfo = userService.queryUserInfoByUserId(uid);
        if(managerUserList.contains(userInfo.getUsername())){
            return JsonResult.failure(ResultCode.USER_IS_MANAGER_DEPART);
        }

        //删除用户的基本信息
        Boolean deleteUserInfo = userService.deleteUserByUserId(uid);

        //删除用户对应的权限信息
        Boolean deleteUserRole = roleService.deleteUserRoleByUserId(uid);
        return deleteUserInfo == deleteUserRole == true ? JsonResult.success() : JsonResult.failure(ResultCode.DELETE_ERROR);
    }

    /**
     * 修改信息之前查询信息进行页面渲染
     * @param uid
     * @return
     */
    @GetMapping("/admin/readyForUpdate/{uid}")
    public JsonResult queryUserInfoBeforeUpdate(@PathVariable Integer uid){
        if(!hasAdminRole()) {
            return JsonResult.failure(ResultCode.PERMISSION_NO_ACCESS);
        }
        UserInfo userInfo = userService.queryUserInfoByUserId(uid);
        if(userInfo != null)
        {
            UserInfoVo userInfoVo = (UserInfoVo) changeUserPattern(userInfo);
            String detail = departService.queryDetailByDepartId(departService.queryDepartIdByName(userInfoVo.getDepartName()));
            userInfoVo.setDepartName(detail);
            return JsonResult.success(userInfoVo);
        }
        return JsonResult.failure(ResultCode.QUERY_ERROR);
    }

    /**
     * 获取所有的权限名和部门名称
     * @return
     */
    @GetMapping("/getRolesAndDepart")
    public JsonResult queryRoleAndDepartList(){
        List<String> roleNameZhList = roleService.queryAllRoles().stream().map(Role::getRoleNameZh).collect(Collectors.toList());
        List<DepartInfo> departInfos = departService.queryDepart();
        List<DepartInfoVo> departInfoVos = new ArrayList<>();
        DepartInfoVo.changeDepartPojoToVo(departInfos,departInfoVos);
        if(roleNameZhList.size() != 0 && departInfoVos.size() != 0){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("roleNameZhList",roleNameZhList);
            jsonObject.put("departNameList",departInfoVos);
            return JsonResult.success(jsonObject);
        }
        return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    /**
     * 获取部门列表，提供给用户进行查询选择
     * @return
     */
    @GetMapping("/readyForQueryUserInfo")
    public JsonResult queryDepartReadyForQueryUserInfo() {
        List<String> departNameList = departService.queryAllDepartInfo().stream().map(DepartInfo::getDepartName).collect(Collectors.toList());
        if (departNameList.size() != 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("departNameList", departNameList);
            return JsonResult.success(jsonObject);
        }
        return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    /**
     * 根据输入的条件查询用户信息
     * @param userId
     * @param depart
     * @return
     */
    @GetMapping("/queryUserByIdOrDepart")
    public JsonResult queryUserByConditions(String userId,String depart){
        List<UserInfoVo> userInfoVoList = new ArrayList<>();
        UserInfoVo userInfoVo;
        if(!"".equals(userId)){
            if(userId.matches("\\d+")) {
                UserInfo userInfo = userService.queryUserInfoByUserId(Integer.parseInt(userId));
                userInfoVo = (UserInfoVo) changeUserPattern(userInfo);
                userInfoVoList.add(userInfoVo);
            }else{
                return JsonResult.failure(ResultCode.PARAM_TYPE_BIND_ERROR);
            }
        }else {
            if(!"".equals(depart)){
                Integer departId = departService.queryDepartIdByName(depart);
                for(UserInfo u : userService.queryUserInfoByDepartId(departId)){
                    userInfoVo = (UserInfoVo) changeUserPattern(u);
                    userInfoVoList.add(userInfoVo);
                }
            }else{
                return JsonResult.failure(ResultCode.PARAM_TYPE_BIND_ERROR);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userInfoVoList",userInfoVoList);
        return JsonResult.success(jsonObject);

    }

    /**
     * 查找拥有部门管理员权限的用户
     * @return
     */
    @GetMapping("/admin/getManagerList")
    public JsonResult queryManagerUser(){
        List<String> managerUserList = userService.getManagerUserList();
        return managerUserList.size() != 0 ? JsonResult.success(managerUserList) : JsonResult.failure(ResultCode.NEED_CHANGE_ROLE);
    }

    /**
     * 重载方法，改变user的类型，根据输入的user类型进行pojo到bo，vo之间的转化
     * @param u
     * @return
     */
    private Object changeUserPattern(UserInfo u){
        UserInfoVo userInfoVo = new UserInfoVo();
        //根据部门的id查找出完整的部门信息（包含上级目录）
        String departName = departService.queryDepartInfoById(u.getDepartId()).getDepartName();
        String allRole = "";
        String status = u.getEnlock().equals(0)?"未锁定":"已锁定";
        List<Role> roles = userService.getUserRolesByUserId(u.getUid());
        for(Role r : roles){
            allRole += r.getRoleNameZh();
        }
        if(!"".equals(departName)){
            userInfoVo = new UserInfoVo(u.getUid(),u.getUsername(),allRole,departName,u.getEmail(),status,u.getRemark());
        }
        return userInfoVo;
    }

    private Object changeUserPattern(UserInfoBo userInfoBo,Integer id){
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(id);
        userInfo.setUsername(userInfoBo.getUsername());
        userInfo.setPassword(userInfoBo.getPassword());
        userInfo.setEmail(userInfoBo.getEmail());
        userInfo.setRemark(userInfoBo.getRemark());
        //根据部门名称查找部门id
        userInfo.setDepartId(departService.queryDepartIdByName(userInfoBo.getDepartName()));
        return userInfo;
    }





}
