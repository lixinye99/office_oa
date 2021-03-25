package com.example.officeoa.model.enumeration;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 9:33 2021/3/10
 */
public enum Status {
    UNLOCK(0,"未锁定"),
    LOCK(1,"已锁定"),
    WAIT_EXAMINE(2,"待审核"),
    PASS(3,"已通过"),
    NOPASS(4,"未通过"),
    WORK_LATE(5,"迟到"),
    LEAVE_EARLY(6,"早退"),
    LEAT_LEAVE(7,"迟到并早退"),
    NORMAL(8,"正常")
    ;

    private int code;
    private String status;

    Status(int i, String status) {
        this.code = i;
        this.status = status;
    }

    /**
     * getValue方法根据输入的参数的类型，决定获取值的类型
     * @param msg
     * @return
     */
    public static int getValue(String msg){
        for(Status s : Status.values()){
            if(s.getStatus().equals(msg))
                return s.getCode();
        }
        return -1;
    }

    public static String getValue(int code){
        for(Status s : Status.values()){
            if(s.getCode() == code)
                return s.getStatus();
        }
        return null;
    }



    public int getCode(){
        return code;
    }
    public String getStatus(){
        return status;
    }
}
