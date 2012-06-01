package com.zyj.qiqile.activity;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.activity.ActivityListActivity;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.constant.ServerErrorConstants;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.manager.UserManager;
import com.zyj.qiqile.task.GenericAferExcutedListener;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.LoginTask;
import com.zyj.qiqile.task.TaskParams;
import com.zyj.qiqile.task.TaskResult;
import com.zyj.qiqile.task.TaskResult.ResultCode;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BasicMainActivity {

	// view
	private TextView registerButton;
	private TextView findPasswordButton;
	private TextView signInButton;

	private EditText emailOrAccountEdit;
	private EditText passwordEdit;

	// value
	private String emailOrAccount;
	private String password;

	private GenericTask loginTask;

	// Preferences.
	private SharedPreferences mPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManagerApplication.getInstance().addActvity(this);
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public void setContentView() {
		super.setContentView(R.layout.login);
	}

	public void Refresh() {

	}

	@Override
	protected void setContext() {
		context = LoginActivity.this;
		QiqileApplication.getInstance().context = LoginActivity.this;
	}

	@Override
	protected void initView() {
		registerButton = (TextView) findViewById(R.id.top_register_btn);
		findPasswordButton = (TextView) findViewById(R.id.find_password_link);
		signInButton = (TextView) findViewById(R.id.signin_button);
		emailOrAccountEdit = (EditText) findViewById(R.id.account_edit);
		passwordEdit = (EditText) findViewById(R.id.password_edit);
		super.initView();
	}

	@Override
	protected void initListener() {
		super.initListener();
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});
		findPasswordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						FindPasswordActivity.class);
				startActivity(intent);
			}
		});
		signInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				validateUser();
			}
		});
	}

	public Boolean validateUser() {
		Boolean result = Boolean.FALSE;
		emailOrAccount = emailOrAccountEdit.getText() == null ? null
				: emailOrAccountEdit.getText().toString();
		password = passwordEdit.getText() == null ? null : passwordEdit
				.getText().toString();
		if (emailOrAccount != null && !emailOrAccount.equals("")) {
			if (password != null && !password.equals("")) {
				TaskParams params = new TaskParams();
				params.put(UserBO.PASSWORD, password);
				params.put(UserBO.EMAIL, emailOrAccount);
				loginTask = new LoginTask(this);
				loginTask.setListener(new GenericAferExcutedListener() {
					@Override
					public void onPostExecute(GenericTask task,
							TaskResult result) {
						if (result.getResult() == ResultCode.SUCCESS) {
							Editor editor = mPreferences.edit();
							editor.putString(UserBO.EMAIL, emailOrAccount);
							editor.putString(UserBO.PASSWORD, password);
							editor.commit();
							QiqileApplication.getInstance().setCheckLogin(Boolean.TRUE);// 设置已经自动登录过
							QiqileApplication.getInstance().setIsLogin(Boolean.TRUE);// 设置已经登陆
							QiqileApplication.getInstance().getMainActivity()
									.setContentView();
						}
					}
				});
				loginTask.execute(params);
			} else {
				Toast.makeText(context, R.string.input_password,
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context, R.string.input_email_and_account,
					Toast.LENGTH_SHORT).show();
		}
		return result;
	}
}
