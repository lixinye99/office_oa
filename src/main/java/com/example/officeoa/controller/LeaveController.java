package com.example.officeoa.controller;

import com.example.officeoa.model.Bo.LeaveInfoBo;
import com.example.officeoa.model.LeaveInfo;
import com.example.officeoa.model.UserInfo;
import com.example.officeoa.model.Vo.LeaveInfoVo;
import com.example.officeoa.model.enumeration.Status;
import com.example.officeoa.service.LeaveService;
import com.example.officeoa.service.UserService;
import com.example.officeoa.utils.JsonResult;
import com.example.officeoa.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 14:48 2021/3/18
 */
@RestController
public class LeaveController {

    @Autowired
    private UserService userService;

    @Autowired
    private LeaveService leaveService;


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
     * 插入新的请假信息
     * @param leaveInfoBo
     * @param principal
     * @return
     */
    @PostMapping("/insertLeaveInfo")
    public JsonResult insertLeaveInfo(LeaveInfoBo leaveInfoBo, Principal principal){
        //状态信息  2：待审核  3：已通过   4：未通过
        LeaveInfo leaveInfo = new LeaveInfo();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        leaveInfo.setPostUserId(userService.queryUsernameIsExist(principal.getName()));
        leaveInfo.setLeaveType(leaveInfoBo.getLeaveType());
        leaveInfo.setLeaveReason(leaveInfoBo.getLeaveReason());
        leaveInfo.setLeaveTime(dateFormat.format(leaveInfoBo.getLeaveBeginTime()) + " —— " + dateFormat.format(leaveInfoBo.getLeaveEndTime()));
        leaveInfo.setCreateTime(dateFormat.format(date));
        leaveInfo.setStatus(Status.WAIT_EXAMINE.getCode());
        Boolean insertResult = leaveService.insertLeaveInfo(leaveInfo);
        return insertResult ? JsonResult.success() : JsonResult.failure(ResultCode.SAVE_ERROR);
    }

    /**
     * 修改请假信息的状态
     * @param lidList
     * @param status
     * @return
     */
    @PutMapping("/updateLeaveInfoStatus")
    public JsonResult updateLeaveInfoStatus(Integer[] lidList, String status){

        Boolean updateResult = leaveService.updateLeaveInfoStatusByLid(lidList,status);
        return updateResult ? JsonResult.success() : JsonResult.failure(ResultCode.UPDATE_ERROR);
    }


    /**
     * 删除请假信息
     * @param lidList
     * @return
     */
    @DeleteMapping("/deleteLeaveInfo")
    public JsonResult deleteLeaveInfo(Integer[] lidList){
        Boolean deleteResult = leaveService.deleteLeaveInfoByLidList(lidList);
        return deleteResult ? JsonResult.success() : JsonResult.failure(ResultCode.DELETE_ERROR);
    }

    /**
     * 获取登录用户的所有请假信息
     * @param principal
     * @return
     */
    @GetMapping("/getMyLeaveInfo")
    public JsonResult queryMyLeaveInfoList(Principal principal){
        List<LeaveInfo> leaveInfosSelf = leaveService.queryLeaveInfoByUserId(userService.queryUsernameIsExist(principal.getName()));
        List<LeaveInfoVo> leaveInfoVos = (List<LeaveInfoVo>) changeLeaveInfoPojoToVo(leaveInfosSelf);
        return leaveInfoVos != null ? JsonResult.success(leaveInfoVos) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    /**
     * 获取自己审核的请假信息
     * @param principal
     * @return
     */
    @GetMapping("/getMyExamineLeaveInfo")
    public JsonResult queryMyExamineLeaveInfoList(Principal principal){
        Integer loginId = userService.queryUsernameIsExist(principal.getName());
        List<LeaveInfo> leaveInfosOther = new ArrayList<>();
        //如果登录的用户为管理员则获取所有部门管理员的请假信息
        if(hasAdminRole()){
            List<String> managerUserList = userService.getManagerUserList();
            List<Integer> managerUserIdList = new ArrayList<>();
            for(String managerUser : managerUserList){
                Integer userId = userService.queryUsernameIsExist(managerUser);
                managerUserIdList.add(userId);
            }
            leaveInfosOther = leaveService.queryLeaveInfoByUserIdList(managerUserIdList);
        }
        //如果登录的用户为部门管理员则获取部门下所有员工的请假信息
        if(hasManagerRole()){
            Integer departId = userService.queryUserInfoByUserId(userService.queryUsernameIsExist(principal.getName())).getDepartId();
            List<UserInfo> userInfos = userService.queryUserInfoByDepartId(departId);
            List<Integer> userList = userInfos.stream().map(UserInfo::getUid).collect(Collectors.toList());
            userList.remove(loginId);
            if(userList.size() == 0){
                return JsonResult.success();
            }
            leaveInfosOther = leaveService.queryLeaveInfoByUserIdList(userList);
        }
        List<LeaveInfoVo> leaveInfoVos = (List<LeaveInfoVo>) changeLeaveInfoPojoToVo(leaveInfosOther);
        return leaveInfoVos != null ? JsonResult.success(leaveInfoVos) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    /**
     * 类型转化，pojo  变成  vo
     * @param leaveInfos
     * @return
     */
    private Object changeLeaveInfoPojoToVo(List<LeaveInfo> leaveInfos){
        List<LeaveInfoVo> leaveInfoVos = new ArrayList<>();
        for(LeaveInfo leaveInfo : leaveInfos){
            LeaveInfoVo leaveInfoVo = new LeaveInfoVo();
            leaveInfoVo.setLid(leaveInfo.getLid());
            leaveInfoVo.setPostUserName(userService.queryUserInfoByUserId(leaveInfo.getPostUserId()).getUsername());
            leaveInfoVo.setLeaveType(leaveInfo.getLeaveType());
            leaveInfoVo.setCreateTime(leaveInfo.getCreateTime());
            leaveInfoVo.setLeaveReason(leaveInfo.getLeaveReason());
            leaveInfoVo.setLeaveTime(leaveInfo.getLeaveTime());
            leaveInfoVo.setStatus(Status.getValue(leaveInfo.getStatus()));
            leaveInfoVos.add(leaveInfoVo);
        }
        return leaveInfoVos;
    }

}
