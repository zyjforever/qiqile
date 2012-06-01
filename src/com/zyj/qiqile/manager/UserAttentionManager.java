package com.zyj.qiqile.manager;

import com.zyj.qiqile.task.TaskResult;

/**用户关注请求处理*/
public interface UserAttentionManager {

	/** 增加一个好友关注 */
	TaskResult addAttenttion(String userId,String attentionId);

	/** 查找一个用户所有的关注列表 */
	TaskResult queryUserAttentionList(String userId);
}
