package com.zyj.qiqile.app;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.zyj.qiqile.activity.QiqileMainActivity;
import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.domain.bo.UserAttentionBO;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.manager.ActivityJoinManager;
import com.zyj.qiqile.manager.ActivityManager;
import com.zyj.qiqile.manager.UserManager;
import com.zyj.qiqile.manager.impl.ActivityJoinManagerImpl;
import com.zyj.qiqile.manager.impl.ActivityManagerImpl;
import com.zyj.qiqile.manager.impl.UserManagerImpl;

public class QiqileApplication extends Application {

	private static final String TAG = "QiqileApplication";
	private QiqileMainActivity mainActivity;
	private static QiqileApplication instance;
	public static Context context;
	private Boolean isLogin = Boolean.FALSE;// 用户是否登陆
	private Boolean isMapList = Boolean.FALSE; // activityList 是否map页面
	private UserBO userBO;// 用户个人信息
	private String token;
	private ActivityBO currentEditActivity; // 当前编辑的新活动
	private ActivityBO currentLookActivity;// 当前正在查看的活动
	private Boolean checkLogin = Boolean.FALSE;
	private String email;
	private String password;
	private String city;// 当前所在城市

	private Map<String, List<List<ActivityBO>>> activityCityMapList;
	private List<UserAttentionBO> myAttentionList;

	public ActivityBO getCurrentLookActivity() {
		return currentLookActivity;
	}

	public void setCurrentLookActivity(ActivityBO currentLookActivity) {
		this.currentLookActivity = currentLookActivity;
	}

	private UserManager userManager;
	private ActivityManager activityManager;
	private ActivityJoinManager activityJoinManager;

	private QiqileApplication() {
		userManager = new UserManagerImpl();
		activityManager = new ActivityManagerImpl();
		activityJoinManager = new ActivityJoinManagerImpl();
		activityCityMapList = new ConcurrentHashMap<String, List<List<ActivityBO>>>();
	}

	public static QiqileApplication getInstance() {
		if (null == instance) {
			instance = new QiqileApplication();
		}
		return instance;
	}

	public Boolean isLogin() {
		return isLogin;
	}

	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}

	public Boolean getIsMapList() {
		return isMapList;
	}

	public void setIsMapList(Boolean isMapList) {
		this.isMapList = isMapList;
	}

	public UserBO getUserBO() {
		return userBO;
	}

	public void setUserBO(UserBO userBO) {
		this.userBO = userBO;
	}

	/** 返回当前正在编辑的活动（新建、修改） */
	public ActivityBO getCurrentEditActivity() {
		if (currentEditActivity == null) {
			currentEditActivity = new ActivityBO();
		}
		return currentEditActivity;
	}

	public void setCurrentEditActivity(ActivityBO currentEditActivity) {
		this.currentEditActivity = currentEditActivity;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public ActivityJoinManager getActivityJoinManager() {
		return activityJoinManager;
	}

	public void setActivityJoinManager(ActivityJoinManager activityJoinManager) {
		this.activityJoinManager = activityJoinManager;
	}

	public Boolean getCheckLogin() {
		return checkLogin;
	}

	public void setCheckLogin(Boolean checkLogin) {
		this.checkLogin = checkLogin;
	}

	public ActivityManager getActivityManager() {
		return activityManager;
	}

	public void setActivityManager(ActivityManager activityManager) {
		this.activityManager = activityManager;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Map<String, List<List<ActivityBO>>> getActivityCityMapList() {
		return activityCityMapList;
	}

	public void setActivityCityMapList(
			Map<String, List<List<ActivityBO>>> activityCityMapList) {
		this.activityCityMapList = activityCityMapList;
	}

	public QiqileMainActivity getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(QiqileMainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public List<UserAttentionBO> getMyAttentionList() {
		return myAttentionList;
	}

	public void setMyAttentionList(List<UserAttentionBO> myAttentionList) {
		this.myAttentionList = myAttentionList;
	}

}
