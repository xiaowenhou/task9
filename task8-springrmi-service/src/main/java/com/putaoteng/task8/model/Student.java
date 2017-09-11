package com.putaoteng.task8.model;

public class Student extends BasicVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = -914011201731053462L;
	
	private long id;
	private String name;
	private long phoneNumber;
	private long qqNumber;
	private String email;
	private String profession;
	private String joinDate;
	private String school;
	private String onlineNumber;
	private String dailyLink;
	private String desire;
	private String msgSource;
	private String brother;
	private String image;
	private long createAt;
	private long updateAt;
	
	public Student() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public long getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(long qqNumber) {
		this.qqNumber = qqNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getOnlineNumber() {
		return onlineNumber;
	}

	public void setOnlineNumber(String onlineNumber) {
		this.onlineNumber = onlineNumber;
	}

	public String getDailyLink() {
		return dailyLink;
	}

	public void setDailyLink(String dailyLink) {
		this.dailyLink = dailyLink;
	}

	public String getDesire() {
		return desire;
	}

	public void setDesire(String desire) {
		this.desire = desire;
	}

	public String getMsgSource() {
		return msgSource;
	}

	public void setMsgSource(String msgSource) {
		this.msgSource = msgSource;
	}

	public String getBrother() {
		return brother;
	}

	public void setBrother(String brother) {
		this.brother = brother;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", qqNumber=" + qqNumber
				+ ", email=" + email + ", profession=" + profession + ", joinDate=" + joinDate + ", school=" + school
				+ ", onlineNumber=" + onlineNumber + ", dailyLink=" + dailyLink + ", desire=" + desire + ", msgSource="
				+ msgSource + ", brother=" + brother + ", image=" + image + ", createAt=" + createAt + ", updateAt="
				+ updateAt + "]";
	}
}
