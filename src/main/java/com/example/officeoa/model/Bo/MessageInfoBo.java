package com.example.officeoa.model.Bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 8:51 2021/3/17
 */
@Data
@NoArgsConstructor
public class MessageInfoBo {
    private String massageTitle;
    private String pushContent;
    private String[] pushObject;
    private String pushUserName;
}
