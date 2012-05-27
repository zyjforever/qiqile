package com.zyj.qiqile.domain.bo;

import com.zyj.qiqile.domain.vo.UserVO;

public class ActivityJoinBO {

	public static final String ID = "id";
	public static final String ACTIVITY_ID = "activityId";
	public static final String USER_ID = "userId";
	public static final String ATTRIBUTE = "attribute";

	private String id;
	private String activityId;
	private String userId;
	private String attribute;

	private final UserVO userVO=new UserVO();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

}
