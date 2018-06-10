package com.admin.data.exception;

import com.admin.common.enums.ExceptionInterface;
import com.admin.common.exception.BatchException;

/**
 * 异常枚举值，这里定义系统错误相关的异常码，每层自己的异常码继承此枚举，然后自己定义
 * 
 * SYS开头 + 三位数字 － 系统错误码
 * STA开头 + 三位数字 － STA层错误码
 * SDM开头 + 三位数字 － SDM层错误码
 * FDM开头 + 三位数字 － FDM层错误码
 * ADM开头 + 三位数字 － ADM层错误码
 * @author xiamh
 *
 */
public enum StaExceptionEnum implements ExceptionInterface {
	
	STA0001("SYS0001","文件FILE_CONFIG_ID[{}]校验信息为空，失败"),
	STA0002("STA0002","FTP ok文件不存在[{}]"),
	STA0003("STA0003","SFTP ok文件不存在[{}]"),
	STA0004("STA0004","SFTP 校验文件失败[{}],{}"),
	STA0005("STA0005","ok文件不存在[{}]"),
	STA0006("STA0006","FILE_IS_EXISTS_SER：当前配置[{}]不支持，校验失败"),
	STA0007("STA0007","校验规则[{}]不支持"),
	STA0008("STA0008","文件配置信息为空，下载失败"),
	STA0009("STA0009","FTP 下载文件失败[{}]"),
	STA0010("STA0010","SFTP 下载文件失败[{}],{}"),
	STA0011("STA0011","文件不存在[{}]"),
	STA0012("STA0012","文件复制失败[{}]"),
	STA0013("STA0013","FILE_IS_EXISTS_SER：当前配置[{}]不支持，下载失败"),
	STA0014("STA0014","FILE_IS_EXISTS_SER：当前配置[{}]不支持，下载失败"),
	STA0015("STA0015","文件字段配置信息为空，无法导入数据"),
	STA0016("STA0016","脚本文件找不到:{}"),
	STA0017("STA0017","文件导入失败[{}]"),
	STA0018("STA0018","获取文件行数失败"),
	STA0019("STA0019","文件导入结束，但是步骤号不匹配，请检查!执行步骤号为[{}]"),
	STA0020("STA0020","文件[{}]分拆失败"),
	STA0021("STA0021","记录文件导入流水失败:文件名格式[{}],文件日期[{}]"),
	STA0022("STA0022","文件处理中，请稍后重试:文件名格式[{}],文件日期[{}]"),
	STA0023("STA0023","更新导入流水失败"),
	STA0024("STA0024","流水校验失败"),




	



	
	;

	private String msg;
	private String code;
	
	private StaExceptionEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return msg;
	}
	
	public static void main(String[] args) {
		BatchException be = new BatchException(StaExceptionEnum.STA0002);
		System.out.println(be.getCode());
		System.out.println(be.getMsg());
	}

}
