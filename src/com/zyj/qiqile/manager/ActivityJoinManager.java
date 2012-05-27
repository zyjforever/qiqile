package com.zyj.qiqile.manager;

import com.zyj.qiqile.domain.bo.ActivityJoinBO;
import com.zyj.qiqile.task.TaskResult;

/** 有关活动的一些操作 */
public interface ActivityJoinManager {

	/** 加入一个活动 */
	TaskResult join(ActivityJoinBO activityJoinBO);

	/** 取消加入一个活动 */
	TaskResult cancelJoin(ActivityJoinBO activityJoinBO);

	/** 是否已加入一个活动 */
	TaskResult isJoin(ActivityJoinBO activityJoinBO);

	/** 列出所有参加者 */
	TaskResult queryActivityJoinBOByActivityId(String activityId);


}
