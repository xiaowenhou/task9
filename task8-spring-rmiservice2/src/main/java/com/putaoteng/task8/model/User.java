package com.putaoteng.task8.model;

public class User extends BasicVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6141796575016877660L;
	
	private long id;
	private String username;
	private String password;
	private long phoneNumber;
	private String email;
	private long loginAt;
	private long createAt;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getLoginAt() {
		return loginAt;
	}
	public void setLoginAt(long loginAt) {
		this.loginAt = loginAt;
	}
	public long getCreateAt() {
		return createAt;
	}
	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", phoneNumber=" + phoneNumber
				+ ", email=" + email + ", loginAt=" + loginAt + ", createAt=" + createAt + "]";
	}
	
}
