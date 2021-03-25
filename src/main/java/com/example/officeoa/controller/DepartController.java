package com.example.officeoa.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.officeoa.mapper.UserExtraMapper;
import com.example.officeoa.model.Bo.DepartInfoBo;
import com.example.officeoa.model.DepartInfo;
import com.example.officeoa.service.DepartService;
import com.example.officeoa.service.UserService;
import com.example.officeoa.utils.JsonResult;
import com.example.officeoa.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 9:22 2021/3/16
 */
@RestController
public class DepartController {

    @Autowired
    private DepartService departService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserExtraMapper userExtraMapper;

    @PostMapping("/admin/insertDepart")
    public JsonResult insertDepartInfo(DepartInfoBo departInfoBo){
        if(departService.queryDepartIdByName(departInfoBo.getDepartName()) != -1){
            return JsonResult.failure(ResultCode.DEPART_IS_EXIST);
        }
        DepartInfo departInfo = (DepartInfo) changeDepartBoToPojo(departInfoBo);
        boolean insertFlag = departService.insertDepartInfo(departInfo);
        return insertFlag == true ? JsonResult.success() : JsonResult.failure(ResultCode.SAVE_ERROR);
    }

    /**
     * 根据的部门id删除部门
     * @param departId
     * @return
     */
    @DeleteMapping("/admin/deleteDepart/{departId}")
    public JsonResult deleteDepartById(@PathVariable Integer departId){
        //在删除之前先查询该部门下是否有员工，有则无法删除
        boolean hasUser = userService.queryHasUserInDepartByDepartId(departId);
        //如果没有进行删除部门，有返回错误信息
        if(!hasUser){
            boolean deleteDepart = departService.deleteDepartById(departId);
            return deleteDepart ? JsonResult.success() : JsonResult.failure(ResultCode.DELETE_ERROR);
        }
        return JsonResult.failure(ResultCode.DEPART_HAS_USER);
    }

    /**
     * 更新前根据部门id获取部门信息进行回显
     * @param departId
     * @return
     */
    @GetMapping("/admin/readyToChangeDepartInfo/{departId}")
    public JsonResult queryDepartInfoToUpdate(@PathVariable Integer departId){
        //获取部门信息
        DepartInfo departInfo = departService.queryDepartInfoById(departId);
        DepartInfoBo departInfoBo = (DepartInfoBo) changeDepartPojoToBo(departInfo);
        //查询管理者列表
        List<String> managerUserList = userExtraMapper.getManagerUserList();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("managerList",managerUserList);
        jsonObject.put("departInfo",departInfoBo);
        return JsonResult.success(jsonObject);
    }

    /**
     * 根据部门ia修改部门信息
     * @param departId
     * @param departInfoBo
     * @return
     */
    @PutMapping("/admin/updateDepart/{departId}")
    public JsonResult updateDepartInfo(@PathVariable Integer departId,DepartInfoBo departInfoBo){
        DepartInfo departInfo = (DepartInfo) changeDepartBoToPojo(departInfoBo);
        departInfo.setDepartId(departId);
        boolean updateResult = departService.updataDepartInfo(departInfo);
        return updateResult ? JsonResult.success() : JsonResult.failure(ResultCode.UPDATE_ERROR);
    }

    /**
     * 重构方法，把部门的bo类转换为pojo
     * @param departInfoBo
     * @return
     */
    private Object changeDepartBoToPojo(DepartInfoBo departInfoBo){
        DepartInfo departInfo = new DepartInfo();
        departInfo.setDepartName(departInfoBo.getDepartName());
        departInfo.setConnectTelNum(departInfoBo.getConnectTelNum());
        departInfo.setDepartDescribe(departInfoBo.getDepartDescribe());
        departInfo.setPrincipalUserId(userService.queryUsernameIsExist(departInfoBo.getPrincipalUserName()));
        if("".equals(departInfoBo.getParentName())) {
            departInfo.setParentId(0);
        }else {
            departInfo.setParentId(departService.queryDepartIdByName(departInfoBo.getParentName()));
        }
        return departInfo;
    }

    private Object changeDepartPojoToBo(DepartInfo departInfo){
        DepartInfoBo departInfoBo = new DepartInfoBo();
        departInfoBo.setDepartName(departInfo.getDepartName());
        departInfoBo.setConnectTelNum(departInfo.getConnectTelNum());
        departInfoBo.setDepartDescribe(null == departInfoBo.getDepartDescribe()?"":departInfo.getDepartDescribe());
        if(departInfo.getParentId() == 0){
            departInfoBo.setParentName("");
        }else {
            departInfoBo.setParentName(departService.queryDepartInfoById(departInfo.getParentId()).getDepartName());
        }
        departInfoBo.setPrincipalUserName(userService.queryUserInfoByUserId(departInfo.getPrincipalUserId()).getUsername());
        return departInfoBo;
    }
}
