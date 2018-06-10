package com.admin.common.enums;

/**
 * 异常处理接口，定义的枚举实现此接口，用于统一调用
 * @author xiamh
 *
 */
public interface ExceptionInterface {
	
	/**
	 * 获取异常码
	 * @return
	 */
	public String getCode();
	
	/**
	 * 获取异常信息
	 * @return
	 */
	public String getMsg();
	
}
