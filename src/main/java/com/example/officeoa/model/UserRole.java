package com.example.officeoa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 9:04 2021/3/12
 */
@Data
@NoArgsConstructor
@Table(name = "user_role")
@ToString
public class UserRole {
    @Id
    private Integer id;
    private Integer uid;
    private Integer rid;

}
