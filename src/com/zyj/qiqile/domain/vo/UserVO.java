package com.zyj.qiqile.domain.vo;

import java.sql.Date;

public class UserVO {

	public static final String USERID="userId";
	public static final String USERNAME="userName";
	public static final String USERPICURL="userPicUrl";
	public static final String USERPICNAME="userPicName";
	public static final String USERBIRTHDAY="userBirthday";
	public static final String USERSIGNATURE="userSignature";
	public static final String USERSEX="userSex";
	
	private String userId;
	private String userName;
	private String userPicUrl;
	private String userPicName;
	private Date userBirthday;
	private String userSignature;
	private Integer userSex;// 1:ÄÐ 0:Å® -1ÆäËû
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPicUrl() {
		return userPicUrl;
	}
	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}
	public String getUserPicName() {
		return userPicName;
	}
	public void setUserPicName(String userPicName) {
		this.userPicName = userPicName;
	}
	public Date getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getUserSignature() {
		return userSignature;
	}
	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}
	public Integer getUserSex() {
		return userSex;
	}
	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
