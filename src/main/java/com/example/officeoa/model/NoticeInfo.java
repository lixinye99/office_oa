package com.example.officeoa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 10:07 2021/3/22
 */
@Data
@NoArgsConstructor
@ToString
@Table(name = "noticeinfo")
public class NoticeInfo {
    @Id
    private Integer nid;
    @Column(name = "noticetitle")
    private String noticeTitle;
    @Column(name = "noticecontent")
    private String noticeContent;
    @Column(name = "createTime")
    private String createTime;
    @Column(name = "releaser")
    private Integer releaser;
}
