package com.dayup.seckil.VO;

import java.io.Serializable;

public class userVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5938124511828346229L;
	
	private String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getDbflag() {
		return dbflag;
	}
	public void setDbflag(String dbflag) {
		this.dbflag = dbflag;
	}
	private String password;
	private Integer id;
	private String repassword;
	private String dbflag;
}
