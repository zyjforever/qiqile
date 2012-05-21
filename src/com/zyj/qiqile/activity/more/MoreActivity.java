package com.zyj.qiqile.activity.more;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zyj.qiqile.R;
import com.zyj.qiqile.activity.BasicActivity;
import com.zyj.qiqile.activity.QiqileMainActivity;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;
import com.zyj.qiqile.domain.bo.UserBO;

public class MoreActivity extends BasicActivity {

	private TextView modifyPasswordButton;
	private TextView preferencesButton;
	private TextView feedbackButton;
	private TextView aboutButton;
	private TextView helpButton;
	private TextView signoutButton;
	private TextView exitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		QiqileApplication.getInstance().context = this;
		ActivityManagerApplication.getInstance().addActvity(this);
	}

	@Override
	protected void setContentView() {
		this.setContentView(R.layout.more);
	}

	@Override
	protected void initView() {
		modifyPasswordButton = (TextView) findViewById(R.id.modify_password_button);
		preferencesButton = (TextView) findViewById(R.id.preferences_button);
		feedbackButton = (TextView) findViewById(R.id.feedback_button);
		aboutButton = (TextView) findViewById(R.id.about_button);
		helpButton = (TextView) findViewById(R.id.help_button);
		signoutButton = (TextView) findViewById(R.id.signout_button);
		exitButton = (TextView) findViewById(R.id.exit_button);
	}

	@Override
	protected void initListener() {
		modifyPasswordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MoreActivity.this,
						ModifyPasswordActivity.class);
				startActivity(intent);
			}
		});
		feedbackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MoreActivity.this,
						FeedbackActivity.class);
				startActivity(intent);
			}
		});
		aboutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MoreActivity.this,
						AboutActivity.class);
				startActivity(intent);
			}
		});
		signoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MoreActivity.this)
						.setTitle(getString(R.string.confirm_lognout))
						.setPositiveButton(getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										QiqileApplication.getInstance()
												.setIsLogin(Boolean.FALSE);
										Intent intent = new Intent(
												QiqileApplication.context,
												QiqileMainActivity.class);
										QiqileApplication.context
												.startActivity(intent);
										SharedPreferences p = PreferenceManager
												.getDefaultSharedPreferences(QiqileApplication.context);
										Editor editor = p.edit();
										editor.remove(UserBO.EMAIL);
										editor.remove(UserBO.PASSWORD);
										editor.commit();
										((Activity) QiqileApplication.context)
												.finish();
									}
								})
						.setNegativeButton(getString(R.string.no), null).show();
			}
		});
		exitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MoreActivity.this)
						.setTitle(getString(R.string.confirm_exit))
						.setPositiveButton(getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										ActivityManagerApplication
												.getInstance().exit();
									}
								})
						.setNegativeButton(getString(R.string.no), null).show();
			}
		});
	}

	@Override
	protected void bindListener() {

	}

}
