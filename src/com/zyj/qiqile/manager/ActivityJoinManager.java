package com.zyj.qiqile.manager;

import com.zyj.qiqile.domain.bo.ActivityJoinBO;
import com.zyj.qiqile.task.TaskResult;

/** �йػ��һЩ���� */
public interface ActivityJoinManager {

	/** ����һ��� */
	TaskResult join(ActivityJoinBO activityJoinBO);

	/** ȡ������һ��� */
	TaskResult cancelJoin(ActivityJoinBO activityJoinBO);

	/** �Ƿ��Ѽ���һ��� */
	TaskResult isJoin(ActivityJoinBO activityJoinBO);

	/** �г����вμ��� */
	TaskResult queryActivityJoinBOByActivityId(String activityId);


}
