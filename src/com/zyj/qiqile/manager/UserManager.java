package com.zyj.qiqile.manager;

import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.task.TaskResult;

public interface UserManager {

	/** �û�ע�� */
	TaskResult register(String email, String password,String nick);
	
	/**�û���¼,���ظ���������Ϣ*/
	TaskResult login(String email, String password);

	/** ����һ���û� */
	TaskResult queryUserBOById(String id);

	/** �޸��ҵ���Ϣ */
	TaskResult update(UserBO userBO, String token);

	/** �޸����� */
	TaskResult updatePassword(String email, String password, String newPassword);

}
