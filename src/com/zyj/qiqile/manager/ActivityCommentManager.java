package com.zyj.qiqile.manager;

import com.zyj.qiqile.domain.bo.ActivityCommentBO;
import com.zyj.qiqile.task.TaskResult;

/** �йػ���۵�һЩ���� */
public interface ActivityCommentManager {


	/** ����һ������� */
	TaskResult addActivityComment(ActivityCommentBO activityCommentBO);

	/** ����ĳ����ĳ�췢���Ļ */
	TaskResult queryActivityCommentByActivityId(String activityId);

}
