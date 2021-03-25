package com.example.officeoa.controller;

import com.example.officeoa.model.Bo.NoticeInfoBo;
import com.example.officeoa.model.NoticeInfo;
import com.example.officeoa.model.Vo.NoticeInfoVo;
import com.example.officeoa.service.NoticeService;
import com.example.officeoa.service.UserService;
import com.example.officeoa.utils.JsonResult;
import com.example.officeoa.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 10:16 2021/3/22
 */
@RestController
public class NoticeController {

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    /**
     * 插入新的公告信息
     * @param noticeInfoBo
     * @param principal
     * @return
     */
    @PostMapping("/insertNoticeInfo")
    public JsonResult insertNotice(NoticeInfoBo noticeInfoBo, Principal principal){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        NoticeInfo noticeInfo = new NoticeInfo();
        noticeInfo.setNoticeTitle(noticeInfoBo.getTitle());
        noticeInfo.setNoticeContent(noticeInfoBo.getContent());
        noticeInfo.setCreateTime(simpleDateFormat.format(new Date()));
        noticeInfo.setReleaser(userService.queryUsernameIsExist(principal.getName()));
        Boolean insertResult = noticeService.insertNoticeInfo(noticeInfo);
        return insertResult == true ? JsonResult.success() : JsonResult.failure(ResultCode.SAVE_ERROR);
    }

    /**
     * 查询所有的公告信息
     * @return
     */
    @GetMapping("/queryAllNoticeInfo")
    public JsonResult queryAllNoticeInfo(){
        List<NoticeInfo> noticeInfos = noticeService.queryAllNoticeInfo();
        List<NoticeInfoVo> noticeInfoVos = new ArrayList<>();
        if(noticeInfos.size() != 0){
            for(NoticeInfo noticeInfo : noticeInfos){
                NoticeInfoVo noticeInfoVo = new NoticeInfoVo();
                noticeInfoVo.setNid(noticeInfo.getNid());
                noticeInfoVo.setTitle(noticeInfo.getNoticeTitle());
                noticeInfoVo.setContent(noticeInfo.getNoticeContent());
                noticeInfoVo.setCreateTime(noticeInfo.getCreateTime());
                noticeInfoVo.setReleaser(userService.queryUserInfoByUserId(noticeInfo.getReleaser()).getUsername());
                noticeInfoVos.add(noticeInfoVo);
            }
        }
        return noticeInfoVos.size() != 0 ? JsonResult.success(noticeInfoVos) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    /**
     * 根据nid列表删除公告信息
     * @param nidList
     * @return
     */
    @DeleteMapping("/deleteNoticeInfo")
    public JsonResult deleteNoticeInfo(Integer[] nidList){
        Boolean deleteResult = noticeService.deleteNoticeInfoByNidList(nidList);
        return deleteResult ? JsonResult.success() : JsonResult.failure(ResultCode.DELETE_ERROR);
    }

    /**
     * 修改公告信息
     * @param nid
     * @param noticeInfoBo
     * @return
     */
    @PutMapping("/updateNoticeInfo/{nid}")
    public JsonResult updateNoticeInfo(@PathVariable Integer nid,NoticeInfoBo noticeInfoBo){
        NoticeInfo noticeInfo = new NoticeInfo();
        noticeInfo.setNid(nid);
        noticeInfo.setNoticeTitle(noticeInfoBo.getTitle());
        noticeInfo.setNoticeContent(noticeInfoBo.getContent());
        Boolean updateResult = noticeService.updateNoticeInfo(noticeInfo);
        return updateResult ? JsonResult.success() : JsonResult.failure(ResultCode.UPDATE_ERROR);
    }
}
