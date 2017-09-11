package com.putaoteng.task8.model;

public class Verification extends BasicVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1717284433834485769L;
	
	private long id;
	private String phoneNumber;
	private String code;
	private String email;
	private byte emailVerification;
	private long createAt;
	private long updateAt;
	private String createBy;
	private String updateBy;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public byte getEmailVerification() {
		return emailVerification;
	}
	public void setEmailVerification(byte emailVerification) {
		this.emailVerification = emailVerification;
	}
	public long getCreateAt() {
		return createAt;
	}
	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}
	public long getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(long updateAt) {
		this.updateAt = updateAt;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Override
	public String toString() {
		return "Verification [id=" + id + ", phoneNumber=" + phoneNumber + ", code=" + code + ", email=" + email
				+ ", emailVerification=" + emailVerification + ", createAt=" + createAt + ", updateAt=" + updateAt
				+ ", createBy=" + createBy + ", updateBy=" + updateBy + "]";
	}
}
