package com.example.officeoa.model.Bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 16:10 2021/3/9
 */
@Data
@NoArgsConstructor
public class UserInfoBo {
    private String username;
    private String password;
    private String role;
    private String departName;
    private String email;
    private String remark;
}
