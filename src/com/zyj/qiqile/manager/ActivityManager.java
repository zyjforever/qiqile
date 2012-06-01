package com.zyj.qiqile.manager;

import java.util.Date;

import com.zyj.qiqile.domain.bo.ActivityBO;
import com.zyj.qiqile.task.TaskResult;

/** �йػ��һЩ���� */
public interface ActivityManager {

	/** ����һ��� */
	TaskResult addActivity(ActivityBO activityBO);

	/** ����ĳ����ĳ�췢���Ļ */
	TaskResult queryActivityByCityAndTime(String city, Date time);
	
	/** ����һ��� */
	TaskResult updateActivity(ActivityBO activityBO);


}
