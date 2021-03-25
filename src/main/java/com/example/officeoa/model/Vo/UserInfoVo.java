package com.example.officeoa.model.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 10:16 2021/3/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoVo {
    private Integer uid;
    private String username;
    private String role;
    private String departName;
    private String email;
    private String status;
    private String remark;
}
