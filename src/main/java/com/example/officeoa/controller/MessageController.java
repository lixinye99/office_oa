package com.example.officeoa.controller;

import com.example.officeoa.model.Bo.MessageInfoBo;
import com.example.officeoa.model.MessageInfo;
import com.example.officeoa.model.UserInfo;
import com.example.officeoa.model.Vo.MessageInfoVo;
import com.example.officeoa.service.DepartService;
import com.example.officeoa.service.MessageService;
import com.example.officeoa.service.UserService;
import com.example.officeoa.utils.JsonResult;
import com.example.officeoa.utils.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:46 2021/3/16
 */
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartService departService;

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
     * 查询登录用户收到的消息信息
     * @return
     */
    @GetMapping("/getMyReceiveMessageInfo")
    public JsonResult queryMyReceiveMessageInfo(Principal principal){
        List<MessageInfo> messageInfos;
        //获取登录的用户的ID
        String username = principal.getName();
        Integer uid = userService.queryUsernameIsExist(username);
        //根据用户名获取到部门id
        UserInfo userInfo = userService.queryUserInfoByUserId(userService.queryUsernameIsExist(username));
        Integer departId = userInfo.getDepartId();
        messageInfos = messageService.queryMyReceiveMessageInfo(uid,departId);
        List<MessageInfoVo> messageInfoVos = (List<MessageInfoVo>) changeMessageInfoPojoToVo(messageInfos);
        return messageInfoVos != null ? JsonResult.success(messageInfoVos) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    /**
     * 查询所有的消息消息
     * @param principal
     * @return
     */
    @GetMapping("/getAllMessageInfo")
    public JsonResult queryAllMessageInfo(Principal principal){
        //如果是系统管理员可以查看到所有的消息推送信息
        List<MessageInfo> messageInfos;
        if(hasAdminRole()) {
            messageInfos = messageService.queryAllMessageInfo();
            List<MessageInfoVo> messageInfoVos = (List<MessageInfoVo>) changeMessageInfoPojoToVo(messageInfos);
            return messageInfoVos != null ? JsonResult.success(messageInfoVos) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        }
        return JsonResult.failure(ResultCode.PERMISSION_NO_ACCESS);
    }

    /**
     * 查询登录用户推送的消息信息
     * @param principal
     * @return
     */
    @GetMapping("/getMyPushMessageInfo")
    public JsonResult queryMyPushMessageInfo(Principal principal){
        Integer uid = userService.queryUsernameIsExist(principal.getName());
        if(uid != -1){
            List<MessageInfo> messageInfos = messageService.queryMyPushMessageInfo(uid);
            List<MessageInfoVo> messageInfoVos = (List<MessageInfoVo>) changeMessageInfoPojoToVo(messageInfos);
            return JsonResult.success(messageInfoVos);
        }
        return JsonResult.failure(ResultCode.USER_NOT_EXIST);
    }

    /**
     * 点击消息详情后根据登录用户判断是否需要更新消息状态
     * @param username
     * @param status
     * @param principal
     * @return
     */
    @PutMapping("/changeMessageStatus")
    public JsonResult changeMessageInfoStatus(String username,String status,Integer mid,Principal principal){
        //如果登录用户为推送用户且消息状态为未读则改变消息状态
        if(principal.getName().equals(username) && "未读".equals(status) ){
            boolean updateResult = messageService.updateMessageInfoStatus(mid);
            return updateResult ? JsonResult.success() : JsonResult.failure(ResultCode.UPDATE_ERROR);
        }
        return null;
    }

    /**
     * 根据输入条件筛选消息信息
     * @param mid
     * @param pusher
     * @param inputDate
     * @return
     */
    @GetMapping("/queryMessageByCondition")
    public JsonResult queryMessageByCondition(String mid,String pusher,String inputDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = dateFormat.parse(inputDate);

        if(StringUtils.isBlank(mid) && StringUtils.isBlank(pusher) && date == null){
            return JsonResult.failure(ResultCode.PARAM_IS_BLANK);
        }
        List<MessageInfo> messageInfos = new ArrayList<>();
        List<MessageInfoVo> messageInfoVos;
        if(!StringUtils.isBlank(mid)){
            //如果查找的编号不为空
            MessageInfo messageInfo = messageService.queryMessageInfoById(Integer.parseInt(mid));
            messageInfos.add(messageInfo);
            messageInfoVos = (List<MessageInfoVo>) changeMessageInfoPojoToVo(messageInfos);
            return messageInfo != null ? JsonResult.success(messageInfoVos) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        }else {
            //如果查找的编号为空,其他条件不为空
            messageInfos = messageService.queryMessageInfoByPusherOrDate(pusher,date);
        }
        messageInfoVos = (List<MessageInfoVo>) changeMessageInfoPojoToVo(messageInfos);
        return messageInfos.size() != 0 ? JsonResult.success(messageInfoVos) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }



    /**
     * 插入消息信息
     * @param principal
     * @param messageInfoBo
     * @return
     */
    @PostMapping("/insertPushMessage")
    public JsonResult insertMessage(Principal principal,MessageInfoBo messageInfoBo){
        //对数据进行校验，字段不为空
        if(StringUtils.isBlank(messageInfoBo.getMassageTitle()) || StringUtils.isBlank(messageInfoBo.getPushContent())){
            return JsonResult.failure(ResultCode.PARAM_IS_BLANK);
        }

        MessageInfo messageInfo = messageInfo = new MessageInfo();
        messageInfo.setMassageTitle(messageInfoBo.getMassageTitle());
        messageInfo.setPusher(userService.queryUsernameIsExist(principal.getName()));
        messageInfo.setPushTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        messageInfo.setPushContent(messageInfoBo.getPushContent());
        messageInfo.setMessageStatus(0);
        //判断是推送给部门还是用户个人
        if(!"".equals(messageInfoBo.getPushUserName()) && messageInfoBo.getPushObject().length == 0){
            //推送给个人
            //先判断用户是否存在
            Integer usernameIsExist = userService.queryUsernameIsExist(messageInfoBo.getPushUserName());
            if(usernameIsExist != -1){
                //把bo封装为pojo准备插入数据
                messageInfo.setPushObject(usernameIsExist);
                messageInfo.setPushType(1);
                Boolean insertResult = messageService.insertMessageInfo(messageInfo);
                return insertResult ? JsonResult.success() : JsonResult.failure(ResultCode.SAVE_ERROR);
            }
            return JsonResult.failure(ResultCode.USER_NOT_EXIST);
        }else if("".equals(messageInfoBo.getPushUserName()) && messageInfoBo.getPushObject().length != 0){
            //推送给部门
            messageInfo.setPushType(0);
            for(String departId : messageInfoBo.getPushObject()){
                messageInfo.setPushObject(Integer.parseInt(departId));
                Boolean insertResult = messageService.insertMessageInfo(messageInfo);
                if(insertResult){
                    continue;
                }else {
                    return JsonResult.failure(ResultCode.SAVE_ERROR);
                }
            }
            return JsonResult.success();
        }
        return JsonResult.failure(ResultCode.PARAM_IS_INVALID);
    }

    /**
     * 把pojo转化为vo
     * @param messageInfos
     * @return
     */
    private Object changeMessageInfoPojoToVo(List<MessageInfo> messageInfos){
        List<MessageInfoVo> messageInfoVos = new ArrayList<>();
        for(MessageInfo messageInfo : messageInfos){
            MessageInfoVo messageInfoVo = new MessageInfoVo();
            messageInfoVo.setMid(messageInfo.getMid());
            messageInfoVo.setMassageTitle(messageInfo.getMassageTitle());
            messageInfoVo.setPusher(userService.queryUserInfoByUserId(messageInfo.getPusher()).getUsername());
            messageInfoVo.setPushTime(messageInfo.getPushTime());
            if(messageInfo.getPushType() == 1){
                messageInfoVo.setPushObject(userService.queryUserInfoByUserId(messageInfo.getPushObject()).getUsername());
            }else if(messageInfo.getPushType() == 0){
                messageInfoVo.setPushObject(departService.queryDepartInfoById(messageInfo.getPushObject()).getDepartName());
            }
            messageInfoVo.setPushContent(messageInfo.getPushContent());
            messageInfoVo.setMessageStatus(messageInfo.getMessageStatus() == 0 ? "未读" : "已读");
            messageInfoVos.add(messageInfoVo);
        }
        return messageInfoVos;
    }
}
