package com.example.officeoa.utils;

import lombok.Data;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 15:26 2021/3/9
 */
@Data
public class JsonResult {

    /**
     * 响应业务状态
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;

    public JsonResult() {}

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static JsonResult success() {
        JsonResult result = new JsonResult();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static JsonResult success(Object data) {
        JsonResult result = new JsonResult();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static JsonResult failure(ResultCode resultCode) {
        JsonResult result = new JsonResult();
        result.setResultCode(resultCode);
        return result;
    }

    public static JsonResult failure(ResultCode resultCode, Object data) {
        JsonResult result = new JsonResult();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    private void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }
}
