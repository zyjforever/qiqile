package com.zyj.qiqile.manager;

import com.zyj.qiqile.task.TaskResult;

/**�û���ע������*/
public interface UserAttentionManager {

	/** ����һ�����ѹ�ע */
	TaskResult addAttenttion(String userId,String attentionId);

	/** ����һ���û����еĹ�ע�б� */
	TaskResult queryUserAttentionList(String userId);
}
