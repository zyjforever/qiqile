package com.zyj.qiqile.activity;

import com.zyj.qiqile.R;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.ServerErrorConstants;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.manager.UserManager;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;
import com.zyj.qiqile.tools.MD5Helper;
import com.zyj.qiqile.tools.Validator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends BasicWriteActivity {

	private EditText nickEdit;
	private EditText emailEdit;
	private EditText passwordEdit;
	private EditText password2Edit;

	private String nick;
	private String email;
	private String password;
	private String password2;

	private GenericTask registerTask;

	@Override
	protected void initView() {
		super.initView();
		nickEdit = (EditText) findViewById(R.id.nick_edit);
		emailEdit = (EditText) findViewById(R.id.email_edit);
		passwordEdit = (EditText) findViewById(R.id.password_edit);
		password2Edit = (EditText) findViewById(R.id.password_confirm_edit);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManagerApplication.getInstance().addActvity(this);
	}

	@Override
	protected void setContext() {
		setContext(RegisterActivity.this);
		QiqileApplication.getInstance().context = RegisterActivity.this;
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.register);
	}

	@Override
	protected Boolean validate() {
		Boolean result = Boolean.FALSE;
		nick = nickEdit.getText().toString();
		email = emailEdit.getText().toString();
		password = passwordEdit.getText().toString();
		password2 = password2Edit.getText().toString();
		if (Validator.isNick(nick)) {
			if (Validator.isEmail(email)) {
				if (password.equals(password2)) {
					if (Validator.isPassword(password)) {
						result = Boolean.TRUE;
					} else {
						Toast.makeText(context, R.string.error_password,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(context, R.string.error_different_password,
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(context, R.string.error_user_email,
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context, R.string.error_user_nick_toolong,
					Toast.LENGTH_SHORT).show();
		}

		return result;
	}

	@Override
	protected void beforeBack() {
	}

	@Override
	protected void beforeFinish() {
		TaskParams params = new TaskParams();
		params.put(UserBO.EMAIL, email);
		params.put(UserBO.PASSWORD, password);
		params.put(UserBO.NICK, nick);
		registerTask = new RegisterTask();
		registerTask.execute(params);
	}

	class RegisterTask extends GenericTask {

		private Context context;

		public RegisterTask() {
			context = QiqileApplication.context;
		}

		public RegisterTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(context,
					context.getString(R.string.register_status_in),
					Toast.LENGTH_SHORT).show();
		}

		@Override
		protected TaskResult _doInBackground(TaskParams... params) {
			UserManager userManager = QiqileApplication.getInstance()
					.getUserManager();
			TaskResult taskResult = userManager.register(
					(String) params[0].get(UserBO.EMAIL),
					MD5Helper.MD5((String) params[0].get(UserBO.PASSWORD)),
					(String) params[0].get(UserBO.NICK));
			return taskResult;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			if (result != null) {
				ResultCode resultCode = result.getResult();
				if (resultCode == ResultCode.SUCCESS) {
					Toast.makeText(
							context,
							context.getString(R.string.register_status_success),
							Toast.LENGTH_SHORT).show();
					((Activity) context).finish();
				} else if (resultCode == ResultCode.NETWORK_ERROR) {
					Toast.makeText(
							context,
							context.getString(R.string.login_status_network_or_connection_error),
							Toast.LENGTH_SHORT).show();
				} else if (resultCode == ResultCode.FAILED
						&& result.getErrorCode() == ServerErrorConstants.ERROR_EMAIL_UNQUE) {
					Toast.makeText(
							context,
							context.getString(R.string.register_status_fail_email),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context,
							context.getString(R.string.error_unknow),
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(
						context,
						context.getString(R.string.login_status_invalid_username_or_password),
						Toast.LENGTH_SHORT).show();
			}

		}
	}

}
