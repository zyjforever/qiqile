package com.zyj.qiqile.activity;

import com.zyj.qiqile.R;
import com.zyj.qiqile.app.ActivityManagerApplication;
import com.zyj.qiqile.app.QiqileApplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * header只有一个back按钮的activity都继承这个类
 * */
public abstract class BasicBackActivity extends BasicActivity {
	protected TextView backButton;
	protected TextView headTitle;
	protected Context context;
	protected OnClickListener backListener;
	protected String titleString;

	public TextView getBackButton() {
		return backButton;
	}

	public void setBackButton(TextView backButton) {
		this.backButton = backButton;
	}

	public TextView getHeadTitle() {
		return headTitle;
	}

	public void setHeadTitle(TextView headTitle) {
		this.headTitle = headTitle;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public OnClickListener getBackListener() {
		return backListener;
	}

	public void setBackListener(OnClickListener backListener) {
		this.backListener = backListener;
	}

	public String getTitleString() {
		return titleString;
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	public void init() {
		setContext();// 设置context
		super.init();
		setHeadTitle();
		QiqileApplication.getInstance().context = context;
		ActivityManagerApplication.getInstance().addActvity((Activity) context);
	}

	@Override
	protected void initView() {
		backButton = (TextView) findViewById(R.id.top_back);
		headTitle = (TextView) findViewById(R.id.title);
	}

	@Override
	protected void initListener() {
		backListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				beforeBack();
				((Activity) context).finish();// 结束当前的activity
				((Activity) context).onBackPressed();
			}
		};

	}

	@Override
	protected void bindListener() {
		backButton.setOnClickListener(backListener);
	}

	private void setHeadTitle() {
		headTitle.setText(titleString);
	}
	
	/** 设置context */
	protected abstract void setContext();
	
	protected void beforeBack(){
		
	}

}
