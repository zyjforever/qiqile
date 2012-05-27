package com.zyj.qiqile.domain.bo;

import java.util.Date;

public class ActivityCommentBO {
	public static final String ID = "id";
	public static final String ACTIVITY_ID = "activityId";
	public static final String USER_ID = "userId";
	public static final String ATTRIBUTE = "attribute";
	public static final String TIME="time";
	public static final String CONTEXT="context";
	public static final String USERNAME="userName";
	public static final String USERPICURL="userPicUrl";
	public static final String USERPICNAME="userPicName";
	
	
	private String id;
	private String context;
	private String userId;
	private String activityId;
	private Date time;
	private String attribute;

	private String userName;
	private String userPicUrl;
	private String userPicName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
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

	
}
