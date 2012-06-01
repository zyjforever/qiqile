package com.zyj.qiqile.manager;

import java.util.Date;

import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.task.TaskResult;

/** 有关活动的一些操作 */
public interface ActivityManager {

	/** 增加一个活动 */
	TaskResult addActivity(ActivityBO activityBO);

	/** 查找某城市某天发布的活动 */
	TaskResult queryActivityByCityAndTime(String city, Date time);
	
	/** 增加一个活动 */
	TaskResult updateActivity(ActivityBO activityBO);


}
