package com.example.officeoa.service.impl;

import com.example.officeoa.mapper.LeaveMapper;
import com.example.officeoa.model.LeaveInfo;
import com.example.officeoa.model.enumeration.Status;
import com.example.officeoa.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 14:50 2021/3/18
 */
@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;


    @Override
    public Boolean insertLeaveInfo(LeaveInfo leaveInfo) {
        int count = leaveMapper.insert(leaveInfo);
        return count == 1 ? true : false;
    }

    @Override
    public List<LeaveInfo> queryLeaveInfoByUserId(Integer uid) {
        LeaveInfo leaveInfo = new LeaveInfo();
        leaveInfo.setPostUserId(uid);
        List<LeaveInfo> leaveInfos = leaveMapper.select(leaveInfo);
        return leaveInfos;
    }

    @Override
    public Boolean updateLeaveInfoStatusByLid(Integer[] lidList, String status) {
        Integer statusType;
        if("通过".equals(status) || "全部通过".equals(status)){
            statusType = Status.getValue("已通过");
        }else{
            statusType = Status.getValue("未通过");
        }
        LeaveInfo leaveInfo = new LeaveInfo();
        leaveInfo.setStatus(statusType);
        Example example = new Example(LeaveInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("lid", Arrays.asList(lidList));
        int count = leaveMapper.updateByExampleSelective(leaveInfo, example);
        return count != 0 ? true : false;
    }

    @Override
    public Boolean deleteLeaveInfoByLidList(Integer[] lidList) {
        Example example = new Example(LeaveInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("lid", Arrays.asList(lidList));
        int count = leaveMapper.deleteByExample(example);
        return count != 0 ? true : false;
    }

    @Override
    public List<LeaveInfo> queryLeaveInfoByUserIdList(List<Integer> managerUserIdList) {
        Example example = new Example(LeaveInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("postUserId",managerUserIdList);
        List<LeaveInfo> leaveInfos = leaveMapper.selectByExample(example);
        return leaveInfos;
    }
}
