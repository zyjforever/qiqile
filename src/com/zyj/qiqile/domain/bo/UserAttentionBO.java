package com.zyj.qiqile.domain.bo;

import java.sql.Date;

import com.zyj.qiqile.domain.vo.UserVO;

public class UserAttentionBO {

	public static final String USERID = "userId";
	public static final String USERNAME = "userName";
	public static final String USERPICURL = "userPicUrl";
	public static final String USERPICNAME = "userPicName";
	public static final String USERBIRTHDAY = "userBirthday";
	public static final String USERSIGNATURE = "userSignature";
	public static final String USERSEX = "userSex";
	public static final String ATTENTIONID = "attentionId";
	public static final String ATTRIBUTE = "attribute";

	private String attentionId;
	private String attribute;

	private UserVO userVO;

	public String getAttentionId() {
		return attentionId;
	}

	public void setAttentionId(String attentionId) {
		this.attentionId = attentionId;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

}
