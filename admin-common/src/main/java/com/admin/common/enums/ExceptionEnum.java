package com.admin.common.enums;
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
public enum ExceptionEnum implements ExceptionInterface{
	
	SYS0000("SYS0000","处理成功"),
	SYS0001("SYS0001","校验失败"),
	SYS0002("SYS0002","流程处理失败"),
	SYS5555("SYS5555","处理中"),
	SYS9999("SYS9999","系统开小差"),
	
	
	
	
	;
	
	private String msg;
	private String code;
	
	private ExceptionEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return msg;
	}
	
	public static void main(String[] args) {
		ExceptionInterface tmp = ExceptionEnum.SYS9999;
		System.out.println(tmp.getCode());
		System.out.println(tmp.getMsg());
		
		
	}

}
