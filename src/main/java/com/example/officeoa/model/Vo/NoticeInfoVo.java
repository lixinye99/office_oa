package com.example.officeoa.model.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 10:12 2021/3/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoticeInfoVo {
    private Integer nid;
    private String title;
    private String content;
    private String createTime;
    private String releaser;
}
