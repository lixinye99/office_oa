package com.example.officeoa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 13:08 2021/3/8
 */
@Data
@NoArgsConstructor
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rid;
    @Column(name = "roleName")
    private String roleName;
    @Column(name = "roleNameZh")
    private String roleNameZh;
    @Column(name = "roleIntroduction")
    private String roleIntroduction;
}
