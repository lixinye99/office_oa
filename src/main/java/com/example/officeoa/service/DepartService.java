package com.example.officeoa.service;

import com.example.officeoa.model.DepartInfo;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 16:31 2021/3/9
 */
public interface DepartService {

    /**
     * 根据部门名称查询部门id
     * @param name
     * @return
     */
    Integer queryDepartIdByName(String name);

    /**
     * 根据部门id查找部门信息
     * @param id
     * @return
     */
    DepartInfo queryDepartInfoById(Integer id);

    /**
     * 查询所有的部门信息
     * @return
     */
    List<DepartInfo> queryAllDepartInfo();


    /**
     * 按级联的样式查找所有的部门
     * @return
     */
    List<DepartInfo> queryDepart();

    /**
     * 根据部门id查找出部门的详细信息（包括上级部门）,返回字符串，由级联部门的id组成
     * @param departId
     * @return
     */
    String queryDetailByDepartId(Integer departId);

    /**
     * 插入部门信息
     * @param departInfo
     * @return
     */
    boolean insertDepartInfo(DepartInfo departInfo);

    /**
     * 根据部门编号删除部门信息
     * @param departId
     * @return
     */
    boolean deleteDepartById(Integer departId);

    /**
     * 更新部门的信息
     * @param departInfo
     * @return
     */
    boolean updataDepartInfo(DepartInfo departInfo);
}
