package com.zyj.qiqile.activity;

import com.zyj.qiqile.R;
import com.zyj.qiqile.app.ActivityManagerApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class FindPasswordActivity extends BasicBackActivity {

	private EditText emailEdit;
	private TextView findPasswordButton;
	private String emailString;

	private OnClickListener findPasswordListener;
	private OnEditorActionListener emailEditorActionListener;
	/** 是否合法email */
	private Boolean emailIsLegal = Boolean.TRUE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitleString(getString(R.string.find_password_header));
		ActivityManagerApplication.getInstance().addActvity(this);
	}

	@Override
	protected void setContext() {
		context = FindPasswordActivity.this;
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.find_password);
	}

	@Override
	protected void initView() {
		findPasswordButton = (TextView) findViewById(R.id.find_password_button);
		emailEdit = (EditText) findViewById(R.id.user_email_edit);
		super.initView();
	}

	@Override
	protected void initListener() {
		findPasswordListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (emailIsLegal) {
					AlertDialog.Builder builder = new Builder(context);
					builder.setMessage(getString(R.string.find_password_success));
					builder.setPositiveButton(
							"确认",
							new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									((Activity) context).finish();
									((Activity) context).onBackPressed();
								}
							});
					builder.show();
				}
			}
		};

		emailEditorActionListener = new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (v.equals(emailEdit)
						&& event.getAction() == KeyEvent.ACTION_UP) {
					emailString = v.getText().toString();// 得到输入
				}
				return false;
			}
		};
		super.initListener();
	}

	@Override
	protected void bindListener() {
		emailEdit.setOnEditorActionListener(emailEditorActionListener);
		findPasswordButton.setOnClickListener(findPasswordListener);
		super.bindListener();
	}

}
