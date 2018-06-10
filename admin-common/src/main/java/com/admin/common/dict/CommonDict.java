package com.admin.common.dict;

/**
 * 公共数据字典
 * 
 * @author hepeng
 * @date 2018/3/22 14:42
 */
public enum CommonDict {

    /** 执行状态 */
    EXECUTE_STATUS_00("00","未执行"),
    EXECUTE_STATUS_01("01","执行中"),
    EXECUTE_STATUS_02("02","执行成功"),
    EXECUTE_STATUS_03("03","执行失败"),

    /**  是否执行 */
    EXECUTE_0("0","否"),
    EXECUTE_1("1","是"),

    /** 跑批标识 */
    REPEAT_FLAG_00("00","正常跑批"),
    REPEAT_FLAG_01("01","跑单个步骤"),
    REPEAT_FLAG_02("02","重跑所有步骤"),
    REPEAT_FLAG_03("03","重跑单个步骤"),

    /**  业务种类 */
    BUSI_TYPE_00("00","存款"),
    BUSI_TYPE_01("01","贷款"),
    BUSI_TYPE_02("02",""),
    BUSI_TYPE_03("03",""),

    ;

    CommonDict(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
