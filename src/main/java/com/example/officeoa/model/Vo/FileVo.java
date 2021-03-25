package com.example.officeoa.model.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 16:35 2021/3/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileVo {
    private String fileName;
    private String downloadUrl;
    private String fileSize;
    private String updateTime;
}
