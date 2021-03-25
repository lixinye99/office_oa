package com.example.officeoa.service;

import com.example.officeoa.model.AttendanceInfo;
import com.example.officeoa.model.Vo.AttendanceInfoVo;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 16:14 2021/3/24
 */
public interface AttendanceService {

    /**
     * 插入新的打卡信息，返回插入成功的aid
     * @param attendanceInfo
     * @return
     */
    Integer insertNewAttendanceInfo(AttendanceInfo attendanceInfo);

    /**
     * 根据id查询已有的状态
     * @param aid
     * @return
     */
    Integer queryAttendanceInfoTypeById(Integer aid);

    /**
     * 更新打卡信息
     * @param attendanceInfo
     * @return
     */
    Boolean updateAttendanceInfo(AttendanceInfo attendanceInfo);

    /**
     * 查询是否已经打卡
     * @param info
     * @return
     */
    Integer queryAttendanceInfoIsExistByTime(AttendanceInfo info);


    List<AttendanceInfoVo> queryAbnormalAttendInfoByMonth(String year,String month,Integer days,Integer nid);
}
