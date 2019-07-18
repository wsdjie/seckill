package com.dayup.seckil.base.result;

public enum ResultCode {

	SUCCESS(200,"成功"),
	
	FAIL(500,"失败"),
	
	USER_LOGIN_ERROR(500201,"登录失败，用户名或密码出错，请重新输入。"),
	USER_HAS_EXISTED(500202,"用户已存在，请试试其他用户名。"),
	USER_NOT_LOGIN(500203,"用户未登录或者登录已失效，请重新登陆。");
	
	private Integer code;
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;
	
	private ResultCode(Integer code,String message) {
		this.code = code;
		this.message = message;
	}
}
