package com.example.officeoa.model.Vo;

import com.example.officeoa.model.DepartInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description: 级联的部门
 * @Date: Create in 9:37 2021/3/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepartInfoVo {
    private String value;
    private String label;
    private List<DepartInfoVo> children;

    /**
     * 定义静态方法把部门的pojo类修改为要展示的vo类
     * @param departInfos
     * @return
     */
    public static void changeDepartPojoToVo(List<DepartInfo> departInfos, List<DepartInfoVo> departInfoVos){
        for(DepartInfo departInfo : departInfos){
            DepartInfoVo departInfoVo = new DepartInfoVo();
            departInfoVo.setValue(departInfo.getDepartId().toString());
            departInfoVo.setLabel(departInfo.getDepartName());
            if(departInfo.getChildren().size() != 0){
                departInfoVo.setChildren(new ArrayList<>());
                changeDepartPojoToVo(departInfo.getChildren(), departInfoVo.getChildren());
            }else {
                departInfoVo.setChildren(null);
            }
            departInfoVos.add(departInfoVo);
        }
    }
}
