package com.zyj.qiqile.activity;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.activity.ActivityListActivity;
import com.zyj.qiqile.activity.meprofile.MeProfileActivity;
import com.zyj.qiqile.activity.more.MoreActivity;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * ӵ�еײ���activity���̳�����࣬���ﶨ�����·���ť�Ļ����¼�
 * */
@Deprecated
public abstract class BasicMainActivity extends BasicActivity {
	protected Context context;

	// protected TextView loginButton;
	// protected TextView shareButton;
	// protected TextView moreButton;
	// protected TextView meButton;
	// protected TextView friendButton;

	// protected OnClickListener shareListener;
	// protected OnClickListener loginListener;
	// protected OnClickListener moreListener;
	// protected OnClickListener meListener;
	// protected OnClickListener friendListener;

	public void init() {
		setContext();// ����context
		setContentView();// ����contentView
		initView();// ��ʼ��view
		initListener();// ��ʼ��������
		bindListener();// �󶨼�����
		QiqileApplication.getInstance().context = context;
		ActivityManagerApplication.getInstance().addActvity((Activity) context);
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("ȷ��Ҫ�˳���?");
		builder.setPositiveButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						ActivityManagerApplication.getInstance().exit();
					}
				});
		builder.setNegativeButton("ȡ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	/** ��ʼ��listener */
	protected void initListener() {
		// this.loginListener = new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(context, LoginActivity.class);
		// startActivity(intent);
		// }
		// };
		// this.friendListener = new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(context, FriendActivity.class);
		// startActivity(intent);
		// }
		// };
		//
		// this.meListener = new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(context, MeProfileActivity.class);
		// startActivity(intent);
		// }
		// };
		//
		// this.moreListener = new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(context, MoreActivity.class);
		// startActivity(intent);
		// }
		// };
		// this.shareListener = new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(context, ActivityListActivity.class);
		// startActivity(intent);
		// }
		// };

	}

	/** ��ʼ��view */
	protected void initView() {
		// shareButton = (TextView) findViewById(R.id.footer_btn_share);
		// moreButton = (TextView) findViewById(R.id.footer_btn_more);
		// if (QiqileApplication.getInstance().isLogin()) {
		// meButton = (TextView) findViewById(R.id.footer_btn_me);
		// friendButton = (TextView) findViewById(R.id.footer_btn_friend);
		// } else {
		// loginButton = (TextView) findViewById(R.id.footer_btn_login);
		// }
	}

	/** ���¼� **/
	protected void bindListener() {
		// shareButton.setOnClickListener(shareListener);
		// moreButton.setOnClickListener(moreListener);
		// if (QiqileApplication.getInstance().isLogin()) {
		// friendButton.setOnClickListener(friendListener);
		// meButton.setOnClickListener(meListener);
		// } else {
		// loginButton.setOnClickListener(loginListener);
		// }
	}

	/** ����context */
	protected abstract void setContext();
}
