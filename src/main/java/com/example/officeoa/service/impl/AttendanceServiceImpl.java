package com.example.officeoa.service.impl;

import com.example.officeoa.mapper.AttendanceMapper;
import com.example.officeoa.model.AttendanceInfo;
import com.example.officeoa.model.Vo.AttendanceInfoVo;
import com.example.officeoa.model.enumeration.Status;
import com.example.officeoa.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 16:14 2021/3/24
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;


    @Override
    public Integer insertNewAttendanceInfo(AttendanceInfo attendanceInfo) {
        int count = attendanceMapper.insert(attendanceInfo);
        return count != 0 ? attendanceInfo.getAid() : -1 ;
    }


    @Override
    public Integer queryAttendanceInfoTypeById(Integer aid) {
        AttendanceInfo attendanceInfo = attendanceMapper.selectByPrimaryKey(aid);
        return attendanceInfo.getType() != null ? attendanceInfo.getType() : -1;
    }

    @Override
    public Boolean updateAttendanceInfo(AttendanceInfo attendanceInfo) {
        int count = attendanceMapper.updateByPrimaryKeySelective(attendanceInfo);
        return count == 1 ? true : false;
    }

    @Override
    public Integer queryAttendanceInfoIsExistByTime(AttendanceInfo info) {
        AttendanceInfo attendanceInfo = attendanceMapper.selectOne(info);
        return attendanceInfo != null ? attendanceInfo.getAid() : -1;
    }

    @Override
    public List<AttendanceInfoVo> queryAbnormalAttendInfoByMonth(String year,String month,Integer days,Integer nid) {
        List<AttendanceInfoVo> vos = new ArrayList<>();
        //查询条件指定用户和创建时间
        AttendanceInfo attendanceInfo = new AttendanceInfo();
        attendanceInfo.setNid(nid);
        for(int i=1;i<=days;i++){
            String day = "";
            if(i < 10){
                day = "0"+i;
            }
            attendanceInfo.setCreateTime(year+month+day);
            AttendanceInfo selectOne = attendanceMapper.selectOne(attendanceInfo);

            AttendanceInfoVo attendanceInfoVo = new AttendanceInfoVo();
            attendanceInfoVo.setCreateTime(year+"-"+month+"-"+day);
            if(selectOne == null){
                attendanceInfoVo.setType("未打卡");
            }else{
                attendanceInfoVo.setType(Status.getValue(selectOne.getType()));
            }
            vos.add(attendanceInfoVo);
        }
        return vos;
    }
}
