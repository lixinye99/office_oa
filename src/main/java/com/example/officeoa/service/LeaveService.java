package com.example.officeoa.service;

import com.example.officeoa.model.LeaveInfo;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 14:50 2021/3/18
 */
public interface LeaveService {

    /**
     * 插入请假信息
     * @param leaveInfo
     * @return
     */
    Boolean insertLeaveInfo(LeaveInfo leaveInfo);


    /**
     * 根据用户id查找用户的请假信息
     * @param uid
     * @return
     */
    List<LeaveInfo> queryLeaveInfoByUserId(Integer uid);

    /**
     * 根据传入的id列表对请假信息的状态进行修改
     * @param lidList
     * @param status
     * @return
     */
    Boolean updateLeaveInfoStatusByLid(Integer[] lidList, String status);

    /**
     * 根据传入的id列表对请假信息进行删除
     * @param lidList
     * @return
     */
    Boolean deleteLeaveInfoByLidList(Integer[] lidList);

    /**
     * 根据传入的id列表查询用户的请假信息
     * @param managerUserIdList
     * @return
     */
    List<LeaveInfo> queryLeaveInfoByUserIdList(List<Integer> managerUserIdList);
}
