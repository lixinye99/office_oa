package com.example.officeoa.model.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 10:13 2021/3/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageInfoVo {
    private Integer mid;
    private String massageTitle;
    private String pusher;
    private String pushTime;
    private String pushObject;
    private String pushContent;
    private String messageStatus;
}
