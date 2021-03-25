package com.example.officeoa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 16:07 2021/3/24
 */
@Data
@NoArgsConstructor
@Table(name = "attendanceinfo")
public class AttendanceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aid;
    private Integer nid;
    @Column(name = "worktime")
    private Integer workTime;
    @Column(name = "closingtime")
    private Integer closingTime;
    @Column(name = "createtime")
    private String createTime;
    @Column(name = "type")
    private Integer type;
}
