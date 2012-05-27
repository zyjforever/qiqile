package com.zyj.qiqile.domain.bo;

import java.io.File;
import java.util.Date;
import java.util.List;

public class ActivityBO {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String CONTEXT = "context";
	public static final String STARTTIME = "startTime";
	public static final String ENDTIME = "endTime";
	public static final String PICURL = "picUrl";
	public static final String PICNAME = "picName";
	public static final String PEOPLELIMIT = "peopleLimit";
	public static final String AREAID = "areaId";
	public static final String LOCATION = "location";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String CITY = "city";
	public static final String USERID = "userId";
	public static final String TIME = "time";

	public static final String USERNAME = "userName";
	public static final String USERPICNAME = "userPicName";
	public static final String USERPICURL = "userPicUrl";
	public static final String JCOUNT = "jcount";
	public static final String CCOUNT = "ccount";

	private String id;
	private String name;
	private String context;
	private String userId;
	private String picUrl;
	private String picName;
	private Date startTime;
	private Date endTime;
	private String location;
	private Double latitude;// 经度
	private Double longitude;// 纬度
	private Integer peopleLimit; // 0,1,2,3 分别为：无限制，10人，20人，50人
	private String city;// 所在城市
	private Date time;// 活动发布时间

	private String userName;
	private String userPicName;
	private String userPicUrl;
	private Integer jcount;
	private Integer ccount;

	// 外加,用于编辑
	private File picFile;
	private Boolean newPic;

	private List<ActivityJoinBO> activityJoinBOList;
	private List<ActivityCommentBO> activityCommentBOList;

	private Boolean isJoin;
	
	public Boolean getNewPic() {
		return newPic;
	}

	public void setNewPic(Boolean newPic) {
		this.newPic = newPic;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getPeopleLimit() {
		return peopleLimit;
	}

	public void setPeopleLimit(Integer peopleLimit) {
		this.peopleLimit = peopleLimit;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public void setPicFile(File picFile) {
		this.picFile = picFile;
	}

	public File getPicFile() {
		return picFile;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPicName() {
		return userPicName;
	}

	public void setUserPicName(String userPicName) {
		this.userPicName = userPicName;
	}

	public String getUserPicUrl() {
		return userPicUrl;
	}

	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public Integer getJcount() {
		return jcount;
	}

	public void setJcount(Integer jcount) {
		this.jcount = jcount;
	}

	public Integer getCcount() {
		return ccount;
	}

	public void setCcount(Integer ccount) {
		this.ccount = ccount;
	}

	public List<ActivityJoinBO> getActivityJoinBOList() {
		return activityJoinBOList;
	}

	public void setActivityJoinBOList(List<ActivityJoinBO> activityJoinBOList) {
		this.activityJoinBOList = activityJoinBOList;
	}

	public List<ActivityCommentBO> getActivityCommentBOList() {
		return activityCommentBOList;
	}

	public void setActivityCommentBOList(
			List<ActivityCommentBO> activityCommentBOList) {
		this.activityCommentBOList = activityCommentBOList;
	}

	public Boolean getIsJoin() {
		return isJoin;
	}

	public void setIsJoin(Boolean isJoin) {
		this.isJoin = isJoin;
	}

}
