package com.example.officeoa.model.Bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 10:01 2021/3/16
 */
@Data
@NoArgsConstructor
public class DepartInfoBo {
    private String departName;
    private String parentName;
    private String principalUserName;
    private String connectTelNum;
    private String departDescribe;
}
