package com.example.officeoa.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.officeoa.model.Vo.FileVo;
import com.example.officeoa.utils.JsonResult;
import com.example.officeoa.utils.OssUtil;
import com.example.officeoa.utils.ResultCode;
import com.example.officeoa.utils.UploadResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 10:17 2021/3/22
 */
@RestController
public class FileController {

    @Autowired
    OssUtil ossUtil;

    /**
     * 权限判断是否是管理员
     * @return
     */
    public static boolean hasAdminRole(){
        //获取登录用户的权限，只有用ADMIN权限的用户才可以增删改查用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean role_admin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return role_admin;
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("/uploadImg")
    public JSONObject uploadImg(MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        UploadResult result = new UploadResult();
        List<UploadResult> uploadResults = new ArrayList<>();
        try {
            String url = ossUtil.uploadImg(file); //调用OSS工具类
            result.setUrl(url);
            result.setAlt("");
            result.setHref("");
            uploadResults.add(result);
            jsonObject.put("errno",0);
            jsonObject.put("data",uploadResults);
            return jsonObject;
        }
        catch (Exception e) {
            jsonObject.put("errno",1);
            return jsonObject;
        }
    }

    /**
     * 获取指定路径下的所有文件夹
     * @param path
     * @return
     */
    @GetMapping("/getFileFolder")
    public JsonResult getAllFolder(String path,Principal principal){
        if("personal".equals(path)){
            path = path + "/" + principal.getName();
        }
        if(ossUtil.checkFolderIsExist(path+"/")){
            List<String> fileDir = ossUtil.fileFolder(path);
            return JsonResult.success(fileDir);
        }else{
            String folder = ossUtil.createFolder(path + "/");
            return !StringUtils.isBlank(folder) ? JsonResult.success() : JsonResult.failure(ResultCode.FOLDER_CREATE_FAIL);
        }
    }

    /**
     * 创建文件夹
     * @param folderName
     * @return
     */
    @PostMapping("/createFolder")
    public JsonResult createFolder(String folderName, Principal principal){
        String folder = ossUtil.createFolder("personal/"+principal.getName()+"/"+folderName+"/");
        return !StringUtils.isBlank(folder) ? JsonResult.success() : JsonResult.failure(ResultCode.FOLDER_CREATE_FAIL);
    }

    /**
     * 删除文件夹
     * @param folderName
     * @return
     */
    @DeleteMapping("/deleteFolder")
    public JsonResult deleteFolder(String[] folderName){
        if(folderName[0].contains("public/")){
            if(!hasAdminRole()){
                return JsonResult.failure(ResultCode.PERMISSION_NO_ACCESS);
            }
        }
        Boolean deleteResult = ossUtil.delFileList(Arrays.asList(folderName));
        return deleteResult ? JsonResult.success() : JsonResult.failure(ResultCode.FOLDER_DELETE_FAIL);
    }

    /**
     * 获取路径下的所有文件
     * @param path
     * @param principal
     * @return
     */
    @GetMapping("/getFileInFolder")
    public JsonResult getFileInFolder(String path,String type,Principal principal){
        if("personal".equals(type)) {
            path = type+"/"+principal.getName()+"/"+path;
        }else{
            path = type+"/"+path;
        }
        List<FileVo> fileVos = ossUtil.getFileDetail(path);
        return fileVos != null ? JsonResult.success(fileVos) : JsonResult.failure(ResultCode.RESULE_DATA_NONE);
    }

    /**
     * 上传文件（可上传多个文件）
     * @param file
     * @param path
     * @param type
     * @param principal
     * @return
     */
    @PostMapping("/uploadFile")
    public JsonResult uploadFileToPersonalSpace(MultipartFile[] file,String path,String type,Principal principal){
        if("personal".equals(type)){
            path = type + "/" + principal.getName() +"/"+ path;
        }else{
            path = type + "/" + path;
        }
        Boolean uploadResult = ossUtil.uploadFiles(file,path);
        return uploadResult ? JsonResult.success() : JsonResult.failure(ResultCode.UPLOAD_FILE_FAIL);
    }

    /**
     * 删除文件（可删除多个）
     * @param fileNameList
     * @param path
     * @param type
     * @param principal
     * @return
     */
    @DeleteMapping("/deleteFile")
    public JsonResult deleteFile(String[] fileNameList,String path,String type,Principal principal){
        if((  null == fileNameList || fileNameList.length == 0) || StringUtils.isBlank(type) || StringUtils.isBlank(path) )
            return JsonResult.failure(ResultCode.PARAM_IS_BLANK);

        if("personal".equals(type)){
            path = type + "/" + principal.getName() + "/" + path + "/";
        }else{
            path = type + "/" + path + "/";
        }

        List<String> nameList = new ArrayList<>();
        for(String s : fileNameList){
            String name = path + s;
            nameList.add(name);
        }
        Boolean deleteResult = ossUtil.delFileList(nameList);
        return deleteResult ?  JsonResult.success() : JsonResult.failure(ResultCode.DELETE_ERROR);
    }
}
