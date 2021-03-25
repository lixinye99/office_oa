package com.example.officeoa.controller;

import com.example.officeoa.model.AttendanceInfo;
import com.example.officeoa.model.Vo.AttendanceInfoVo;
import com.example.officeoa.model.enumeration.Status;
import com.example.officeoa.service.AttendanceService;
import com.example.officeoa.service.UserService;
import com.example.officeoa.utils.JsonResult;
import com.example.officeoa.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 16:22 2021/3/24
 */
@RestController
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserService userService;

    /**
     * 上班打卡流程
     * @param workTime
     * @param principal
     * @return
     */
    @PostMapping("/insertAttendanceInfo")
    public JsonResult insertAttendanceInfo(String workTime,Principal principal){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        //查询今天是否已经打卡上班了
        Integer isExist = commonCode(principal);
        if(isExist != -1){
            return JsonResult.failure(ResultCode.WORK_TIME_IS_EXIST);
        }

        //没有就插入新的数据
        AttendanceInfo attendanceInfo = new AttendanceInfo();
        attendanceInfo.setNid(userService.queryUsernameIsExist(principal.getName()));
        Integer work = Integer.valueOf(workTime);
        //设置上班打卡时间
        attendanceInfo.setWorkTime(work);
        //设置创建的年月日
        attendanceInfo.setCreateTime(format.format(new Date()));
        //判断是否迟到，添加状态
        if(work > 80000){
            //迟到返回false
            attendanceInfo.setType(Status.getValue("迟到"));
        }
        //插入到数据库中
        Integer aid = attendanceService.insertNewAttendanceInfo(attendanceInfo);
        if(aid == -1){
            return JsonResult.failure(ResultCode.SAVE_ERROR);
        }
        return JsonResult.success();
    }

    /**
     * 下班打卡
     * @param leaveTime
     * @param principal
     * @return
     */
    @PutMapping("/addAttendanceInfoLeaveTime")
    public JsonResult addAttendanceInfoLeaveTime(String leaveTime,Principal principal){
        //查询是否进行上班打卡
        Integer aid = commonCode(principal);
        if(aid == -1){
            return JsonResult.failure(ResultCode.WORK_TIME_IS_NOT_EXIST);
        }

        //更新打卡信息，添加下班时间
        AttendanceInfo attendanceInfo = new AttendanceInfo();
        attendanceInfo.setAid(aid);
        Integer leave = Integer.valueOf(leaveTime);
        attendanceInfo.setClosingTime(leave);

        //获取已经有的状态决定是否需要修改状态
        Integer type = attendanceService.queryAttendanceInfoTypeById(aid);
        if(leave < 173000){
            if(type != -1){
                attendanceInfo.setType(Status.getValue("迟到并早退"));
            }else{
                attendanceInfo.setType(Status.getValue("早退"));
            }
        }else{
            if(type == -1){
                attendanceInfo.setType(Status.getValue("正常"));
            }
        }
        Boolean updateResult = attendanceService.updateAttendanceInfo(attendanceInfo);
        return  updateResult ? JsonResult.success() : JsonResult.failure(ResultCode.UPDATE_ERROR);
    }

    /**
     * 获取点击的月份当月的考勤情况
     * @param year
     * @param month
     * @param principal
     * @return
     */
    @GetMapping("/queryAttendInfoByMonth")
    public JsonResult queryAbnormalAttendInfoByMonth(String year,String month,Principal principal){
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(year), Integer.parseInt(month), 0); //输入类型为int类型
        //获取指定年月有多少天
        int days =  c.get(Calendar.DAY_OF_MONTH);
        //查找nid
        Integer nid = userService.queryUsernameIsExist(principal.getName());
        List<AttendanceInfoVo> attendanceInfoVos = attendanceService.queryAbnormalAttendInfoByMonth(year,month,days,nid);
        return attendanceInfoVos != null ? JsonResult.success(attendanceInfoVos) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    /**
     * 公共代码获取今天打卡信息的aid
     * @param principal
     * @return
     */
    private Integer commonCode(Principal principal){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        AttendanceInfo info = new AttendanceInfo();
        info.setNid(userService.queryUsernameIsExist(principal.getName()));
        info.setCreateTime(format.format(new Date()));
        Integer aid=  attendanceService.queryAttendanceInfoIsExistByTime(info);
        return aid;
    }
}
