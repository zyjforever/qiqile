package com.zyj.qiqile.manager;

import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.task.TaskResult;

public interface UserManager {

	/** 用户注册 */
	TaskResult register(String email, String password,String nick);
	
	/**用户登录,返回个人完整信息*/
	TaskResult login(String email, String password);

	/** 查找一个用户 */
	TaskResult queryUserBOById(String id);

	/** 修改我的信息 */
	TaskResult update(UserBO userBO, String token);

	/** 修改密码 */
	TaskResult updatePassword(String email, String password, String newPassword);

}
