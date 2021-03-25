package com.example.officeoa.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.officeoa.model.DepartInfo;
import com.example.officeoa.model.Vo.DepartInfoVo;
import com.example.officeoa.model.Vo.MailInfoVo;
import com.example.officeoa.service.DepartService;
import com.example.officeoa.service.MailListService;
import com.example.officeoa.utils.JsonResult;
import com.example.officeoa.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:14 2021/3/21
 */
@RestController
public class MailListController {

    @Autowired
    private MailListService mailListService;

    @Autowired
    private DepartService departService;

    @GetMapping("/queryAllMailListInfo")
    public JsonResult getAllMailListInfo(){
        //部门树状图
        List<DepartInfo> departInfos = departService.queryDepart();
        List<DepartInfoVo> departInfoVos = new ArrayList<>();
        DepartInfoVo.changeDepartPojoToVo(departInfos,departInfoVos);
        //全体人员的通讯录信息
        List<MailInfoVo> mailInfoVos = mailListService.queryAllMailListInfo();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mailListInfos",mailInfoVos);
        jsonObject.put("departInfoVos",departInfoVos);
        return mailInfoVos.size() != 0 ? JsonResult.success(jsonObject) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }
}
