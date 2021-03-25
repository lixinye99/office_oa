package com.example.officeoa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 14:25 2021/3/18
 */
@Data
@NoArgsConstructor
@Table(name = "leaveinfo")
public class LeaveInfo {
    @Id
    private Integer lid;
    @Column(name = "postUserId")
    private Integer postUserId;
    @Column(name = "leaveType")
    private String leaveType;
    @Column(name = "leaveReason")
    private String leaveReason;
    @Column(name = "leaveTime")
    private String leaveTime;
    @Column(name = "createTime")
    private String createTime;
    @Column(name = "status")
    private Integer status;
}
