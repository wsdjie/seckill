package com.dayup.seckil.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity //实体类与表
@Table(name = "user")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5048322465399973366L;

	@Id
	@NotBlank(message = "用户名不可以为空")
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	@Size(min = 4,message = "密码长度应在4到6之间")
	private String password;
	
	@Column(name = "id", nullable = false)
	private Integer id;
	
	private String repassword;
	
	@Column(name = "dbflag")
	private String dbflag;

	public String getDbflag() {
		return dbflag;
	}

	public void setDbflag(String dbflag) {
		this.dbflag = dbflag;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public User() {}
	
	public User(String username, String password, Integer id) {
		this.username = username;
		this.password = password;
		this.id= id;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
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

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", id=" + id + "]";
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
