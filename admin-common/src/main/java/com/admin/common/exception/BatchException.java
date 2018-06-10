package com.admin.common.exception;

import com.admin.common.enums.ExceptionInterface;

/**
 * 批量异常类
 * @author xiamh
 *
 */
public class BatchException extends Exception {
	
	private String code;		//返回码
	private String msg;			//返回信息

	/**
	 * 异常类构造方法
	 * @param e			错误码对象
	 * @param params	参数
	 */
	public BatchException(ExceptionInterface e, Object... params) {
		this.code = e.getCode();
		if(params != null && params.length > 0) {
			String sb = e.getMsg();
			for(Object str : params) {
				sb = sb.replaceFirst("\\{\\}", String.valueOf(str));
			}
			this.msg = sb;
		}else {
			this.msg = e.getMsg();
		}
	}

	/**
	 * 异常类构造方法
	 * @param code		返回码
	 * @param msg		返回信息
	 */
	public BatchException(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "BatchException{" +
				"code='" + code + '\'' +
				", msg='" + msg + '\'' +
				'}';
	}
}
