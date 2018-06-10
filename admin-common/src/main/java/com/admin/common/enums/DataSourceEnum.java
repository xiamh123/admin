package com.admin.common.enums;
/**
 * 数据源枚举值
 * @author xiamh
 *
 */
public enum DataSourceEnum {
	
	/**STA层数据源*/
	STA("sta"),
	/**SDM层数据源*/
	SDM("sdm"),
	/**FDM层数据源*/
	FDM("fdm"),
	/**ADM层数据源*/
	ADM("adm");

	private String ds;
	
	private DataSourceEnum(String ds){
        this.ds = ds ;
    }
	
	/** 获取数据源枚举对应的字符串 */
	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}
	
}
