package com.example.officeoa.model.Bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 14:31 2021/3/18
 */
@Data
@NoArgsConstructor
public class LeaveInfoBo {
    private String leaveType;
    private Date leaveBeginTime;
    private Date leaveEndTime;
    private String leaveReason;
}
