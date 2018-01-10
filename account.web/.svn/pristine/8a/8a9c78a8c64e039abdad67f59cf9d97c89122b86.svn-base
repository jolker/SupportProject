package com.bliss.account.model;

import java.io.Serializable;
import java.util.Date;


public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum Status {
		PENDING, ACTIVED, DISABLED
	}
	
	public enum Role {
		USER,
		ADMIN
	}
	
	private int id;
	
	private String userName;
	
	private String passWord;
	
	private Status status;
	
	private Role role;
	
	private Date expiredAt;
	
	private Date createdAt;
	
	private Date updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
