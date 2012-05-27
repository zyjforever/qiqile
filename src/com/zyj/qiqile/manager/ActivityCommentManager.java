package com.zyj.qiqile.manager;

import com.zyj.qiqile.domain.bo.ActivityCommentBO;
import com.zyj.qiqile.task.TaskResult;

/** 有关活动评论的一些操作 */
public interface ActivityCommentManager {


	/** 增加一个活动评论 */
	TaskResult addActivityComment(ActivityCommentBO activityCommentBO);

	/** 查找某城市某天发布的活动 */
	TaskResult queryActivityCommentByActivityId(String activityId);

}
