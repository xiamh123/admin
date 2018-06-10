package com.admin.data.dict;
/**
 * STA层数据字典枚举值
 * @author xiamh
 *
 */
public enum StaDictEnum {
	
	/**  通用数据字典 00-否，01-是 */
	COMMON_00("00","否"),
	COMMON_01("01","是"),
	
	/** STA_FILE_CONFIG_INFO－文件信息表数据字典 */
	/**所属系统*/
	ATTACH_SYSTEM_00("00","存款"),
	ATTACH_SYSTEM_01("01","贷款"),
	
	/**文件服务器传输类型*/
	REMOTE_TRANSFER_00("00","FTP"),
	REMOTE_TRANSFER_01("01","SFTP"),
	
	/**状态*/
	STATUS_00("00","停止导入"),
	STATUS_01("01","正常"),
	
	/** STA_FILE_IMPORT_LOG - 文件导入流水表数据字典 */
	/**文件类型*/
	FILE_TYPE_O("O","发送"),
	FILE_TYPE_I("I","接收"),
	
	/** 流程执行步骤 */
	EXEC_STEP_NO_10("10","ok文件校验"),
	EXEC_STEP_NO_20("20","清空表"),
	EXEC_STEP_NO_30("30","文件下载"),
	EXEC_STEP_NO_31("31","分拆文件"),
	EXEC_STEP_NO_40("40","数据导入"),
	EXEC_STEP_NO_99("99","文件导入成功"),
	
	/** 处理状态 */
	STATUS_LOG_00("00","文件导入成功"),
	STATUS_LOG_01("01","文件导入失败"),
	STATUS_LOG_98("98","文件导入处理中"),
	STATUS_LOG_99("99","文件不存在"),
	
	/** STA_FILE_COL_CONFIG－文件字段配置表数据字典 */
	
	
	/** STA_FILE_CHECK_INFO-文件校验信息表数据字典 */
	CHECK_RULE_00("00","OK文件生成"),
	CHECK_RULE_01("01","结果文件解析"),


	/** 文件分拆数据字典 */
	SPLIT_STATUS_00("00","文件分拆成功"),
	SPLIT_STATUS_01("01","文件导入成功"),
	SPLIT_STATUS_02("02","文件导入失败"),

	
	/** 其余标示 */
	/**数据层重跑标示*/
	REPEATFLAG_00("00","非重跑，检测当前日期已经跑批则不再执行"),
	REPEATFLAG_01("01","根据文件格式名和日期跑批(单个文件)"),
	REPEATFLAG_02("02","当前日期或上送日期所有文件重跑"),
	REPEATFLAG_03("03","根据文件格式名和日期重跑单个文件"),
	
	
	/** 调用shell脚本成功返回码－0 */
	SHELL_RET_CODE_0("0","成功");
	
	;
	private String key;
	private String value;
	
	private StaDictEnum(String key,String value){
        this.key = key ;
        this.value = value;
    }

	public String getKey() {
		return key;
	}

}
