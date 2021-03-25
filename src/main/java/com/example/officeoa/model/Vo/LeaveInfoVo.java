package com.example.officeoa.model.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 19:04 2021/3/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LeaveInfoVo {
    private Integer lid;
    private String postUserName;
    private String leaveType;
    private String leaveTime;
    private String leaveReason;
    private String createTime;
    private String status;
}
