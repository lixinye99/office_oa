package com.example.officeoa.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 10:34 2021/3/22
 */
@Data
@NoArgsConstructor
@ToString
public class UploadResult {
    private String url;
    private String alt;
    private String href;
}
