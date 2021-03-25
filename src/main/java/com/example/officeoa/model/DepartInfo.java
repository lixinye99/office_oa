package com.example.officeoa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 14:17 2021/3/9
 */
@Data
@NoArgsConstructor
@Table(name = "departinfo")
public class DepartInfo {
    @Column(name = "departId")
    @Id
    private Integer departId;
    @Column(name = "departName")
    private String departName;
    @Column(name = "principalUserId")
    private Integer principalUserId;
    @Column(name = "connectTelNum")
    private String connectTelNum;
    @Column(name = "parentId")
    private Integer parentId;
    @Column(name = "departDescribe")
    private String departDescribe;

    @Transient
    private List<DepartInfo> children;

    public DepartInfo(String departName) {
        this.departName = departName;
    }
}
