package com.example.officeoa.service.impl;

import com.example.officeoa.mapper.DepartMapper;
import com.example.officeoa.model.DepartInfo;
import com.example.officeoa.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 16:31 2021/3/9
 */
@Service
public class DepartServiceImpl implements DepartService {

    @Autowired
    private DepartMapper departMapper;

    @Override
    public Integer queryDepartIdByName(String name) {
        DepartInfo departInfo = departMapper.selectOne(new DepartInfo(name));
        return departInfo != null ? departInfo.getDepartId() : -1;
    }

    @Override
    public DepartInfo queryDepartInfoById(Integer id) {
        DepartInfo departInfo = new DepartInfo();
        departInfo.setDepartId(id);
        DepartInfo detailInfo = departMapper.selectOne(departInfo);
        return detailInfo;
    }

    @Override
    public List<DepartInfo> queryAllDepartInfo() {
        List<DepartInfo> departInfos = departMapper.selectAll();
        return departInfos;
    }

    @Override
    public List<DepartInfo> queryDepart() {
        DepartInfo departInfo = new DepartInfo();
        departInfo.setParentId(0);
        //查询所有的一级标题
        List<DepartInfo> departOne = departMapper.select(departInfo);
        queryChildrenDepart(departOne);
        return departOne;
    }

    /**
     * 根据id查询部门的详细信息（包括上级部门）
     * @param departId
     * @return
     */
    @Override
    public String queryDetailByDepartId(Integer departId) {
        String detail = "";
        detail = queryDetailDepart(departId,detail);
        detail = detail.substring(0,detail.length()-1);
        detail= new StringBuffer(detail).reverse().toString();
        return detail;
    }

    @Override
    public boolean insertDepartInfo(DepartInfo departInfo) {
        int count = departMapper.insert(departInfo);
        return  count == 1 ? true : false;
    }

    @Override
    public boolean deleteDepartById(Integer departId) {
        int count = departMapper.deleteByPrimaryKey(departId);
        return count == 1 ? true : false;
    }

    @Override
    public boolean updataDepartInfo(DepartInfo departInfo) {
        int count = departMapper.updateByPrimaryKeySelective(departInfo);
        return count == 1 ? true : false;
    }


    /**
     * 迭代查找所有的部门的子部门
     * @param departInfos
     * @return
     */
    public void queryChildrenDepart(List<DepartInfo> departInfos){
        if(departInfos.size() == 0)
            return;
        for(DepartInfo departInfo : departInfos){
            //根据一级部门的id查找各自的子部门
            DepartInfo select = new DepartInfo();
            select.setParentId(departInfo.getDepartId());
            List<DepartInfo> children = departMapper.select(select);
            departInfo.setChildren(children);
            //迭代查找子部门的部门
            queryChildrenDepart(children);
        }
    }

    /**
     * 迭代查找部门的详细信息（包括上级部门）
     * @param departId
     */
    public String queryDetailDepart(Integer departId,String detail){
        //根据部门id找出这个部门
        DepartInfo departInfo = new DepartInfo();
        departInfo.setDepartId(departId);
        DepartInfo selectOne = departMapper.selectOne(departInfo);
        detail += selectOne.getDepartId()+",";
        //如果此部门的上级部门的id不为0，则还需要向上查找
        if(selectOne.getParentId() != 0){
            detail = queryDetailDepart(selectOne.getParentId(),detail);
        }
        return detail;
    }
}
