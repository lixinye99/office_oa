package com.example.officeoa.model.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:09 2021/3/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailInfoVo {
    private Integer uid;
    private String username;
    private String departName;
    private String roleNameZh;
    private String connectTelNum;
    private String email;
    private String remark;
}
