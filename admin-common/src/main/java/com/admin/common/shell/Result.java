package com.admin.common.shell;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 */
public class Result {
    private int code;

    private Exception exception;

    private String message;
    
    private String retmsg;

    public Result(int code, Exception exception, String message) {
        this.code = code;
        this.exception = exception;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public Exception getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getRetmsg() {
		return retmsg;
	}

	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", exception=" + exception + ", message=" + message + ", retmsg=" + retmsg
				+ "]";
	}

}
