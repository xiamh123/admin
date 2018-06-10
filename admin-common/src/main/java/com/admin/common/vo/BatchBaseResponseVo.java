package com.admin.common.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiamh
 * @date 18/3/27
 */
public class BatchBaseResponseVo {

    private String retFlag;         //处理标识：SYS0000-处理成功，SYS0001-校验失败, 其他失败－错误明细信息见exceptionList错误列表
    private List<Map<String,String>> exceptionList = new ArrayList<Map<String,String>>();


    public String getRetFlag() {
        return retFlag;
    }

    public void setRetFlag(String retFlag) {
        this.retFlag = retFlag;
    }

    public List<Map<String, String>> getExceptionList() {
        return exceptionList;
    }

    public void setExceptionList(List<Map<String, String>> exceptionList) {
        this.exceptionList = exceptionList;
    }


    @Override
    public String toString() {
        return "BatchBaseResponseVo{" +
                "retFlag='" + retFlag + '\'' +
                ", exceptionList=" + exceptionList +
                '}';
    }
}
