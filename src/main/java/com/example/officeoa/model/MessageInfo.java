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
 * @Date: Create in 17:38 2021/3/16
 */
@Data
@NoArgsConstructor
@Table(name = "messageinfo")
@ToString
public class MessageInfo {
    @Id
    private Integer mid;
    @Column(name = "massagetitle")
    private String  massageTitle;
    @Column(name = "pusher")
    private Integer  pusher;
    @Column(name = "pushtime")
    private String pushTime;
    @Column(name = "pushtype")
    private Integer pushType;
    @Column(name = "pushobject")
    private Integer pushObject;
    @Column(name = "pushcontent")
    private String pushContent;
    @Column(name = "messagestatus")
    private Integer messageStatus;
}
