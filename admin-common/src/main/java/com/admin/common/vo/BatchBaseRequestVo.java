package com.admin.common.vo;

/**
 * 后台数据跑批请求参数
 * @author xiamh
 *
 */
public class BatchBaseRequestVo {
	
	private String date;				//跑批日期
	private String repeatFlag;			//跑批标志：00-正常跑批、01-跑单个步骤、02-重跑所有步骤、03-重跑单个步骤
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRepeatFlag() {
		return repeatFlag;
	}
	public void setRepeatFlag(String repeatFlag) {
		this.repeatFlag = repeatFlag;
	}

	@Override
	public String toString() {
		return "BatchBaseRequestVo{" +
				"date='" + date + '\'' +
				", repeatFlag='" + repeatFlag + '\'' +
				'}';
	}
}
