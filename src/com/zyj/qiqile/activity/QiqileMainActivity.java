package com.zyj.qiqile.activity;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.activity.ActivityListActivity;
import com.zyj.qiqile.activity.meprofile.MeProfileActivity;
import com.zyj.qiqile.activity.more.MoreActivity;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.UserBO;
import com.zyj.qiqile.task.GenericTask;
import com.zyj.qiqile.task.LoginTask;
import com.zyj.qiqile.task.TaskParams;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QiqileMainActivity extends ActivityGroup {
	private LinearLayout container;
	private TextView loginButton;
	private TextView shareButton;
	private TextView moreButton;
	private TextView meButton;
	private TextView friendButton;
	// Preferences.
	private SharedPreferences mPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (QiqileApplication.getInstance().isLogin()) {
			setContentView(R.layout.main);
		} else {
			setContentView(R.layout.main_unlogin);
		}
		QiqileApplication.getInstance().context = this;
		ActivityManagerApplication.getInstance().addActvity(this);
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		autoLogin();
		container = (LinearLayout) findViewById(R.id.containerBody);
		container.addView(getLocalActivityManager().startActivity(
				"share",
				new Intent(QiqileMainActivity.this, ActivityListActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
		initButton();
		bindListener();
	}

	private void initButton() {
		shareButton = (TextView) findViewById(R.id.footer_btn_share);
		moreButton = (TextView) findViewById(R.id.footer_btn_more);
		if (QiqileApplication.getInstance().isLogin()) {
			meButton = (TextView) findViewById(R.id.footer_btn_me);
			friendButton = (TextView) findViewById(R.id.footer_btn_friend);
		} else {
			loginButton = (TextView) findViewById(R.id.footer_btn_login);
		}
	}

	/** 绑定事件 **/
	private void bindListener() {
		shareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				container.removeAllViews();
				container.addView(getLocalActivityManager().startActivity(
						"share",
						new Intent(QiqileMainActivity.this,
								ActivityListActivity.class)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView());
			}
		});
		moreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				container.removeAllViews();
				container.addView(getLocalActivityManager().startActivity(
						"more",
						new Intent(QiqileMainActivity.this, MoreActivity.class)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView());
			}
		});
		if (QiqileApplication.getInstance().isLogin()) {
			friendButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					container.removeAllViews();
					container.addView(getLocalActivityManager().startActivity(
							"friend",
							new Intent(QiqileMainActivity.this,
									FriendActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
							.getDecorView());
				}
			});
			meButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					container.removeAllViews();
					container.addView(getLocalActivityManager().startActivity(
							"me",
							new Intent(QiqileMainActivity.this,
									MeProfileActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
							.getDecorView());
				}
			});
		} else {
			loginButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					container.removeAllViews();
					container.addView(getLocalActivityManager().startActivity(
							"login",
							new Intent(QiqileMainActivity.this,
									LoginActivity.class)
									.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
							.getDecorView());
				}
			});
		}
	}

	private void autoLogin() {
		if (mPreferences != null) {
			String email = mPreferences.getString(UserBO.EMAIL, null);
			String password = mPreferences.getString(UserBO.PASSWORD, null);
			Boolean checkedLogin = QiqileApplication.getInstance()
					.getCheckLogin();
			if (!checkedLogin && email != null && password != null) {
				GenericTask task = new LoginTask();
				TaskParams params = new TaskParams();
				params.put(UserBO.PASSWORD, password);
				params.put(UserBO.EMAIL, email);
				task.execute(params);
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确定要退出吗?");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						ActivityManagerApplication.getInstance().exit();
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

}
