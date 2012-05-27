package com.zyj.qiqile.task;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.QiqileMainActivity;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.manager.UserManager;
import com.zyj.qiqile.task.TaskResult.ResultCode;

/** 登录到服务器 */
public class LoginTask extends GenericTask {

	private Context context;

	public LoginTask() {
		this.context = QiqileApplication.context;
	}
	
	public LoginTask(Context context){
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Toast.makeText(
				QiqileApplication.context,
				QiqileApplication.context
						.getString(R.string.login_status_logging_in),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
		Toast.makeText(
				QiqileApplication.context,
				QiqileApplication.context
						.getString(R.string.login_status_logging_in),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected TaskResult _doInBackground(TaskParams... params) {
		UserManager userManager = QiqileApplication.getInstance()
				.getUserManager();
		TaskResult taskResult = userManager.login(
				(String) params[0].get(UserBO.EMAIL),
				(String) params[0].get(UserBO.PASSWORD));
		return taskResult;
	}

	@Override
	protected void onPostExecute(TaskResult result) {
		super.onPostExecute(result);
		if (result != null) {
			ResultCode resultCode = result.getResult();
			if (resultCode == ResultCode.SUCCESS) {
				Toast.makeText(
						QiqileApplication.context,
						QiqileApplication.context
								.getString(R.string.login_success),
						Toast.LENGTH_SHORT).show();
				QiqileApplication.getInstance().setCheckLogin(Boolean.TRUE);// 设置已经自动登录过
				QiqileApplication.getInstance().setIsLogin(Boolean.TRUE);// 设置已经登陆
				Intent intent = new Intent(QiqileApplication.context,
						QiqileMainActivity.class);
				((Activity) QiqileApplication.context).finish();
				QiqileApplication.context.startActivity(intent);
			} else if (resultCode == ResultCode.NETWORK_ERROR) {
				Toast.makeText(
						QiqileApplication.context,
						QiqileApplication.context
								.getString(R.string.login_status_network_or_connection_error),
						Toast.LENGTH_SHORT).show();
			} else if (resultCode == ResultCode.FAILED) {
				Toast.makeText(
						QiqileApplication.context,
						QiqileApplication.context
								.getString(R.string.login_status_invalid_username_or_password),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(
						QiqileApplication.context,
						QiqileApplication.context
								.getString(R.string.error_unknow),
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(
					QiqileApplication.context,
					QiqileApplication.context
							.getString(R.string.login_status_invalid_username_or_password),
					Toast.LENGTH_SHORT).show();
		}
	}
}